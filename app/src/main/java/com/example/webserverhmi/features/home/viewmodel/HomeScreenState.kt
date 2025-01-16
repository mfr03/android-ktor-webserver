package com.example.webserverhmi.features.home.viewmodel



data class HomeScreenState (
    val snackbarMessage: String = "",
    val statusState: Boolean = false,
    val postState: Boolean = false,
    val getState: Boolean = false,
    val loadingState: Boolean = false,
)