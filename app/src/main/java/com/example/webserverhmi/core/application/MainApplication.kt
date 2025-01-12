package com.example.webserverhmi.core.application

import android.app.Application
import com.example.webserverhmi.core.ktor.ServerManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication: Application() {

    lateinit var serverManager: ServerManager

    override fun onCreate() {
        super.onCreate()

        serverManager = ServerManager()
    }

    override fun onTerminate() {
        super.onTerminate()
        serverManager.stopAllServers()
    }
}