package com.example.webserverhmi.features.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.webserverhmi.data.server.model.WebServer
import com.example.webserverhmi.data.server.repository.ServerRepository
import com.example.webserverhmi.data.server.repository.ServerRepositoryImplementation
import com.example.webserverhmi.data.user.model.UserSettings
import com.example.webserverhmi.data.user.repository.UserSettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val serverRepository: ServerRepository,
    private val userSettingsRepository: UserSettingsRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenState())
    val uiState: StateFlow<HomeScreenState> = _uiState.asStateFlow()

    private val _userSettings = MutableStateFlow(UserSettings())
    private val userSettings: StateFlow<UserSettings> = _userSettings.asStateFlow()

    val userWebServerSettings: StateFlow<WebServer> = userSettings.map { it.userWebServerSettings }
        .stateIn(viewModelScope, SharingStarted.Eagerly, WebServer())

    init {
        fetchUserSettings()
    }

    private fun fetchUserSettings() {
        viewModelScope.launch {
            _userSettings.value = userSettingsRepository.getUserSettings()
        }
    }

    fun updateUserWebserverSettings(webserverSettings: WebServer) {
        viewModelScope.launch {
            userSettingsRepository.saveUserSettings { preferences ->
                preferences.copy(userWebServerSettings = webserverSettings)
            }
        }
        fetchUserSettings()
    }



    fun showSnackbar(message: String)
    {
        _uiState.update { homeScreenState ->  
            homeScreenState.copy(snackbarMessage = message)
        }
    }

    fun clearSnackbar()
    {
        _uiState.update { homeScreenState ->
            homeScreenState.copy(snackbarMessage = "")
        }
    }

    suspend fun getLocalIp()
    {
        when(val result = serverRepository.getLocalIp()) {
            result -> {
                hostAddressUpdate(result.getOrDefault("0.0.0.0"))
            }
            else -> {
                showSnackbar("No Addresses were found")
                hostAddressUpdate("0.0.0.0")
            }
        }
    }

    fun hostAddressUpdate(newAddress: String) {
        _userSettings.update { userSettings ->
            userSettings.copy(userWebServerSettings =
            WebServer(newAddress, _userSettings.value.userWebServerSettings.hostPort)
            )
        }
    }

    fun hostPortUpdate(newPort: String) {
        _userSettings.update { userSettings ->
            userSettings.copy(userWebServerSettings =
            WebServer(_userSettings.value.userWebServerSettings.hostAddress, newPort)
            )
        }
    }

    private fun statusStateUpdate(statusState: Boolean) {
        _uiState.update { homeScreenState ->
            homeScreenState.copy(statusState = statusState)
        }
    }

    private fun loadingStateUpdate(loadingState: Boolean)
    {
        _uiState.update { homeScreenState ->
            homeScreenState.copy(loadingState = loadingState)
        }
    }

    suspend fun startServer() {

        val host = _userSettings.value.userWebServerSettings.hostAddress
        val port = _userSettings.value.userWebServerSettings.hostPort.toIntOrNull() ?: return

        loadingStateUpdate(true)

        val result = serverRepository.startServer(
            hostAddress = host,
            hostPort = port,
            uiEvent = { res, boolean ->
                showSnackbar(res)
                statusStateUpdate(boolean)
            }
        )

        if(result.isFailure) {
            showSnackbar("Error starting server: ${result.exceptionOrNull()?.message}")
        }

        loadingStateUpdate(false)
        updateUserWebserverSettings(_userSettings.value.userWebServerSettings)
    }

    suspend fun stopServer() {
        loadingStateUpdate(true)

        val result = serverRepository.stopServer(_userSettings.value.userWebServerSettings.hostPort.toInt())

        if(result.isSuccess){
            if(result.getOrNull() == "Server stopped successfully.") {
                showSnackbar("Server stopped successfully.")
            } else {
                showSnackbar("Server is already stopped.")
            }
            loadingStateUpdate(false)
        } else {
            showSnackbar("Error stopping server: ${result.exceptionOrNull()?.message}")
        }

    }

}