package com.example.webserverhmi.data.user.repository

interface UserSettingsRepository {
    suspend fun getUserRouting(): List<String>
    suspend fun saveUserRouting(routing: List<String>)
}