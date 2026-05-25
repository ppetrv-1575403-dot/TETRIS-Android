package com.psoft.tetrisgame.domain.usecases

import com.psoft.tetrisgame.domain.models.GameConstants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LineClearer @Inject constructor() {

    operator fun invoke(board: Array<IntArray>): Int {
        var linesCleared = 0
        var y = GameConstants.BOARD_HEIGHT - 1

        while (y >= 0) {
            if (board[y].all { it != 0 }) {
                // Remove line
                for (i in y downTo 1) {
                    board[i] = board[i - 1].clone()
                }
                board[0] = IntArray(GameConstants.BOARD_WIDTH)
                linesCleared++
                // Check same line again
            } else {
                y--
            }
        }

        return linesCleared
    }
}