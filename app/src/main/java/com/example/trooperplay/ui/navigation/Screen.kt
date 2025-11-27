package com.example.trooperplay.ui.navigation

sealed class Screen (val route: String) {
    object Start : Screen("start")
    object Game : Screen("game")
}