package com.example.trooperplay.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.trooperplay.ui.screens.StartScreen
import com.example.trooperplay.ui.game.GameScreen

@Composable
fun AppNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.Start.route
    ) {

        composable(Screen.Start.route) {
            StartScreen(
                onStartClick = {
                    navController.navigate(Screen.Game.route)
                }
            )
        }

        composable(Screen.Game.route) {
            GameScreen()
        }
    }
}
