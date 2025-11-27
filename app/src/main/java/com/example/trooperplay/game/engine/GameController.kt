package com.example.trooperplay.game.engine

import com.example.trooperplay.game.logic.CollisionDetector
import com.example.trooperplay.game.objects.Bullet
import com.example.trooperplay.game.objects.Enemy
import com.example.trooperplay.game.objects.Player

class GameController(
    private val player: Player
) {
    var lives = 3
    val bullets = mutableListOf<Bullet>()
    val enemies = mutableListOf<Enemy>()

    fun update(canvasWidth: Float) {

        // Mover balas
        bullets.forEach { it.update() }
        bullets.removeAll { it.pos.x > canvasWidth }

        // Mover enemigos
        enemies.forEach { it.update() }
        enemies.removeAll { it.pos.x < -100f }

        // Colisiones
        val deadEnemies = mutableListOf<Enemy>()
        val usedBullets = mutableListOf<Bullet>()

        enemies.forEach { enemy ->

            // Colisión jugador–enemigo
            if (CollisionDetector.hit(player, enemy)) {
                lives = maxOf(0, lives - 1)
                deadEnemies.add(enemy)
            }

            // Colisión bala–enemigo
            bullets.forEach { bullet ->
                if (CollisionDetector.hit(bullet, enemy)) {
                    deadEnemies.add(enemy)
                    usedBullets.add(bullet)
                }
            }
        }

        enemies.removeAll(deadEnemies)
        bullets.removeAll(usedBullets)
    }
}
