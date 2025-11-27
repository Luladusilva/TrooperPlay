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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class GameViewModel(
    settingsDataStore: SettingsDataStore
) : ViewModel(){

    val settings: StateFlow<SettingsPreferences> =
        settingsDataStore.settingsFlow.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            SettingsPreferences()
        )

    // 🔹 Inicializar Jugador
    private val player = Player(
        pos = Offset(200f, 600f),
        width = 120f,
        height = 120f
    )

    private var playerTarget: Offset = player.pos

    // 🔹 Controlador del juego
    private val controller = GameController(player)

    // 🔹 Estado visible por la UI
    var uiState = mutableStateOf(GameUiState())
        private set

    init {
        startEnemySpawner()
    }

    // 🕹 Actualizar frame
    fun updateGameFrame(canvasWidth: Float) {
        // mover suavemente al jugador
        player.moveToward(playerTarget)
        controller.update(canvasWidth)

        uiState.value = uiState.value.copy(
            playerPos = player.pos,
            enemies = controller.enemies.map { it.pos },
            bullets = controller.bullets.map { it.pos },
            lives = controller.lives
        )
    }


    // 👆 Movimiento del jugador al tocar
    fun onPlayerTap(tapPos: Offset) {
        playerTarget = tapPos
        shoot()
    }



    // 🔫 Disparar
    private fun shoot() {
        controller.bullets.add(
            Bullet(Offset(player.pos.x + player.width / 2, player.pos.y))
        )
    }

    // 👾 Spawner de enemigos
    private fun startEnemySpawner() {
        viewModelScope.launch {
            while (true) {
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


}