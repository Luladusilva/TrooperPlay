package com.example.trooperplay.ui.navigation

sealed class Screen(val route: String) {

    object Start : Screen("start")

    object Game : Screen("game")

    object Settings : Screen("settings")

    object Pause : Screen("pause")

    object GameOver : Screen("gameover")

    // Ejemplo con parámetro:
    object GameWithName : Screen("game/{playerName}") {
        fun passName(name: String) = "game/$name"
    }
}
