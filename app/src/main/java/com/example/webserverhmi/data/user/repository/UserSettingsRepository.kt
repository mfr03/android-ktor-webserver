package com.example.webserverhmi.data.user.repository

import com.example.webserverhmi.data.user.model.UserSettings

interface UserSettingsRepository {
    suspend fun getUserSettings(): UserSettings
    suspend fun saveUserSettings(update: (UserSettings) -> UserSettings)
}