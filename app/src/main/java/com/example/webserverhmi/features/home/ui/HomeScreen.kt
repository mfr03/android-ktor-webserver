package com.example.webserverhmi.features.home.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.webserverhmi.R
import com.example.webserverhmi.data.home.state.HomeScreenState
import com.example.webserverhmi.data.home.viewmodel.HomeViewModel
import com.example.webserverhmi.features.composable.CircleIndicatorWithText
import com.example.webserverhmi.features.composable.TextFieldWithString
import com.example.webserverhmi.ui.theme.WebserverHMITheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow



@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel
) {

    val homeScreenState by viewModel.uiState.collectAsState()

    LaunchedEffect(homeScreenState) {
        Log.d("HomeScreen", "Host Address: ${homeScreenState.hostAddress}, Host Port: ${homeScreenState.hostPort}")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInteropFilter {
                homeScreenState.loadingState
            }
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextFieldWithString(textFieldValue = homeScreenState.hostAddress, stringRes = R.string.host_address,
                    forceNumber = false) { newAddress ->
                    viewModel.hostAddressUpdate(newAddress = newAddress)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextFieldWithString(textFieldValue = homeScreenState.hostPort, stringRes = R.string.host_port,
                    forceNumber = true) { newPort ->
                    viewModel.hostPortUpdate(newPort)

                }
            }
            Column {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
                ) {
                    OutlinedButton(onClick = {
                        viewModel.getLocalIp()
                    }) {
                        Text(text = "Get Local IP Address")
                    }
                    OutlinedButton(onClick = {
                        viewModel.startServer()
                    }) {
                        Text(text = "Start Server")
                    }

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    OutlinedButton(onClick = {
                        viewModel.stopServer()
                    }) {
                        Text(text = "Stop Server")
                    }
                }

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Bottom),
                    text = "Server Activity")

                CircleIndicatorWithText(
                    indicatorText = "Status",
                    primaryBorderColor = MaterialTheme.colorScheme.primaryContainer,
                    primaryBackgroundColor = MaterialTheme.colorScheme.primary,
                    errorBorderColor = MaterialTheme.colorScheme.errorContainer,
                    errorBackgroundColor = MaterialTheme.colorScheme.error,
                    statusState = homeScreenState.statusState
                )

                CircleIndicatorWithText(
                    indicatorText = "POST",
                    primaryBorderColor = MaterialTheme.colorScheme.primaryContainer,
                    primaryBackgroundColor = MaterialTheme.colorScheme.primary,
                    errorBorderColor = MaterialTheme.colorScheme.errorContainer,
                    errorBackgroundColor = MaterialTheme.colorScheme.error,
                    statusState = homeScreenState.postState
                )

                CircleIndicatorWithText(
                    indicatorText = "GET",
                    primaryBorderColor = MaterialTheme.colorScheme.primaryContainer,
                    primaryBackgroundColor = MaterialTheme.colorScheme.primary,
                    errorBorderColor = MaterialTheme.colorScheme.errorContainer,
                    errorBackgroundColor = MaterialTheme.colorScheme.error,
                    statusState = homeScreenState.getState
                )
            }


        }

        if (homeScreenState.loadingState) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
            ) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }

}