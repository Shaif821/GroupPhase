package com.example.groupphase.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.groupphase.domain.model.Simulation
import com.example.groupphase.domain.model.Team
import com.example.groupphase.presentation.screens.result_screen.ResultScreen
import com.example.groupphase.presentation.screens.simulate_screen.SimulateScreen
import com.example.groupphase.presentation.screens.start_screen.StartScreen

@Composable
fun GroupPhaseNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "start",
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {

        composable("start") {
            StartScreen(
                onNavigateSimulate = { navController.navigate("simulate") },
                onNavigateResult = { id ->
                    navController.navigate("result/${id ?: ""}") { // Standaardwaarde is een lege string als id null is
                        popUpTo("simulate") { inclusive = true }
                    }
                },
            )
        }
        composable("simulate") {
            SimulateScreen(
                onNavigateStart = { navController.navigate("start") },
                onNavigateResult = { id ->
                    navController.navigate("result/$id") {
                        popUpTo("simulate") { inclusive = true }
                    }
                },
            )
        }
        composable("result/?id={id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")

            if (id == null) {
                navController.navigate("start")
            } else {
                ResultScreen(
                    id,
                    onNavigateStart = { navController.navigate("start") },
                )
            }
        }
    }
}