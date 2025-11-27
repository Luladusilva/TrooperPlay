package com.example.trooperplay.game.objects

import android.graphics.RectF
import androidx.compose.ui.geometry.Offset
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.sqrt

data class Player(
    var pos: Offset,
    val width: Float,
    val height: Float,
) : Collidable {

    // tamaño útil (radio aproximado) — puedes usar width/2 o height/2 según necesites
    val size: Float
        get() = max(width, height) / 2f

    override val hitRadius: Float
        get() = size

    override fun getRect(): RectF {
        // Rect centrado en pos (pos es el centro del sprite)
        return RectF(
            pos.x - width / 2f,
            pos.y - height / 2f,
            pos.x + width / 2f,
            pos.y + height / 2f
        )
    }

    // Mover el jugador hacia un target (suavizado)
    fun moveToward(target: Offset, speed: Float = 12f) {
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
