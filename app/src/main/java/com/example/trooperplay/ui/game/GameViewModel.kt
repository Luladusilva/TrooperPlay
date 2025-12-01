package com.example.trooperplay.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trooperplay.data.datastore.SettingsDataStore
import com.example.trooperplay.data.datastore.SettingsPreferences
import com.example.trooperplay.game.engine.GameController
import com.example.trooperplay.game.logic.CollisionDetector
import com.example.trooperplay.game.objects.Bullet
import com.example.trooperplay.game.objects.Enemy
import com.example.trooperplay.game.objects.Player
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset

class GameViewModel(
    settingsDataStore: SettingsDataStore
) : ViewModel() {

    // Settings
    val settings: StateFlow<SettingsPreferences> =
        settingsDataStore.settingsFlow.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            SettingsPreferences()
        )

    // Estado general
    var uiState = mutableStateOf(GameUiState())
        private set

    var isPaused = mutableStateOf(false)
    var isGameOver = mutableStateOf(false)

    // guardar nombre del usuario jugador
    var lastPlayerName: String = ""

    // Objetos del juego
    private val player = Player(
        pos = Offset(200f, 600f),
        width = 120f,
        height = 120f
    )

    private var playerTarget: Offset = player.pos

    private val controller = GameController(player)

    init {
        startEnemySpawner()
    }

    // ---------------------------------------------------------
    // LOOP PRINCIPAL
    // ---------------------------------------------------------
    fun updateGameFrame(canvasWidth: Float) {

        if (uiState.value.isGameOver || isPaused.value) return

        // mover jugador
        player.moveToward(playerTarget)

        // mover balas, enemigos, colisiones jugador/enemigo y vidas
        controller.update(canvasWidth)

        // Verificar vidas (restar vidas en controller)
        if (controller.lives <= 0) {
            uiState.value = uiState.value.copy(isGameOver = true)
            isGameOver.value = true
            return
        }

        // -------------------------------------------------------
        // Colisiones bala - enemigo y sumar score
        // -------------------------------------------------------
        val bulletsToRemove = mutableListOf<Bullet>()
        val enemiesToRemove = mutableListOf<Enemy>()

        controller.bullets.forEach { bullet ->
            controller.enemies.forEach { enemy ->
                if (CollisionDetector.hit(bullet, enemy)) {
                    bulletsToRemove.add(bullet)
                    enemiesToRemove.add(enemy)

                    uiState.value = uiState.value.copy(
                        score = uiState.value.score + 10
                    )
                }
            }
        }

        controller.bullets.removeAll(bulletsToRemove)
        controller.enemies.removeAll(enemiesToRemove)

        // -------------------------------------------------------
        // 🔵 Actualizar UI con los datos del juego
        // -------------------------------------------------------
        uiState.value = uiState.value.copy(
            playerPos = player.pos,
            enemies = controller.enemies.map { it.pos },
            bullets = controller.bullets.map { it.pos },
            lives = controller.lives
        )
    }

    // ---------------------------------------------------------
    // 👆 Tap del jugador → mover nave + disparar
    // ---------------------------------------------------------
    fun onPlayerTap(tapPos: Offset) {
        playerTarget = tapPos
        shoot()
    }

    private fun shoot() {
        controller.bullets.add(
            Bullet(Offset(player.pos.x + player.width / 2, player.pos.y))
        )
    }

    // ---------------------------------------------------------
    // 👾 Spawner de enemigos
    // ---------------------------------------------------------
    private fun startEnemySpawner() {
        viewModelScope.launch {
            while (true) {
                if (!uiState.value.isGameOver && !isPaused.value) {

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
                delay(1200L)
            }
        }
    }

    // ---------------------------------------------------------
    // ⏸ Pausa / Resume
    // ---------------------------------------------------------
    fun pauseGame() { isPaused.value = true }
    fun resumeGame() { isPaused.value = false }

    // ---------------------------------------------------------
    // 🔄 Reiniciar partida
    // ---------------------------------------------------------
    fun restartGame() {
        isPaused.value = false
        isGameOver.value = false

        controller.lives = 3

        uiState.value = GameUiState(
            lives = 3,
            score = 0,
            level = 1,
            isGameOver = false,
            playerPos = Offset(200f, 600f)
        )

        controller.enemies.clear()
        controller.bullets.clear()

        player.pos = Offset(200f, 600f)
        playerTarget = player.pos
    }
}
