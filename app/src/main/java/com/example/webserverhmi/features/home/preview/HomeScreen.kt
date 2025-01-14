package com.example.webserverhmi.features.home.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.webserverhmi.R
import com.example.webserverhmi.features.home.viewmodel.HomeScreenState
import com.example.webserverhmi.features.composable.TextFieldWithString
import com.example.webserverhmi.ui.theme.WebserverHMITheme


@Preview(showBackground = true, showSystemUi = true)
@PreviewLightDark
@Composable
fun HomeScreenPreview()
{
    WebserverHMITheme {
        Scaffold(
            content = { paddingValues ->
                HomeScreen(innerPaddingValues = paddingValues)
            }
        )
    }
}


@Composable
fun HomeScreen(
               innerPaddingValues: PaddingValues
) {
    val homeScreenState = HomeScreenState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPaddingValues)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextFieldWithString(textFieldValue = homeScreenState.webServer.hostAddress, stringRes = R.string.host_address,
                forceNumber = false) {

            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextFieldWithString(textFieldValue = homeScreenState.webServer.hostPort, stringRes = R.string.host_address,
                forceNumber = true) {

            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            OutlinedButton(onClick = {

            }) {
                Text(text = "Get Machine Local IP Address")
            }
            OutlinedButton(onClick = {

            }) {
                Text(text = "Start Server")
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
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Status")
                Box(
                    modifier = Modifier
                        .border(width = 8.dp, color = MaterialTheme.colorScheme.errorContainer, shape = CircleShape)
                        .size(height = 32.dp, width = 32.dp)
                        .background(MaterialTheme.colorScheme.error, shape = CircleShape)
                )
            }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "POST")
                Box(
                    modifier = Modifier
                        .border(width = 8.dp, color = MaterialTheme.colorScheme.errorContainer, shape = CircleShape)
                        .size(height = 32.dp, width = 32.dp)
                        .background(MaterialTheme.colorScheme.error, shape = CircleShape)
                )
            }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "GET")
                Box(
                    modifier = Modifier
                        .border(width = 8.dp, color = MaterialTheme.colorScheme.errorContainer, shape = CircleShape)
                        .size(height = 32.dp, width = 32.dp)
                        .background(MaterialTheme.colorScheme.error, shape = CircleShape)
                )
            }
        }
    }
}