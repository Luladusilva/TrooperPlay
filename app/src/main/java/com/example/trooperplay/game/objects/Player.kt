package com.example.trooperplay.game.objects

import android.graphics.RectF
import androidx.compose.ui.geometry.Offset
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.sqrt

data class Player(
    override var pos: Offset,
    override val width: Float,
    override val height: Float,
) : Collidable {

    // Mover el jugador hacia un target (suavizado)
    fun moveToward(target: Offset, speed: Float = 14f) {
        val dx = target.x - pos.x
        val dy = target.y - pos.y
        val distSq = dx * dx + dy * dy

        if (distSq > 1f) {
            // Normalizar movimiento por velocidad (aproximación simple)
            val dist = sqrt(distSq)
            val nx = dx / dist
            val ny = dy / dist
            pos = Offset(
                pos.x + nx * speed,
                pos.y + ny * speed
            )
        }
    }
}
