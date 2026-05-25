package com.psoft.tetrisgame.domain.models

data class GameStats(
    val score: Int = 0,
    val level: Int = 1,
    val linesCleared: Int = 0,
    val highScore: Int = 0
) {
    fun addPoints(lines: Int): GameStats {
        if (lines == 0) return this

        val points = when (lines) {
            1 -> 40
            2 -> 100
            3 -> 300
            4 -> 1200
            else -> 0
        } * level

        val newScore = score + points
        val newLines = linesCleared + lines
        val newLevel = 1 + newLines / GameConstants.LINES_PER_LEVEL

        return copy(
            score = newScore,
            level = newLevel,
            linesCleared = newLines,
            highScore = maxOf(newScore, highScore)
        )
    }
}