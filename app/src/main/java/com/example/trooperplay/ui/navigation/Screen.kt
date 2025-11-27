package com.example.trooperplay.ui.navigation

sealed class Screen(val route: String) {

    object Start : Screen("start")

    object Game : Screen("game")

    object Settings : Screen("settings")

    // Ejemplo con parámetro:
    object GameWithName : Screen("game/{playerName}") {
        fun passName(name: String) = "game/$name"
    }
}
