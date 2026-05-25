package com.psoft.tetrisgame.presentation.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.psoft.tetrisgame.domain.models.GameConstants
import com.psoft.tetrisgame.domain.models.GameState
import com.psoft.tetrisgame.domain.models.GameStats
import com.psoft.tetrisgame.domain.models.TetrisPiece
import com.psoft.tetrisgame.domain.usecases.CollisionChecker
import com.psoft.tetrisgame.domain.usecases.HardDropUseCase
import com.psoft.tetrisgame.domain.usecases.MovePieceUseCase
import com.psoft.tetrisgame.domain.usecases.PlacePieceUseCase
import com.psoft.tetrisgame.domain.usecases.RotatePieceUseCase
import com.psoft.tetrisgame.domain.usecases.StartGameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val startGameUseCase: StartGameUseCase,
    private val movePieceUseCase: MovePieceUseCase,
    private val rotatePieceUseCase: RotatePieceUseCase,
    private val hardDropUseCase: HardDropUseCase,
    private val placePieceUseCase: PlacePieceUseCase,
    private val collisionChecker: CollisionChecker
) : ViewModel() {

    // State flows
    private val _gameState = MutableStateFlow(GameState.MENU)
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    private val _gameStats = MutableStateFlow(GameStats())
    val gameStats: StateFlow<GameStats> = _gameStats.asStateFlow()

    private val _board = MutableStateFlow(Array(20) { IntArray(10) })
    val board: StateFlow<Array<IntArray>> = _board.asStateFlow()

    private val _currentPiece = MutableStateFlow<TetrisPiece?>(null)
    val currentPiece: StateFlow<TetrisPiece?> = _currentPiece.asStateFlow()

    private val _nextPiece = MutableStateFlow<TetrisPiece?>(null)
    val nextPiece: StateFlow<TetrisPiece?> = _nextPiece.asStateFlow()

    private var gameLoopJob: Job? = null
    private var lastTimestamp = 0L

    fun startGame() {
        startGameUseCase(_gameState, _board, _gameStats, _currentPiece, _nextPiece)
        startGameLoop()
    }

    fun pauseGame() {
        if (_gameState.value == GameState.PLAYING) {
            _gameState.value = GameState.PAUSED
            stopGameLoop()
        }
    }

    fun resumeGame() {
        if (_gameState.value == GameState.PAUSED) {
            _gameState.value = GameState.PLAYING
            startGameLoop()
        }
    }

    fun moveLeft() {
        if (_gameState.value != GameState.PLAYING) return
        movePieceUseCase(_currentPiece, _board, -1, 0)
    }

    fun moveRight() {
        if (_gameState.value != GameState.PLAYING) return
        movePieceUseCase(_currentPiece, _board, 1, 0)
    }

    fun moveDown() {
        if (_gameState.value != GameState.PLAYING) return

        val moved = movePieceUseCase(_currentPiece, _board, 0, 1)
        if (!moved) {
            placePieceUseCase(_currentPiece, _board, _gameStats, _nextPiece, _gameState)
        }
    }

    fun rotatePiece() {
        if (_gameState.value != GameState.PLAYING) return
        rotatePieceUseCase(_currentPiece, _board)
    }

    fun hardDrop() {
        if (_gameState.value != GameState.PLAYING) return

        viewModelScope.launch {
            hardDropUseCase { dx, dy ->
                movePieceUseCase(_currentPiece, _board, dx, dy)
            }
            placePieceUseCase(_currentPiece, _board, _gameStats, _nextPiece, _gameState)
        }
    }

    private fun startGameLoop() {
        gameLoopJob?.cancel()
        gameLoopJob = viewModelScope.launch {
            lastTimestamp = System.currentTimeMillis()
            while (_gameState.value == GameState.PLAYING) {
                val currentTime = System.currentTimeMillis()
                val fallSpeed = calculateFallSpeed()

                if (currentTime - lastTimestamp >= fallSpeed) {
                    moveDown()
                    lastTimestamp = currentTime
                }

                delay(16) // ~60 FPS
            }
        }
    }

    private fun stopGameLoop() {
        gameLoopJob?.cancel()
        gameLoopJob = null
    }

    private fun calculateFallSpeed(): Long {
        val level = _gameStats.value.level
        return maxOf(
            GameConstants.MIN_FALL_SPEED,
            GameConstants.INITIAL_FALL_SPEED - (level - 1) * GameConstants.SPEED_INCREMENT)
    }

    override fun onCleared() {
        super.onCleared()
        stopGameLoop()
    }
}