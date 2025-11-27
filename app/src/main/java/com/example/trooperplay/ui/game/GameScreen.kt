package com.example.trooperplay.ui.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.ui.zIndex

@Composable
fun GameScreen(
    playerName: String,
    viewModel: GameViewModel,
    onOpenSettings: () -> Unit
) {
    val state by viewModel.uiState

    Box(modifier = Modifier.fillMaxSize()) {

        // HUD superior
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .zIndex(2f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Pilot: $playerName", color = Color.White)

            Button(onClick = onOpenSettings) {
                Text("⚙ Settings")
            }
        }

        // Aquí sí va el GameView que solo dibuja y procesa toques
        GameView(
            gameState = state,
            onPlayerTap = viewModel::onPlayerTap,
            onFrameUpdate = viewModel::updateGameFrame,
            modifier = Modifier.zIndex(1f)
        )
    }
}
