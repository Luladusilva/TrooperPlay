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

            GameScreen(
                playerName = name,
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

    }
}
