package com.example.webserverhmi

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.webserverhmi.core.application.MainApplication
import com.example.webserverhmi.core.ktor.ServerManager
import com.example.webserverhmi.data.home.viewmodel.HomeViewModel
import com.example.webserverhmi.features.home.ui.HomeScreen
import com.example.webserverhmi.core.navigation.NavigationItem
import com.example.webserverhmi.features.composable.NavDrawerItem
import com.example.webserverhmi.ui.theme.WebserverHMITheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var serverManager: ServerManager
    private val homeViewModel : HomeViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        serverManager = (application as MainApplication).serverManager
        val navItems = listOf(
            NavigationItem(
                title = "Home",
                route = "Home",
                selectedIcon = Icons.Default.Home,
                unselectedIcon = Icons.Outlined.Home,
            )
        )

        setContent {


            WebserverHMITheme {
                val navController = rememberNavController()

                val homeScreenState by homeViewModel.uiState.collectAsState()
                val snackbarHostState = remember { SnackbarHostState() }
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

                val scope = rememberCoroutineScope()
                var selectedItemIndex by rememberSaveable {
                    mutableIntStateOf(0)
                }

                LaunchedEffect(homeScreenState.snackbarMessage) {
                    homeScreenState.let {
                        if(homeScreenState.snackbarMessage.isNotBlank())
                        {
                            snackbarHostState.showSnackbar(homeScreenState.snackbarMessage)
                            homeViewModel.clearSnackbar()
                        }
                    }
                }

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet {
                            Spacer(modifier = Modifier.height(16.dp))
                            navItems.forEachIndexed { index, navigationItem ->
                                NavDrawerItem(
                                    navigationItem = navigationItem,
                                    itemIndexState = index,
                                    navItemIndex = selectedItemIndex
                                ) {
                                    if(index != selectedItemIndex)
                                    {
                                        selectedItemIndex = index
                                        scope.launch {
                                            drawerState.close()
                                        }
                                        navController.navigate(navigationItem.route)
                                    }
                                }
                            }
                        }
                    }) {
                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize()
                            .imePadding(),
                        topBar = {
                            TopAppBar(
                                title = { Text("Home") },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        scope.launch { drawerState.open()  }
                                        currentFocus?.clearFocus()
                                    }) {
                                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                                    }
                                }
                            )
                        },
                        snackbarHost = {
                            Box(modifier = Modifier.fillMaxSize()) {
                                SnackbarHost(
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                        .offset(y = (-16).dp),
                                    hostState = snackbarHostState)
                            }
                        }
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = navItems[0].route,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable(navItems[0].route) { HomeScreen(viewModel = homeViewModel) }
                        }
                    }
                }
            }
        }
    }

}

