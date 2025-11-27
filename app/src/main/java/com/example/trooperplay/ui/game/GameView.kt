package com.example.trooperplay.ui.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toIntSize
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trooperplay.R

@Composable
fun GameView() {

    val viewModel: GameViewModel = viewModel()
    val state by viewModel.uiState

    val background = ImageBitmap.imageResource(id = R.drawable.fondo)
    val naveImg = ImageBitmap.imageResource(id = R.drawable.nave)
    val enemyImg = ImageBitmap.imageResource(id = R.drawable.enemy)

    var showLives by remember { mutableStateOf(false) }

    Column {

        // -----------------------------
        //       Encabezado simple
        // -----------------------------
        Text("TrooperPlay", modifier = Modifier.padding(16.dp))

        Button(onClick = { showLives = !showLives }) {
            Text(if (showLives) "Ocultar vidas" else "Mostrar vidas")
        }

        if (showLives) {
            Text("Vidas: ❤️".repeat(state.lives))
        }

        // -----------------------------
        //             CANVAS
        // -----------------------------
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures { tapPos ->
                        viewModel.onPlayerTap(tapPos)
                    }
                }
        ) {

            val canvasWidth = size.width

            // FRAME UPDATE
            viewModel.updateGameFrame(canvasWidth)

            // 📌 Dibuja fondo
            drawImage(
                background,
                dstSize = size.toIntSize()
            )

            // -----------------------------
            //     🚀 DIBUJAR NAVE
            // -----------------------------
            drawImage(
                naveImg,
                topLeft = Offset(
                    x = state.playerPos.x - naveImg.width / 2,
                    y = state.playerPos.y - naveImg.height / 2
                )
            )

            // -----------------------------
            //     💥 DIBUJAR ENEMIGOS
            // -----------------------------
            state.enemies.forEach { enemyPos ->
                drawImage(
                    enemyImg,
                    topLeft = Offset(
                        x = enemyPos.x - enemyImg.width / 2,
                        y = enemyPos.y - enemyImg.height / 2
                    )
                )
            }

            // -----------------------------
            //     🔫 DIBUJAR BALAS
            // -----------------------------
            state.bullets.forEach { bullet ->
                drawCircle(
                    color = Color.Yellow,
                    radius = 8f,
                    center = bullet
                )
            }
        }
    }
}
