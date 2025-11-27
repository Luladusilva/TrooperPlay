package com.example.trooperplay.game.logic

import android.graphics.RectF
import com.example.trooperplay.game.objects.Collidable
import com.example.trooperplay.game.objects.Bullet
import com.example.trooperplay.game.objects.Enemy

object CollisionDetector {

    fun hit(a: Collidable, b: Collidable): Boolean {
        return RectF.intersects(a.getRect(), b.getRect())
    }

    // Redundante, pero útil para personalizar la colisión bala–enemigo
    fun hit(bullet: Bullet, enemy: Enemy): Boolean {
        return RectF.intersects(bullet.getRect(), enemy.getRect())
    }
}
