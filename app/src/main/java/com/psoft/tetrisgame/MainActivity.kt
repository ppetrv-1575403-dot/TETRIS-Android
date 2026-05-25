package com.psoft.tetrisgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.psoft.tetrisgame.presentation.game.GameViewModel
import com.psoft.tetrisgame.presentation.menu.MenuScreen
import com.psoft.tetrisgame.presentation.theme.TetrisTheme
import com.tetris.presentation.game.GameScreen
import com.tetris.presentation.game.GameViewModel
import com.tetris.presentation.menu.MenuScreen
import com.tetris.presentation.theme.TetrisTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TetrisTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "menu"
                    ) {
                        composable("menu") {
                            MenuScreen(
                                onStartGame = {
                                    navController.navigate("game") {
                                        popUpTo("menu") { inclusive = false }
                                    }
                                }
                            )
                        }

                        composable("game") {
                            val viewModel: GameViewModel = viewModel()
                            GameScreen(
                                viewModel = viewModel,
                                onBackPressed = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}