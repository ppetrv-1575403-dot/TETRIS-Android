package com.psoft.tetrisgame.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "high_scores")
data class HighScoreEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val score: Int,
    val date: Long = System.currentTimeMillis()
)

@androidx.room.Dao
interface HighScoreDao {
    @androidx.room.Insert
    suspend fun insert(highScore: HighScoreEntity)

    @androidx.room.Query("SELECT MAX(score) FROM high_scores")
    suspend fun getHighestScore(): Int?

    @androidx.room.Query("SELECT * FROM high_scores ORDER BY score DESC LIMIT 10")
    suspend fun getTopScores(): List<HighScoreEntity>
}