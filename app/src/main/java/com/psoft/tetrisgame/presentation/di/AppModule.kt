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

}