package com.psoft.tetrisgame.domain.models

data class TetrisPiece(
    val type: PieceType,
    var position: Position,
    val shape: Array<IntArray> = type.shape.map { it.clone() }.toTypedArray()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TetrisPiece

        if (type != other.type) return false
        if (position != other.position) return false
        if (!shape.contentDeepEquals(other.shape)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + position.hashCode()
        result = 31 * result + shape.contentDeepHashCode()
        return result
    }

    fun rotate(): TetrisPiece {
        val size = shape.size
        val rotated = Array(size) { IntArray(size) }
        for (i in 0 until size) {
            for (j in 0 until size) {
                rotated[j][size - 1 - i] = shape[i][j]
            }
        }
        return TetrisPiece(type, position, rotated)
    }
}