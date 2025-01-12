package com.example.webserverhmi

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(viewModel = homeViewModel,
                        innerPaddingValues = innerPadding
                    )
                }
            }
        }
    }

}

