package com.example.trooperplay.ui.screens.custom

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trooperplay.domain.model.StarWarsCharacter

@Composable
fun CharacterDetailModal(
    character: StarWarsCharacter,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        containerColor = Color(0xFF1C1C1C),
        title = {
            Text(
                text = character.name,
                color = Color.White,
                fontSize = 22.sp
            )
        },
        text = {
            Text(
                text = """
                    Species: ${character.species}
                    Planet: ${character.planet}

                    Starship: ${character.starshipName}
                    Hyperdrive rating: ${character.hyperdriveRating}
                """.trimIndent(),
                color = Color(0xFFE0E0E0),
                fontSize = 16.sp
            )
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

