package com.psoft.tetrisgame.domain.usecases

import com.psoft.tetrisgame.domain.models.GameConstants
import com.psoft.tetrisgame.domain.models.GameState
import com.psoft.tetrisgame.domain.models.GameStats
import com.psoft.tetrisgame.domain.models.PieceType
import com.psoft.tetrisgame.domain.models.Position
import com.psoft.tetrisgame.domain.models.TetrisPiece
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StartGameUseCase @Inject constructor() {

    operator fun invoke(
        gameState: MutableStateFlow<GameState>,
        board: MutableStateFlow<Array<IntArray>>,
        stats: MutableStateFlow<GameStats>,
        currentPiece: MutableStateFlow<TetrisPiece?>,
        nextPiece: MutableStateFlow<TetrisPiece?>
    ) {
        // Reset board
        board.update { Array(GameConstants.BOARD_HEIGHT) { IntArray(GameConstants.BOARD_WIDTH) } }

        // Reset stats
        stats.update { it.copy(score = 0, level = 1, linesCleared = 0) }

        // Generate new pieces
        val newPiece = generateRandomPiece()
        currentPiece.update { newPiece.copy(position = Position(3, 0)) }
        nextPiece.update { generateRandomPiece() }

        // Start game
        gameState.update { GameState.PLAYING }
    }

    private fun generateRandomPiece(): TetrisPiece {
        val randomType = PieceType.random()
        return TetrisPiece(randomType, Position(0, 0))
    }
}