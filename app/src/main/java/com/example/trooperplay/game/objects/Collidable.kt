package com.example.trooperplay.game.objects

import android.graphics.RectF

interface Collidable {
    fun getRect(): RectF
    val hitRadius: Float
}