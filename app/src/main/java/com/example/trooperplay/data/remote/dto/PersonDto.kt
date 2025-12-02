package com.example.trooperplay.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PersonDto(
    val name: String,
    val species: List<String>,
    val homeworld: String,
    @SerializedName("starships") val starships: List<String>
)
