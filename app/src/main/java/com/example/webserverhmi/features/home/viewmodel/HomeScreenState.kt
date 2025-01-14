package com.example.webserverhmi.features.home.viewmodel

import com.example.webserverhmi.data.server.model.WebServer

data class HomeScreenState (
    val webServer: WebServer = WebServer(),
    val snackbarMessage: String = "",
    val statusState: Boolean = false,
    val postState: Boolean = false,
    val getState: Boolean = false,
    val loadingState: Boolean = false,
)