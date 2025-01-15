package com.example.webserverhmi.features.routing.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.webserverhmi.data.user.repository.UserSettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutingScreenViewModel @Inject constructor(
    private val userSettingsRepository: UserSettingsRepository
): ViewModel() {
    private val _userRouting = MutableStateFlow<List<String>>(emptyList())
    val userRouting: StateFlow<List<String>> = _userRouting

    fun fetchUserRouting() {
        viewModelScope.launch {
            _userRouting.value = userSettingsRepository.getUserRouting()
            Log.d("RoutingScreen", _userRouting.value.toString())
        }
    }


}