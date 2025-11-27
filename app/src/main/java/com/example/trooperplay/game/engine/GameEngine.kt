package com.example.trooperplay.game.engine

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import com.example.trooperplay.game.objects.Player
import com.example.trooperplay.game.objects.Bullet
import com.example.trooperplay.game.objects.Enemy
import kotlinx.coroutines.delay
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import com.example.trooperplay.game.engine.GameController

class GameEngine(
    private val controller: GameController,
    private val player: Player
) {

    var target by mutableStateOf(player.pos)

    suspend fun start() = coroutineScope {

        // --------------------------
        // 1. Movimiento del jugador
        // --------------------------
        launch {
            while (true) {
                player.moveToward(target)
                delay(16) // ~60 FPS
            }
        }

        // --------------------------
        // 2. Auto-disparo del jugador
        // --------------------------
        launch {
            while (true) {
                controller.bullets.add(
                    Bullet(
                        pos = Offset(player.pos.x + player.width / 2f, player.pos.y),
                        speed = 25f
                    )
                )
                delay(300)
            }
        }

        // --------------------------
        // 3. Spawner de enemigos
        // --------------------------
        launch {
            while (true) {
                controller.enemies.add(
                    Enemy(
                        pos = Offset(
                            x = 1200f,          // borde derecho de la pantalla
                            y = (50..900).random().toFloat()
                        ),
                        speed = 6f
                    )
                )
                delay(1200)
            }
        }
    }
}
