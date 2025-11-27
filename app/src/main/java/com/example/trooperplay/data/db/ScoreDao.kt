package com.example.trooperplay.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ScoreDao {
    @Insert
    suspend fun insert(score: ScoreEntity)

    @Query("SELECT * FROM ScoreEntity ORDER BY points DESC")
    suspend fun listScores(): List<ScoreEntity>
}
