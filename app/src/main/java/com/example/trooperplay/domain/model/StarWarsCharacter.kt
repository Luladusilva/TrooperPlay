package com.example.trooperplay.domain.model

data class StarWarsCharacter(
    val name: String,
    val species: String,
    val planet: String,
    val starshipName: String,
    val hyperdriveRating: String,
    val imageRes: Int = 0 // ← opcional, recurso local
)

