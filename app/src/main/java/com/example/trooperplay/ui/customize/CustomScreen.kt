package com.example.trooperplay.ui.screens.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.trooperplay.domain.model.StarWarsCharacter

@Composable
fun CustomScreen(
    playerName: String,
    characters: List<StarWarsCharacter>,
    onCharacterSelected: (StarWarsCharacter) -> Unit = {}
) {
    var selectedCharacter by remember { mutableStateOf<StarWarsCharacter?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {

        // 🔹 Grid que lista los personajes
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(characters) { character ->
                CharacterCard(
                    character = character,
                    onClick = {
                        selectedCharacter = character
                        onCharacterSelected(character)
                    }
                )
            }
        }

        // 🔹 Modal flotante
        selectedCharacter?.let { char ->
            CharacterDetailModal(
                character = char,
                onDismiss = { selectedCharacter = null }
            )
        }
    }
}
