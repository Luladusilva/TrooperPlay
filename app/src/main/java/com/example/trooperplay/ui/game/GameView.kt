package com.example.trooperplay.ui.game

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.toIntSize
import com.example.trooperplay.ui.game.GameUiState
import com.example.trooperplay.R


@Composable
fun GameView(
    gameState: GameUiState,
    onPlayerTap: (Offset) -> Unit,
    onFrameUpdate: (Float) -> Unit
) {
    val background = ImageBitmap.imageResource(id = R.drawable.fondo)
    val naveImg = ImageBitmap.imageResource(id = R.drawable.nave)
    val enemyImg = ImageBitmap.imageResource(id = R.drawable.enemy)

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { tapPos ->
                    onPlayerTap(tapPos)
                }
            }
    ) {

        val canvasWidth = size.width

        onFrameUpdate(canvasWidth)

        drawImage(
            background,
            dstSize = size.toIntSize()
        )

        drawImage(
            naveImg,
            topLeft = Offset(
                x = gameState.playerPos.x - naveImg.width / 2,
                y = gameState.playerPos.y - naveImg.height / 2
            )
        )

        gameState.enemies.forEach { enemyPos ->
            drawImage(
                enemyImg,
                topLeft = Offset(
                    x = enemyPos.x - enemyImg.width / 2,
                    y = enemyPos.y - enemyImg.height / 2
                )
            )
        }

        gameState.bullets.forEach { bullet ->
            drawCircle(
                color = Color.Yellow,
                radius = 8f,
                center = bullet
            )
        }
    }
}
