package com.psoft.tetrisgame.data.repository

import com.psoft.tetrisgame.data.database.GameDatabase
import com.psoft.tetrisgame.data.database.GameStateEntity
import com.psoft.tetrisgame.data.database.HighScoreEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepositoryImpl @Inject constructor(
    private val database: GameDatabase
) : GameRepository {

    override suspend fun saveHighScore(score: Int) {
        database.highScoreDao().insert(HighScoreEntity(score = score))
    }

    override fun getHighScore(): Flow<Int> = flow {
        val highScore = database.highScoreDao().getHighestScore()
        emit(highScore ?: 0)
    }

    override suspend fun saveGameState(state: String) {
        database.gameStateDao().save(GameStateEntity(state = state))
    }

    override fun getGameState(): Flow<String?> = flow {
        val state = database.gameStateDao().load("current_game")
        emit(state)
    }
}