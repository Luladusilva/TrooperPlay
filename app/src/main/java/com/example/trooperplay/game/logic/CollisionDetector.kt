package com.example.trooperplay.game.logic

import android.graphics.RectF
import com.example.trooperplay.game.objects.Collidable
import com.example.trooperplay.game.objects.Bullet
import com.example.trooperplay.game.objects.Enemy

object CollisionDetector {

    private fun distance(a: Collidable, b: Collidable): Float {
        val dx = a.centerX - b.centerX
        val dy = a.centerY - b.centerY
        return kotlin.math.sqrt(dx*dx + dy*dy)
    }

    fun hit(a: Collidable, b: Collidable): Boolean {

        // 1. Fast check (rect)
        if (!RectF.intersects(a.getRect(), b.getRect())) {
            return false
        }

        // 2. Precise check (circle)
        val dist = distance(a, b)
        return dist < (a.hitRadius + b.hitRadius)
    }

    fun hit(bullet: Bullet, enemy: Enemy): Boolean = hit(
        a = bullet,
        b = enemy
    )
}

