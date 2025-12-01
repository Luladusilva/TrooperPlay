package com.example.trooperplay.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.trooperplay.data.datastore.SettingsDataStore
import com.example.trooperplay.ui.screens.StartScreen
import com.example.trooperplay.ui.game.GameScreen
import com.example.trooperplay.ui.settings.SettingsScreen
import com.example.trooperplay.ui.settings.SettingsViewModel
import com.example.trooperplay.ui.settings.SettingsViewModelFactory
import com.google.common.base.Defaults.defaultValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trooperplay.ui.game.GameViewModel
import com.example.trooperplay.ui.game.GameViewModelFactory
import com.example.trooperplay.ui.screens.GameOverScreen
import com.example.trooperplay.ui.screens.PauseScreen

@Composable
fun AppNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.Start.route
    ) {

        composable(Screen.Start.route) {
            StartScreen(
                onStartClick = { name ->
                    navController.navigate(Screen.GameWithName.passName(name))
                }
            )
        }

        composable(
            route = Screen.GameWithName.route,
            arguments = listOf(navArgument("playerName") { defaultValue = "" })
        ) { backStackEntry ->

            val name = backStackEntry.arguments?.getString("playerName") ?: ""
            val context = LocalContext.current

            // 👉 Crear GameViewModel usando factory
            val gameViewModel: GameViewModel = viewModel(
                factory = GameViewModelFactory(
                    SettingsDataStore(context)      // <--- pasa el DataStore aquí
                ))

            GameScreen(
                playerName = name,
                viewModel = gameViewModel,
                navController = navController,
                onOpenSettings = { navController.navigate(Screen.Settings.route) }
            )
        }

        composable(Screen.Settings.route) {
            val context = LocalContext.current

            val vm: SettingsViewModel = viewModel(
                factory = SettingsViewModelFactory(
                    SettingsDataStore(context)
                )
            )

            SettingsScreen(
                viewModel = vm,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Pause.route) { backStackEntry ->
            val context = LocalContext.current

            val gameViewModel: GameViewModel = viewModel(
                backStackEntry,
                factory = GameViewModelFactory(SettingsDataStore(context))
            )

            PauseScreen(
                onResume = { navController.popBackStack() },
                onExit = { navController.navigate(Screen.Start.route) }
            )
        }


        composable(Screen.GameOver.route) { backStackEntry ->
            val context = LocalContext.current

            val gameViewModel: GameViewModel = viewModel(
                backStackEntry,
                factory = GameViewModelFactory(SettingsDataStore(context))
            )

            GameOverScreen(
                onRetry = {
                    gameViewModel.restartGame()
                    navController.navigate(Screen.Game.route) {
                        popUpTo(Screen.GameOver.route) { inclusive = true }
                    }
                },
                onExit = { navController.navigate(Screen.Start.route) }
            )
        }



    }
}
