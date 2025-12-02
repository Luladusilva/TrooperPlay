package com.example.trooperplay.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.trooperplay.data.datastore.SettingsDataStore
import com.example.trooperplay.ui.screens.StartScreen
import com.example.trooperplay.ui.game.GameScreen
import com.example.trooperplay.ui.settings.SettingsScreen
import com.example.trooperplay.ui.settings.SettingsViewModel
import com.example.trooperplay.ui.settings.SettingsViewModelFactory
import com.example.trooperplay.ui.game.GameViewModel
import com.example.trooperplay.ui.game.GameViewModelFactory
import com.example.trooperplay.ui.screens.GameOverScreen
import com.example.trooperplay.ui.screens.PauseScreen
import com.example.trooperplay.ui.screens.custom.CustomScreen
import com.example.trooperplay.ui.screens.custom.selectableCharacters

@Composable
fun AppNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.Start.route
    ) {

        // START -> go to CUSTOM (pass playerName)
        composable(Screen.Start.route) {
            StartScreen(
                onStartClick = { name ->
                    navController.navigate(Screen.Custom.route(name))
                }
            )
        }

        // CUSTOM screen: receives playerName via path
        composable(
            route = Screen.Custom.route,
            arguments = listOf(
                navArgument("playerName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val playerName = backStackEntry.arguments?.getString("playerName") ?: ""

            // pass the list of selectable characters (local factory) and the playerName
            CustomScreen(
                playerName = playerName,
                characters = selectableCharacters,
                onCharacterSelected = { character ->
                    // navigate to GAME with both params
                    navController.navigate(
                        Screen.GameWithName.route(playerName, character.name)
                    )
                }
            )
        }

        // GAME screen: receives playerName and characterName via path
        composable(
            route = Screen.GameWithName.route,
            arguments = listOf(
                navArgument("playerName") { type = NavType.StringType },
                navArgument("characterName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val playerName = backStackEntry.arguments?.getString("playerName") ?: ""
            val characterName = backStackEntry.arguments?.getString("characterName") ?: ""
            val context = LocalContext.current

            val gameViewModel: GameViewModel = viewModel(
                factory = GameViewModelFactory(SettingsDataStore(context))
            )

            GameScreen(
                playerName = playerName,
                characterName = characterName,
                viewModel = gameViewModel,
                navController = navController,
                onOpenSettings = { navController.navigate(Screen.Settings.route) },
                onPause = { navController.navigate(Screen.Pause.route) }
            )
        }

        // SETTINGS
        composable(Screen.Settings.route) {
            val context = LocalContext.current
            val vm: SettingsViewModel = viewModel(
                factory = SettingsViewModelFactory(SettingsDataStore(context))
            )
            SettingsScreen(viewModel = vm, onBack = { navController.popBackStack() })
        }

        // PAUSE
        composable(Screen.Pause.route) {
            PauseScreen(onResume = { navController.popBackStack() }, onExit = {
                navController.navigate(Screen.Start.route) {
                    popUpTo(Screen.Start.route) { inclusive = true }
                }
            })
        }

        // GAME OVER
        composable(Screen.GameOver.route) {
            GameOverScreen(
                onRetry = { navController.popBackStack() },
                onExit = { navController.navigate(Screen.Start.route) }
            )
        }
    }
}
