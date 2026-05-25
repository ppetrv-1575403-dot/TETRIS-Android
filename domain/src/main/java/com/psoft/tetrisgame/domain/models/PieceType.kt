package com.psoft.tetrisgame.domain.models

enum class PieceType(val shape: Array<IntArray>, val color: Int) {
    I(
        shape = arrayOf(
            intArrayOf(0, 0, 0, 0),
            intArrayOf(1, 1, 1, 1),
            intArrayOf(0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0)
        ),
        color = 0xFF00E5FF.toInt()
    ),
    O(
        shape = arrayOf(
            intArrayOf(0, 0, 0, 0),
            intArrayOf(0, 1, 1, 0),
            intArrayOf(0, 1, 1, 0),
            intArrayOf(0, 0, 0, 0)
        ),
        color = 0xFFFFEB3B.toInt()
    ),
    T(
        shape = arrayOf(
            intArrayOf(0, 0, 0, 0),
            intArrayOf(0, 1, 0, 0),
            intArrayOf(1, 1, 1, 0),
            intArrayOf(0, 0, 0, 0)
        ),
        color = 0xFF9C27B0.toInt()
    ),
    L(
        shape = arrayOf(
            intArrayOf(0, 0, 0, 0),
            intArrayOf(1, 0, 0, 0),
            intArrayOf(1, 1, 1, 0),
            intArrayOf(0, 0, 0, 0)
        ),
        color = 0xFFFF9800.toInt()
    ),
    J(
        shape = arrayOf(
            intArrayOf(0, 0, 0, 0),
            intArrayOf(0, 0, 1, 0),
            intArrayOf(1, 1, 1, 0),
            intArrayOf(0, 0, 0, 0)
        ),
        color = 0xFF2196F3.toInt()
    ),
    S(
        shape = arrayOf(
            intArrayOf(0, 0, 0, 0),
            intArrayOf(0, 1, 1, 0),
            intArrayOf(1, 1, 0, 0),
            intArrayOf(0, 0, 0, 0)
        ),
        color = 0xFF4CAF50.toInt()
    ),
    Z(
        shape = arrayOf(
            intArrayOf(0, 0, 0, 0),
            intArrayOf(1, 1, 0, 0),
            intArrayOf(0, 1, 1, 0),
            intArrayOf(0, 0, 0, 0)
        ),
        color = 0xFFF44336.toInt()
    );

    companion object {
        fun random(): PieceType = values().random()
    }
}