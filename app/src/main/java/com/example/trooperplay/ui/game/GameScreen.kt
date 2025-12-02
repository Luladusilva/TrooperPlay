package com.example.trooperplay.ui.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.trooperplay.domain.model.StarWarsCharacter
import com.example.trooperplay.ui.navigation.AppNavGraph

@Composable
fun GameScreen(
    playerName: String,
    characterName: String,
    viewModel: GameViewModel,
    navController: NavController,
    onOpenSettings: () -> Unit,
    onPause: () -> Unit
) {
    val state by viewModel.uiState

    // Ir a Game Over
    LaunchedEffect(state.isGameOver) {
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

    //Guardar nombre de usuario jugador
    LaunchedEffect(Unit) {
        viewModel.lastPlayerName = playerName
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
            Text(
                text = "👨‍🚀 $playerName",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "❤️ ${state.lives}",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )

            Button(
                onClick = onPause,
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0x80000000), // negro 50%
                    contentColor = Color.White
                ),
                modifier = Modifier.size(40.dp)
            ) {
                Text("II")
            }

            Button(
                onClick = onOpenSettings,
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0x80000000),
                    contentColor = Color.White
                ),
                modifier = Modifier.size(40.dp)
            ) {
                Text("⚙")   // ícono simple de ajustes
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
