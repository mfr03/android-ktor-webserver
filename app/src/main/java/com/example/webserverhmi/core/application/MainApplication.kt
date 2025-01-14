package com.example.webserverhmi.core.application

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.webserverhmi.core.ktor.ServerManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication: Application() {

    lateinit var serverManager: ServerManager
    val dataStore: DataStore<Preferences> by preferencesDataStore(name = "routing")


    override fun onCreate() {
        super.onCreate()

        serverManager = ServerManager()
    }

    override fun onTerminate() {
        super.onTerminate()
        serverManager.stopAllServers()
    }
}