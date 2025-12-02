package com.example.trooperplay.ui.screens.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import com.example.trooperplay.domain.model.StarWarsCharacter

@Composable
fun CharacterCard(
    character: StarWarsCharacter,
    onClick: () -> Unit
) {
    val bgColor = when (character.name) {
        "Darth Vader" -> Color.Black
        "Obi-Wan Kenobi" -> Color(0xFFFFD740) // Amarillo suave
        "Stormtrooper" -> Color.White
        "Darth Maul" -> Color(0xFFB71C1C)
        else -> Color.Gray
    }

    val borderColor = when (character.name) {
        "Darth Vader" -> Color(0xFF424242)
        "Obi-Wan Kenobi" -> Color(0xFFFFC400)
        "Stormtrooper" -> Color(0xFFE0E0E0)
        "Darth Maul" -> Color(0xFF880E4F)
        else -> Color.DarkGray
    }

    Card(
        modifier = Modifier
            .size(170.dp)
            .clickable { onClick() }
            .shadow(10.dp, shape = RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(bgColor)
                .border(
                    width = 3.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(20.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = character.imageRes),
                contentDescription = null,
                modifier = Modifier.size(110.dp)
            )

            Text(
                text = character.name,
                fontSize = 16.sp,
                color = if (bgColor == Color.Black) Color.White else Color.Black,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(12.dp)
            )
        }
    }
}
