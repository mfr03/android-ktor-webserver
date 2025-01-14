package com.example.webserverhmi.data.home.viewmodel

import android.util.Log
import io.ktor.server.application.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.webserverhmi.core.ktor.ServerManager
import com.example.webserverhmi.core.ktor.configureRouting
import com.example.webserverhmi.core.ktor.configureSerialization
import com.example.webserverhmi.data.home.state.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.NetworkInterface
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val serverManager: ServerManager
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

    fun getLocalIp()
    {
        try {
            val interfaces = NetworkInterface.getNetworkInterfaces()
            for (intf in interfaces) {
                val addresses = intf.inetAddresses
                for (address in addresses) {
                    if (!address.isLoopbackAddress && address is java.net.Inet4Address) {
                        hostAddressUpdate(address.hostAddress)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            hostAddressUpdate("")
        }

    }

    fun hostAddressUpdate(newAddress: String) {
        _uiState.value = _uiState.value.copy(
            hostAddress = newAddress
        )
    }

    fun hostPortUpdate(newPort: String) {
        _uiState.value = _uiState.value.copy(
            hostPort = newPort
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

    fun startServer() {

        val host = _uiState.value.hostAddress
        val port = _uiState.value.hostPort.toIntOrNull() ?: return

        loadingStateUpdate(true)

        viewModelScope.launch(Dispatchers.IO) {

            val result = serverManager.startServer(host = host, port = port) {
                monitor.subscribe(ApplicationStarted) {
                    val res = serverManager.serverSuccessfullyStarted(host, port)
                    showSnackbar(res)
                    statusStateUpdate(true)
                }

                monitor.subscribe(ApplicationStopped) {
                    val res = serverManager.serverStopped(host, port)
                    showSnackbar(res)
                    statusStateUpdate(false)
                    monitor.unsubscribe(ApplicationStarted) {}
                    monitor.unsubscribe(ApplicationStopped) {}
                }
                configureRouting()
                configureSerialization()
                loadingStateUpdate(false)
            }

            withContext(Dispatchers.Main) {
                if(result?.isNotEmpty() == true) {
                    showSnackbar(result)
                    statusStateUpdate(false)
                    loadingStateUpdate(false)
                }
            }
        }
    }

    fun stopServer() {
        viewModelScope.launch(Dispatchers.IO) {
            loadingStateUpdate(true)
            val res = serverManager.stopServer(port = _uiState.value.hostPort.toInt())

            if(res == "Server stopped successfully.") {
                loadingStateUpdate(false)
            } else
            {
                showSnackbar("No server is running")
                loadingStateUpdate(false)
            }

        }
    }

}