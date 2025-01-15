package com.example.webserverhmi.features.home.viewmodel

import androidx.lifecycle.ViewModel
import com.example.webserverhmi.data.server.model.WebServer
import com.example.webserverhmi.data.server.repository.ServerRepository
import com.example.webserverhmi.data.server.repository.ServerRepositoryImplementation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val serverRepository: ServerRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenState())
    val uiState: StateFlow<HomeScreenState> = _uiState.asStateFlow()


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
        _uiState.value = _uiState.value.copy(
            webServer = WebServer(
                hostAddress = newAddress,
                hostPort = _uiState.value.webServer.hostPort
            )
        )
    }

    fun hostPortUpdate(newPort: String) {
        _uiState.value = _uiState.value.copy(
            webServer = WebServer(
                hostAddress = _uiState.value.webServer.hostAddress,
                hostPort = newPort
            )
        )
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

        val host = _uiState.value.webServer.hostAddress
        val port = _uiState.value.webServer.hostPort.toIntOrNull() ?: return

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

    }

    suspend fun stopServer() {
        loadingStateUpdate(true)

        val result = serverRepository.stopServer(_uiState.value.webServer.hostPort.toInt())

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