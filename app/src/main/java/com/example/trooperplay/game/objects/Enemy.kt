package com.example.trooperplay.game.objects

import android.graphics.RectF
import androidx.compose.ui.geometry.Offset
import kotlin.math.max

data class Enemy(
    var pos: Offset,
    val width: Float = 100f,
    val height: Float = 100f,
    var speed: Float = 5f
) : Collidable {

    val size: Float
        get() = max(width, height) / 2f

    override val hitRadius: Float
        get() = size

    override fun getRect(): RectF {
        return RectF(
            pos.x - width / 2f,
            pos.y - height / 2f,
            pos.x + width / 2f,
            pos.y + height / 2f
        )
    }

    fun update() {
        pos = Offset(pos.x - speed, pos.y)
    }
}
