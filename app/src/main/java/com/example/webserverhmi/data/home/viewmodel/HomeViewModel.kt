package com.example.webserverhmi.data.home.viewmodel

import io.ktor.server.application.*
import androidx.lifecycle.ViewModel
import com.example.webserverhmi.core.ktor.ServerManager
import com.example.webserverhmi.core.ktor.configureRouting
import com.example.webserverhmi.core.ktor.configureSerialization
import com.example.webserverhmi.data.home.state.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val serverManager: ServerManager
): ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenState())
    val uiState: StateFlow<HomeScreenState> = _uiState.asStateFlow()


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

    fun startServer() {

        val host = _uiState.value.hostAddress
        val port = _uiState.value.hostPort.toIntOrNull() ?: return

        serverManager.startServer(host = host, port = port) {
            configureRouting()
            configureSerialization()
        }
    }

}