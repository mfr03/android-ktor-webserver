package com.example.webserverhmi.features.routing.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.webserverhmi.data.server.model.WebServer
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
class RoutingScreenViewModel @Inject constructor(
    private val userSettingsRepository: UserSettingsRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(RoutingScreenState())
    val uiState: StateFlow<RoutingScreenState> = _uiState.asStateFlow()

    private val _userSettings = MutableStateFlow(UserSettings())
    private val userSettings: StateFlow<UserSettings> = _userSettings.asStateFlow()

    val userRouting: StateFlow<List<Pair<String, String>>> = userSettings.map { it.routings}
        .stateIn(viewModelScope, SharingStarted.Eagerly, listOf(Pair("/", "GET")))

    init {
        fetchUserSettings()
    }

    private fun fetchUserSettings() {
        viewModelScope.launch {
            _userSettings.value = userSettingsRepository.getUserSettings()
        }
    }

    fun updateUserRouting(routing: List<Pair<String, String>>) {
        viewModelScope.launch {
            userSettingsRepository.saveUserSettings { preferences ->
                preferences.copy(routings = routing)
            }
            fetchUserSettings()
        }
    }

    fun updateDialogState(newState: Boolean) {
        _uiState.update { uiState ->
            uiState.copy(
                dialogState = newState
            )
        }
    }



}