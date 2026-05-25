package com.psoft.tetrisgame.domain.usecases

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HardDropUseCase @Inject constructor(
    private val movePieceUseCase: MovePieceUseCase,
    private val placePieceUseCase: PlacePieceUseCase
) {

    suspend operator fun invoke(
        movePiece: suspend (dx: Int, dy: Int) -> Boolean
    ) {
        while (movePiece(0, 1)) {
            // Continue dropping
        }
    }
}