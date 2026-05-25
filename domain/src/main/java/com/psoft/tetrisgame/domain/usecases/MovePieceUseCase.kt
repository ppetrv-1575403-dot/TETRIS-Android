package com.psoft.tetrisgame.domain.usecases

import com.psoft.tetrisgame.domain.models.Position
import com.psoft.tetrisgame.domain.models.TetrisPiece
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MovePieceUseCase @Inject constructor(
    private val collisionChecker: CollisionChecker
) {

    operator fun invoke(
        currentPiece: MutableStateFlow<TetrisPiece?>,
        board: MutableStateFlow<Array<IntArray>>,
        dx: Int,
        dy: Int
    ): Boolean {
        val piece = currentPiece.value ?: return false

        val newPosition = Position(piece.position.x + dx, piece.position.y + dy)

        if (!collisionChecker.check(piece, newPosition, board.value)) {
            currentPiece.update { piece.copy(position = newPosition) }
            return true
        }

        return false
    }
}