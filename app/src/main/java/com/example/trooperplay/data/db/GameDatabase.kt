package com.example.trooperplay.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ScoreEntity::class], version = 1)
abstract class GameDatabase : RoomDatabase() {
    abstract fun scoreDao(): ScoreDao
}
