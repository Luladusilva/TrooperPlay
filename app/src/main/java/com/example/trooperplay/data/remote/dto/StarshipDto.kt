package com.example.trooperplay.data.remote.dto

import com.google.gson.annotations.SerializedName

data class StarshipDto(
    val name: String,
    @SerializedName("hyperdrive_rating") val hyperdriveRating: String
)
