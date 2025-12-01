package com.example.trooperplay.game.objects

import android.graphics.RectF
import androidx.compose.ui.geometry.Offset
import com.example.trooperplay.game.objects.Collidable


data class Bullet(
    override var pos: Offset,
    override val width: Float = 20f,
    override val height: Float = 20f,
    val speed: Float = 20f
) : Collidable {

    fun update() {
        pos = Offset(pos.x + speed, pos.y)
    }
}

