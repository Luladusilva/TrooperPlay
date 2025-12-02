package com.example.trooperplay.ui.navigation

sealed class Screen(val route: String) {

    object Start : Screen("start")

    // ---- CUSTOM SCREEN ----
    // Recibe solo playerName
    object Custom : Screen("custom_screen/{playerName}") {
        fun route(playerName: String) = "custom_screen/$playerName"
    }

    // ---- GAME SCREEN ----
    // Recibe playerName y characterName
    object GameWithName : Screen("game_screen/{playerName}/{characterName}") {
        fun route(playerName: String, characterName: String) =
            "game_screen/$playerName/$characterName"
    }

    // ---- SETTINGS, PAUSE, GAME OVER ----
    object Settings : Screen("settings")
    object Pause : Screen("pause")
    object GameOver : Screen("gameover")
}
