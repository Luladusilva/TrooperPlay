package com.example.trooperplay.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ScoreEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val points: Int
)
