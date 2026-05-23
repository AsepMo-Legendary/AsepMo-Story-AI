package com.example.ui.screens

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.SettingsManager
import com.example.ui.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    settingsManager: SettingsManager,
    viewModel: MainViewModel
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var currentScreen by remember { mutableStateOf("Home") }
    val context = LocalContext.current

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                val items = listOf("Home", "About", "Gallery", "Service", "Contact", "Settings")
                items.forEach { item ->
                    NavigationDrawerItem(
                        icon = { },
                        label = { Text(item) },
                        selected = item == currentScreen,
                        onClick = {
                            scope.launch { drawerState.close() }
                            currentScreen = item
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                var showMenu by remember { mutableStateOf(false) }
                Column {
                    TopAppBar(
                        title = { Text("AsepMo - $currentScreen", fontWeight = FontWeight.SemiBold, color = Color(0xFF0F172A)) },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.White,
                            navigationIconContentColor = Color(0xFF0F172A),
                            actionIconContentColor = Color(0xFF0F172A)
                        ),
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu")
                            }
                        },
                        actions = {
                            IconButton(onClick = { showMenu = true }) {
                                Icon(Icons.Default.MoreVert, contentDescription = "Options")
                            }
                            DropdownMenu(
                                expanded = showMenu,
                                onDismissRequest = { showMenu = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Browser") },
                                    onClick = {
                                        showMenu = false
                                        val builder = CustomTabsIntent.Builder()
                                        val customTabsIntent = builder.build()
                                        customTabsIntent.launchUrl(context, Uri.parse("https://www.google.com"))
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Settings") },
                                    onClick = {
                                        showMenu = false
                                        currentScreen = "Settings"
                                    }
                                )
                            }
                        }
                    )
                    HorizontalDivider(color = Color(0xFFE2E8F0), thickness = 1.dp)
                }
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                when (currentScreen) {
                    "Home" -> HomeContent(viewModel)
                    "Gallery" -> GalleryContent()
                    "Settings" -> SettingsContent(settingsManager)
                    else -> Column(modifier = Modifier.padding(16.dp)) { Text("$currentScreen Screen") }
                }
            }
        }
    }
}
