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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.trooperplay.ui.navigation.AppNavGraph

@Composable
fun GameScreen(
    playerName: String,
    viewModel: GameViewModel,
    navController: NavController,
    onOpenSettings: () -> Unit
) {
    val state by viewModel.uiState

    // Ir a Game Over
    LaunchedEffect(viewModel.isGameOver.value) {
        if (viewModel.isGameOver.value) {
            navController.navigate("gameover")
        }
    }

    // Ir a pausa
    LaunchedEffect(viewModel.isPaused.value) {
        if (viewModel.isPaused.value && !viewModel.isGameOver.value) {
            navController.navigate("pause")
        }
    }


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
            Text(text = "Trooper: $playerName", color = Color.White)

            Button(onClick = onOpenSettings) {
                Text("⚙")
            }

        }

        // Dibujo y proceso de toques
        GameView(
            gameState = state,
            onPlayerTap = viewModel::onPlayerTap,
            onFrameUpdate = viewModel::updateGameFrame,
            modifier = Modifier.zIndex(1f)
        )
    }
}
