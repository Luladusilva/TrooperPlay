package com.example.trooperplay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.trooperplay.ui.game.GameView
import com.example.trooperplay.ui.navigation.AppNavGraph
import com.example.trooperplay.ui.theme.TrooperPlayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrooperPlayTheme {
                val navController = rememberNavController()
                AppNavGraph(navController)
            }
        }

    }
}

@Composable
fun GameScreen() {
    GameView()   // <-- AQUÍ VA TU JUEGO DIRECTAMENTE
}

@Preview
@Composable
fun PreviewGame() {
    GameScreen()
}
