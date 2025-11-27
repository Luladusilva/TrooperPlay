package com.example.trooperplay.ui.game

import androidx.compose.runtime.Composable
import com.example.trooperplay.ui.settings.SettingsViewModel

@Composable
fun GameScreen(
    playerName: String,
    onOpenSettings: () -> Unit
) {
    GameView(
        playerName = playerName,
        onOpenSettings = onOpenSettings
    )
}

