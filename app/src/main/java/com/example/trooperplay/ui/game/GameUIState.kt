package com.example.trooperplay.ui.game

import androidx.compose.ui.geometry.Offset

data class GameUiState(
    val lives: Int = 3,
    val score: Int = 0,
    val isGameOver: Boolean = false,
    val playerPos: Offset = Offset(200f, 600f),
    val bullets: List<Offset> = emptyList(),
    val enemies: List<Offset> = emptyList(),
)
