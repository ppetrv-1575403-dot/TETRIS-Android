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
class PlacePieceUseCase @Inject constructor(
    private val lineClearer: LineClearer,
    private val scoreCalculator: ScoreCalculator
) {

    operator fun invoke(
        currentPiece: MutableStateFlow<TetrisPiece?>,
        board: MutableStateFlow<Array<IntArray>>,
        stats: MutableStateFlow<GameStats>,
        nextPiece: MutableStateFlow<TetrisPiece?>,
        gameState: MutableStateFlow<GameState>
    ): Boolean {
        val piece = currentPiece.value ?: return false

        // Place piece on board
        val newBoard = board.value.map { it.clone() }.toTypedArray()
        for (i in piece.shape.indices) {
            for (j in piece.shape[i].indices) {
                if (piece.shape[i][j] != 0) {
                    val boardX = piece.position.x + j
                    val boardY = piece.position.y + i
                    if (boardY in 0 until GameConstants.BOARD_HEIGHT &&
                        boardX in 0 until GameConstants.BOARD_WIDTH) {
                        newBoard[boardY][boardX] = piece.type.color
                    }
                }
            }
        }

        // Clear lines and update score
        val clearedLines = lineClearer(newBoard)
        stats.update { scoreCalculator(it, clearedLines) }
        board.update { newBoard }

        // Spawn next piece
        val next = nextPiece.value
        if (next != null) {
            val newPiece = next.copy(position = Position(3, 0))
            currentPiece.update { newPiece }
            nextPiece.update { generateRandomPiece() }

            // Check game over
            if (CollisionChecker().check(newPiece, newPiece.position, newBoard)) {
                gameState.update { GameState.GAME_OVER }
                return false
            }
        }

        return true
    }

    private fun generateRandomPiece(): TetrisPiece {
        val randomType = PieceType.random()
        return TetrisPiece(randomType, Position(0, 0))
    }
}