package com.example.webserverhmi.data.user.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.webserverhmi.data.user.model.UserSettings
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserSettingsRepositoryImplementation @Inject constructor(
    @ApplicationContext private val context: Context
): UserSettingsRepository {

    private val USER_SETTINGS_KEY = stringPreferencesKey("user_settings_key")
    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun getUserSettings(): UserSettings {
        val preferences = context.dataStore.data.first()
        val jsonString = preferences[USER_SETTINGS_KEY]
        return if(jsonString != null) {
            json.decodeFromString(jsonString)
        } else {
            UserSettings()
        }

    }


    override suspend fun saveUserSettings(update: (UserSettings) -> UserSettings) {
        context.dataStore.edit { preferences ->
            val currentPreferences = getUserSettings()
            val updatedPreferences = update(currentPreferences)
          preferences[USER_SETTINGS_KEY] = json.encodeToString(updatedPreferences)
        }
    }


}