package com.example.webserverhmi.features.home.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.webserverhmi.data.home.state.HomeScreenState
import com.example.webserverhmi.data.home.viewmodel.HomeViewModel
import com.example.webserverhmi.features.composable.TextFieldWithString
import com.example.webserverhmi.ui.theme.WebserverHMITheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow



@Composable
fun HomeScreen(viewModel: HomeViewModel,
               innerPaddingValues: PaddingValues
) {

    val homeScreenState by viewModel.uiState.collectAsState()

    LaunchedEffect(homeScreenState) {
        Log.d("HomeScreen", "Host Address: ${homeScreenState.hostAddress}, Host Port: ${homeScreenState.hostPort}")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPaddingValues)
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextFieldWithString(textFieldValue = homeScreenState.hostAddress, forceNumber = false) { newAddress ->
                viewModel.hostAddressUpdate(newAddress = newAddress)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextFieldWithString(textFieldValue = homeScreenState.hostPort, forceNumber = true) { newPort ->
                viewModel.hostPortUpdate(newPort)

            }
        }
        
        OutlinedButton(onClick = { viewModel.startServer() }) {
            Text(text = "Start Server")
        }
    }
}