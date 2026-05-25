package com.psoft.tetrisgame.domain.usecases

import com.psoft.tetrisgame.domain.models.GameConstants
import com.psoft.tetrisgame.domain.models.Position
import com.psoft.tetrisgame.domain.models.TetrisPiece
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CollisionChecker @Inject constructor() {

    fun check(piece: TetrisPiece, position: Position, board: Array<IntArray>): Boolean {
        for (i in piece.shape.indices) {
            for (j in piece.shape[i].indices) {
                if (piece.shape[i][j] != 0) {
                    val boardX = position.x + j
                    val boardY = position.y + i

                    // Check borders
                    if (boardX < 0 || boardX >= GameConstants.BOARD_WIDTH ||
                        boardY >= GameConstants.BOARD_HEIGHT) {
                        return true
                    }

                    // Check collision with placed pieces
                    if (boardY >= 0 && board[boardY][boardX] != 0) {
                        return true
                    }
                }
            }
        }
        return false
    }
}