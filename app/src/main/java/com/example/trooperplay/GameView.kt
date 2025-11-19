package com.example.trooperplay

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.toIntSize
import com.example.trooperplay.R
import kotlinx.coroutines.delay


@Composable
fun GameView() {

    val background = ImageBitmap.imageResource(id = R.drawable.fondo)
    val nave = ImageBitmap.imageResource(id = R.drawable.nave)
    val enemigo = ImageBitmap.imageResource(id = R.drawable.enemy)

    var navePos by remember { mutableStateOf(Offset(200f, 600f)) }
    var targetPos by remember { mutableStateOf(navePos) }

    val balas = remember { mutableStateListOf<Offset>() }
    val enemigos = remember { mutableStateListOf<Offset>() }

    // === MOVER LA NAVE HACIA EL TOQUE ===
    LaunchedEffect(Unit) {
        while (true) {
            val dx = targetPos.x - navePos.x
            val dy = targetPos.y - navePos.y
            val speed = 12f

            if (dx * dx + dy * dy > 20f) {
                navePos = Offset(
                    navePos.x + dx.coerceIn(-speed, speed),
                    navePos.y + dy.coerceIn(-speed, speed)
                )
            }

            delay(16)
        }
    }

    // === DISPARO AUTOMÁTICO CADA 300 ms ===
    LaunchedEffect(Unit) {
        while (true) {
            balas.add(Offset(navePos.x, navePos.y - 40))
            delay(300)
        }
    }

    // === CREAR ENEMIGOS CADA 1.2s ===
    LaunchedEffect(Unit) {
        while (true) {
            val yPos = (50..900).random().toFloat()
            enemigos.add(Offset(1200f, yPos))
            delay(1200)
        }
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures {
                    targetPos = it
                }
            }
    ) {

        // Fondo
        drawImage(background, dstSize = size.toIntSize())

        val canvasWidth = size.width
        val canvasHeight = size.height

        // === MOVER BALAS HACIA LA IZQUIERDA ===
        val balasAEliminar = mutableListOf<Offset>()
        val enemigosAEliminar = mutableListOf<Offset>()

        val nuevasBalas = mutableListOf<Offset>()

        balas.forEach { bala ->
            val nuevaPos = Offset(bala.x + 20f, bala.y) // derecha

            if (nuevaPos.x < canvasWidth) {
                nuevasBalas.add(nuevaPos)

                // DIBUJAR BALA (pequeño círculo)
                drawCircle(
                    color = androidx.compose.ui.graphics.Color.Yellow,
                    radius = 8f,
                    center = nuevaPos
                )
            }
        }

        balas.clear()
        balas.addAll(nuevasBalas)

        // === MOVER ENEMIGOS ===
        val nuevosEnemigos = mutableListOf<Offset>()

        enemigos.forEach { en ->
            val nuevaPos = Offset(en.x - 6f, en.y)

            // Dibujar enemigo
            drawImage(
                enemigo,
                topLeft = Offset(
                    nuevaPos.x - enemigo.width / 2,
                    nuevaPos.y - enemigo.height / 2
                )
            )

            // Borrar si salió
            if (nuevaPos.x < -100f) {
                enemigosAEliminar.add(en)
            } else {
                nuevosEnemigos.add(nuevaPos)
            }

            // Colisiones
            balas.forEach { bala ->
                val dx = bala.x - nuevaPos.x
                val dy = bala.y - nuevaPos.y
                if (dx * dx + dy * dy < 40_00f) {
                    enemigosAEliminar.add(en)
                    balasAEliminar.add(bala)
                }
            }
        }

// Aplicar cambios finales
        enemigos.clear()
        enemigos.addAll(nuevosEnemigos)
        balas.removeAll(balasAEliminar)


        // === DIBUJAR LA NAVE ===
        drawImage(
            nave,
            topLeft = Offset(
                navePos.x - nave.width / 2,
                navePos.y - nave.height / 2
            )
        )
    }
}
