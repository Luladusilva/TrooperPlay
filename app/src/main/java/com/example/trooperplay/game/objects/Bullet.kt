package com.example.trooperplay.game.objects

import android.graphics.RectF
import androidx.compose.ui.geometry.Offset

class Bullet(
    var pos: Offset,
    val speed: Float = 20f,
    val radius: Float = 8f
) : Collidable {

    override val hitRadius: Float
        get() = radius
    override fun getRect(): RectF {
        return RectF(
            pos.x - radius,
            pos.y - radius,
            pos.x + radius,
            pos.y + radius
        )
    }

    fun update() {
        pos = Offset(pos.x + speed, pos.y)
    }
}
