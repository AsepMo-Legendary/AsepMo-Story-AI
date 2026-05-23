package com.example.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.data.SettingsManager
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.StoryScreen

@Composable
fun AppNavigation(
    settingsManager: SettingsManager,
    viewModel: MainViewModel,
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = "story") {
        composable("story") {
            StoryScreen(
                onNavigateToHome = {
                    navController.navigate("home") {
                        popUpTo("story") { inclusive = true }
                    }
                }
            )
        }
        composable("home") {
            HomeScreen(settingsManager, viewModel)
        }
    }
}
