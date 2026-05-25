package com.psoft.tetrisgame.presentation.di

import android.content.Context
import androidx.room.Room
import com.psoft.tetrisgame.data.database.GameDatabase
import com.psoft.tetrisgame.data.repository.GameRepository
import com.psoft.tetrisgame.data.repository.GameRepositoryImpl
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
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGameDatabase(@ApplicationContext context: Context): GameDatabase {
        return Room.databaseBuilder(
            context,
            GameDatabase::class.java,
            "tetris_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideGameRepository(database: GameDatabase): GameRepository {
        return GameRepositoryImpl(database)
    }

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