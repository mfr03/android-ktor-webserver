package com.example.webserverhmi.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Settings

object NavigationItems {
    val navigationItem = listOf(
        NavigationItem(
            title = "Home",
            route = "Home",
            selectedIcon = Icons.Default.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        NavigationItem(
            title = "Routing",
            route = "Routing",
            selectedIcon = Icons.Default.Place,
            unselectedIcon = Icons.Outlined.Place
        ),
        NavigationItem(
            title = "Export",
            route = "Export",
            selectedIcon = Icons.Default.Email,
            unselectedIcon = Icons.Outlined.Email
        ),
        NavigationItem(
            title = "Settings",
            route = "Settings",
            selectedIcon = Icons.Default.Settings,
            unselectedIcon = Icons.Outlined.Settings
        ),
    )
}