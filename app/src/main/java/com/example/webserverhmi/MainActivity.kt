package com.example.webserverhmi

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.webserverhmi.core.application.MainApplication
import com.example.webserverhmi.core.ktor.ServerManager
import com.example.webserverhmi.data.home.viewmodel.HomeViewModel
import com.example.webserverhmi.features.home.ui.HomeScreen
import com.example.webserverhmi.ui.theme.WebserverHMITheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var serverManager: ServerManager
    private val homeViewModel : HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        serverManager = (application as MainApplication).serverManager

        setContent {
            WebserverHMITheme {

                val homeScreenState by homeViewModel.uiState.collectAsState()
                val snackbarHostState = remember { SnackbarHostState() }

                LaunchedEffect(homeScreenState.snackbarMessage) {
                    homeScreenState.let {
                        if(homeScreenState.snackbarMessage.isNotBlank())
                        {
                            snackbarHostState.showSnackbar(homeScreenState.snackbarMessage)
                            homeViewModel.clearSnackbar()
                        }
                    }
                }


                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding(),
                    snackbarHost = {
                            Box(modifier = Modifier.fillMaxSize()) {
                                SnackbarHost(
                                    modifier = Modifier.align(Alignment.BottomCenter)
                                        .offset(y = (-16).dp),
                                    hostState = snackbarHostState)
                            }
                                   },
                ) { innerPadding ->
                    HomeScreen(viewModel = homeViewModel,
                        innerPaddingValues = innerPadding
                    )
                }
            }
        }
    }

}

