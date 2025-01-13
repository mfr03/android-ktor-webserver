package com.example.webserverhmi.data.home.state

data class HomeScreenState (
    val hostAddress: String = "0.0.0.0",
    val hostPort: String = "8080",
    val snackbarMessage: String = "",
    val statusState: Boolean = false,
    val postState: Boolean = false,
    val getState: Boolean = false,
    val loadingState: Boolean = false,
)