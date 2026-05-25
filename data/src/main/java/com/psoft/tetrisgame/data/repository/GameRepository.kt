package com.psoft.tetrisgame.data.repository

import kotlinx.coroutines.flow.Flow

interface GameRepository {
    suspend fun saveHighScore(score: Int)
    fun getHighScore(): Flow<Int>
    suspend fun saveGameState(state: String)
    fun getGameState(): Flow<String?>
}