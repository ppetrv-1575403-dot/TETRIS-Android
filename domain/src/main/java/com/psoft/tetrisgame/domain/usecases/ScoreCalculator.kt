package com.psoft.tetrisgame.domain.usecases

import com.psoft.tetrisgame.domain.models.GameConstants
import com.psoft.tetrisgame.domain.models.GameStats
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoreCalculator @Inject constructor() {

    operator fun invoke(stats: GameStats, linesCleared: Int): GameStats {
        if (linesCleared == 0) return stats

        val points = when (linesCleared) {
            1 -> 40
            2 -> 100
            3 -> 300
            4 -> 1200
            else -> 0
        } * stats.level

        val newScore = stats.score + points
        val newLines = stats.linesCleared + linesCleared
        val newLevel = 1 + newLines / GameConstants.LINES_PER_LEVEL

        return stats.copy(
            score = newScore,
            level = newLevel,
            linesCleared = newLines,
            highScore = maxOf(newScore, stats.highScore)
        )
    }
}