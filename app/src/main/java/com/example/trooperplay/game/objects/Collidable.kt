package com.example.trooperplay.game.objects

import android.graphics.RectF
import androidx.compose.ui.geometry.Offset

interface Collidable {

        val pos: Offset   // posición del centro del sprite
        val width: Float
        val height: Float

        val centerX: Float get() = pos.x
        val centerY: Float get() = pos.y

        val hitRadius: Float
                get() = maxOf(width, height) / 2f

        fun getRect(): RectF =
                RectF(
                        pos.x - width / 2f,
                        pos.y - height / 2f,
                        pos.x + width / 2f,
                        pos.y + height / 2f
                )
}
