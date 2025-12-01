package com.example.trooperplay.game.objects

import androidx.compose.ui.geometry.Offset

data class Enemy(
    override var pos: Offset,
    override val width: Float = 100f,
    override val height: Float = 100f,
    var speed: Float = 5f
) : Collidable {
    fun update() {
        pos = Offset(pos.x - speed, pos.y)
    }
}
