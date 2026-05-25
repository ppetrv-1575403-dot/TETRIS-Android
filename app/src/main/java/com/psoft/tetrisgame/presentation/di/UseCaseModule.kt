package com.psoft.tetrisgame.presentation.di

import com.psoft.tetrisgame.domain.usecases.CollisionChecker
import com.psoft.tetrisgame.domain.usecases.HardDropUseCase
import com.psoft.tetrisgame.domain.usecases.LineClearer
import com.psoft.tetrisgame.domain.usecases.MovePieceUseCase
import com.psoft.tetrisgame.domain.usecases.PlacePieceUseCase
import com.psoft.tetrisgame.domain.usecases.RotatePieceUseCase
import com.psoft.tetrisgame.domain.usecases.ScoreCalculator
import com.psoft.tetrisgame.domain.usecases.StartGameUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideCollisionChecker(): CollisionChecker {
        return CollisionChecker()
    }

    @Provides
    @Singleton
    fun provideCollisionChecker(movePieceUseCase: MovePieceUseCase,
                                placePieceUseCase: PlacePieceUseCase): HardDropUseCase {
        return HardDropUseCase(movePieceUseCase, placePieceUseCase)
    }

    @Provides
    @Singleton
    fun provideLineClearer(): LineClearer {
        return LineClearer()
    }

    @Provides
    @Singleton
    fun provideMovePieceUseCase(collisionChecker: CollisionChecker): MovePieceUseCase {
        return MovePieceUseCase(collisionChecker)
    }

    @Provides
    @Singleton
    fun providePlacePieceUseCase(lineClearer: LineClearer,
                                 scoreCalculator: ScoreCalculator): PlacePieceUseCase {
        return PlacePieceUseCase(lineClearer, scoreCalculator)
    }

    @Provides
    @Singleton
    fun provideRotatePieceUseCase(collisionChecker: CollisionChecker): RotatePieceUseCase {
        return RotatePieceUseCase(collisionChecker)
    }

    @Provides
    @Singleton
    fun provideScoreCalculator(): ScoreCalculator {
        return ScoreCalculator()
    }

    @Provides
    @Singleton
    fun provideStartGameUseCase(): StartGameUseCase {
        return StartGameUseCase()
    }
}