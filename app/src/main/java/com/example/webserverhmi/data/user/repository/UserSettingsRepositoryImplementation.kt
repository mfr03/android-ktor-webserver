package com.example.webserverhmi.data.user.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")



class UserSettingsRepositoryImplementation @Inject constructor(
    @ApplicationContext private val context: Context
): UserSettingsRepository {

    private val USER_ROUTING_KEY = stringPreferencesKey("user_routing_key")
    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun getUserRouting(): List<String> {
        val preferences = context.dataStore.data.first()
        val jsonString = preferences[USER_ROUTING_KEY] ?: "[]"
        return json.decodeFromString(jsonString)
    }

    override suspend fun saveUserRouting(routing: List<String>) {
        val jsonString = json.encodeToString(ListSerializer(String.serializer()), routing)
        context.dataStore.edit { preferences ->
            preferences[USER_ROUTING_KEY] = jsonString
        }
    }


}