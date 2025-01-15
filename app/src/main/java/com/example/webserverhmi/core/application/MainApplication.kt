package com.example.webserverhmi.core.application

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.webserverhmi.core.ktor.ServerManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MainApplication(): Application() {

    val dataStore: DataStore<Preferences> by preferencesDataStore(name = "userAddressableSettings")

    override fun onCreate() {
        super.onCreate()

    }

    override fun onTerminate() {
        super.onTerminate()

    }
}