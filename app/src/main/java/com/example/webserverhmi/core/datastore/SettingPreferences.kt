package com.example.webserverhmi.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val Context.datastore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)


//@Module
//@InstallIn(SingletonComponent::class)
//abstract class SettingPreferencesModule {
//
//    @Binds
//    @Singleton
//    abstract fun bindSettingPreferences()
//}