package com.example.groupphase.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.groupphase.presentation.screens.SimulateScreen
import com.example.groupphase.presentation.screens.StartScreen

@Composable
fun GroupPhaseNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "start",
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination =  startDestination
    ) {
        composable("start") {
            StartScreen(
                onNavigateSimulate = { navController.navigate("simulate") },
            )
        }
        composable("simulate") {
            SimulateScreen(
                onNavigateStart = { navController.navigate("start") },
            )
        }
    }
}