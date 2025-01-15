package com.example.webserverhmi.features.routing.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.webserverhmi.features.routing.viewmodel.RoutingScreenViewModel

@Composable
fun RoutingScreen(viewModel: RoutingScreenViewModel)
{
    viewModel.fetchUserRouting()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

    }
}