package com.psoft.tetrisgame.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_state")
data class GameStateEntity(
    @PrimaryKey
    val key: String = "current_game",
    val state: String
)

@androidx.room.Dao
interface GameStateDao {
    @androidx.room.Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun save(state: GameStateEntity)

    @androidx.room.Query("SELECT state FROM game_state WHERE key = :key")
    suspend fun load(key: String): String?
}