package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.Screen
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.DuelLobbyScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LeaderboardScreen
import com.example.ui.screens.LeagueScreen
import com.example.ui.screens.Mode1GuessScreen
import com.example.ui.screens.Mode2XoxScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.GameViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FutbolArenaApp(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun FutbolArenaApp(
    viewModel: GameViewModel,
    modifier: Modifier = Modifier
) {
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val profile by viewModel.profile.collectAsStateWithLifecycle()

    androidx.compose.foundation.layout.Box(modifier = modifier.fillMaxSize()) {
        when (val screen = currentScreen) {
            is Screen.Auth -> {
                AuthScreen(
                    viewModel = viewModel,
                    onSuccess = { viewModel.navigateTo(Screen.Home) }
                )
            }
            is Screen.Home -> {
                HomeScreen(
                    viewModel = viewModel,
                    profile = profile,
                    onNavigate = { viewModel.navigateTo(it) }
                )
            }
            is Screen.Mode1Guess -> {
                Mode1GuessScreen(
                    viewModel = viewModel,
                    isDuel = screen.isDuel,
                    isPassAndPlay = screen.isPassAndPlay,
                    opponentName = screen.opponentName,
                    onNavigateBack = { viewModel.navigateBack() }
                )
            }
            is Screen.Mode2Xox -> {
                Mode2XoxScreen(
                    viewModel = viewModel,
                    isPassAndPlay = screen.isPassAndPlay,
                    opponentName = screen.opponentName,
                    onNavigateBack = { viewModel.navigateBack() }
                )
            }
            is Screen.DuelLobby -> {
                DuelLobbyScreen(
                    viewModel = viewModel,
                    onNavigate = { viewModel.navigateTo(it) },
                    onNavigateBack = { viewModel.navigateBack() }
                )
            }
            is Screen.Leaderboard -> {
                LeaderboardScreen(
                    viewModel = viewModel,
                    onNavigateBack = { viewModel.navigateBack() }
                )
            }
            is Screen.League -> {
                LeagueScreen(
                    viewModel = viewModel,
                    onNavigateBack = { viewModel.navigateBack() }
                )
            }
            is Screen.Profile -> {
                ProfileScreen(
                    viewModel = viewModel,
                    onNavigateBack = { viewModel.navigateBack() }
                )
            }
        }
    }
}
