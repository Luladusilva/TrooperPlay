package com.example.trooperplay.ui.game

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.viewModelScope
import com.example.trooperplay.game.engine.GameController
import com.example.trooperplay.game.objects.Bullet
import com.example.trooperplay.game.objects.Enemy
import com.example.trooperplay.game.objects.Player
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.trooperplay.data.datastore.SettingsDataStore
import com.example.trooperplay.data.datastore.SettingsPreferences
import com.example.trooperplay.game.logic.CollisionDetector
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class GameViewModel(
    settingsDataStore: SettingsDataStore
) : ViewModel() {

    val settings: StateFlow<SettingsPreferences> =
        settingsDataStore.settingsFlow.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            SettingsPreferences()
        )

    var isPaused = mutableStateOf(false)
    var isGameOver = mutableStateOf(false)


    private val player = Player(
        pos = Offset(200f, 600f),
        width = 120f,
        height = 120f
    )

    private var playerTarget: Offset = player.pos

    private val controller = GameController(player)

    var uiState = mutableStateOf(GameUiState())
        private set

    init {
        startEnemySpawner()
    }

    fun updateGameFrame(canvasWidth: Float) {

        if (uiState.value.isGameOver) return

        player.moveToward(playerTarget)

        controller.update(canvasWidth)

        // 🔹 Colisiones bala - enemigo
        val bulletsToRemove = mutableListOf<Bullet>()
        val enemiesToRemove = mutableListOf<Enemy>()

        controller.bullets.forEach { bullet ->
            controller.enemies.forEach { enemy ->
                if (CollisionDetector.hit(bullet, enemy)) {
                    bulletsToRemove.add(bullet)
                    enemiesToRemove.add(enemy)

                    // sumar score
                    uiState.value = uiState.value.copy(
                        score = uiState.value.score + 10
                    )
                }
            }
        }

        controller.bullets.removeAll(bulletsToRemove)
        controller.enemies.removeAll(enemiesToRemove)

        // 🔹 Colisiones jugador - enemigo
        controller.enemies.forEach { enemy ->
            if (CollisionDetector.hit(player, enemy)) {

                controller.lives--

                if (controller.lives <= 0) {
                    uiState.value = uiState.value.copy(isGameOver = true)
                }
            }
        }

        uiState.value = uiState.value.copy(
            playerPos = player.pos,
            enemies = controller.enemies.map { it.pos },
            bullets = controller.bullets.map { it.pos },
            lives = controller.lives
        )
    }

    fun onPlayerTap(tapPos: Offset) {
        playerTarget = tapPos
        shoot()
    }

    private fun shoot() {
        controller.bullets.add(
            Bullet(Offset(player.pos.x + player.width / 2, player.pos.y))
        )
    }

    private fun startEnemySpawner() {
        viewModelScope.launch {
            while (!uiState.value.isGameOver) {
                delay(1200L)

                val spawnY = (200..1500).random().toFloat()

                controller.enemies.add(
                    Enemy(
                        pos = Offset(1400f, spawnY),
                        width = 120f,
                        height = 120f,
                        speed = (4..8).random().toFloat()
                    )
                )
            }
        }
    }

    //Métodos para pausar o reinicar partida
    fun pauseGame() { isPaused.value = true }
    fun resumeGame() { isPaused.value = false }

    fun endGame() {
        isPaused.value = true
        isGameOver.value = true
    }

    fun restartGame() {
        isPaused.value = false
        isGameOver.value = false

        // Reiniciar vidas
        controller.lives = 3

        // Reiniciar score
        uiState.value = uiState.value.copy(
            score = 0,
            lives = 3,
            isGameOver = false
        )

        // Reiniciar objetos del juego
        controller.enemies.clear()
        controller.bullets.clear()

        // Resetear posición del jugador
        player.pos = Offset(200f, 600f)

        // Reiniciar target
        playerTarget = player.pos
    }

}
