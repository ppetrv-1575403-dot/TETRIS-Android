package com.psoft.tetrisgame.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [HighScoreEntity::class, GameStateEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class GameDatabase : RoomDatabase() {
    abstract fun highScoreDao(): HighScoreDao
    abstract fun gameStateDao(): GameStateDao
}