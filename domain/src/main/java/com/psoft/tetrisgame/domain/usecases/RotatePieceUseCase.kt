package com.psoft.tetrisgame.domain.usecases

import com.psoft.tetrisgame.domain.models.Position
import com.psoft.tetrisgame.domain.models.TetrisPiece
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RotatePieceUseCase @Inject constructor(
    private val collisionChecker: CollisionChecker
) {

    operator fun invoke(
        currentPiece: MutableStateFlow<TetrisPiece?>,
        board: MutableStateFlow<Array<IntArray>>
    ) {
        val piece = currentPiece.value ?: return

        val rotatedPiece = piece.rotate()

        // Try to rotate with wall kicks
        val offsets = listOf(
            Position(0, 0),
            Position(-1, 0),
            Position(1, 0),
            Position(0, -1),
            Position(-2, 0),
            Position(2, 0)
        )

        for (offset in offsets) {
            val newPosition = Position(piece.position.x + offset.x, piece.position.y + offset.y)
            if (!collisionChecker.check(rotatedPiece, newPosition, board.value)) {
                currentPiece.update { rotatedPiece.copy(position = newPosition) }
                return
            }
        }
    }
}