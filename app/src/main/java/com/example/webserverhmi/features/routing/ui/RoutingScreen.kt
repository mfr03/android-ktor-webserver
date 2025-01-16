package com.example.webserverhmi.features.routing.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.webserverhmi.features.composable.RoutingItem
import com.example.webserverhmi.features.routing.viewmodel.RoutingScreenViewModel

@Composable
fun RoutingScreen(viewModel: RoutingScreenViewModel)
{
    val userRouting by viewModel.userRouting.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        LazyColumn {
            userRouting.forEach { routing ->
                item {
                    RoutingItem(routing = routing, routingType = "POST")
                }
            }
        }
    }
}