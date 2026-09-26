package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.data.model.FootballPlayer
import com.example.data.model.XoxPlayer
import com.example.ui.components.ClueComparisonRow
import com.example.ui.theme.DuelRed
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.FootballPitchGreen
import com.example.ui.theme.GoldenTrophy
import com.example.util.AppLanguage
import com.example.util.Strings
import com.example.viewmodel.GameViewModel

@Composable
fun Mode1GuessScreen(
    viewModel: GameViewModel,
    isDuel: Boolean = false,
    isPassAndPlay: Boolean = false,
    opponentName: String = "Rakip",
    onNavigateBack: () -> Unit
) {
    BackHandler {
        onNavigateBack()
    }

    LaunchedEffect(Unit) {
        viewModel.startMode1Game(
            isDuel = isDuel,
            isPassAndPlay = isPassAndPlay,
            opponentName = opponentName
        )
    }

    val lang by viewModel.appLanguage.collectAsStateWithLifecycle()
    val player by viewModel.mode1CurrentPlayer.collectAsStateWithLifecycle()
    val timeLeft by viewModel.mode1TimeLeft.collectAsStateWithLifecycle()
    val round by viewModel.mode1Round.collectAsStateWithLifecycle()
    val score by viewModel.mode1Score.collectAsStateWithLifecycle()
    val opponentScore by viewModel.mode1OpponentScore.collectAsStateWithLifecycle()
    val currentTurn by viewModel.mode1CurrentTurn.collectAsStateWithLifecycle()
    val attempts by viewModel.mode1Attempts.collectAsStateWithLifecycle()
    val feedback by viewModel.mode1Feedback.collectAsStateWithLifecycle()
    val feedbackSuccess by viewModel.mode1FeedbackSuccess.collectAsStateWithLifecycle()
    val revealedPlayer by viewModel.mode1RevealedPlayer.collectAsStateWithLifecycle()
    val isGameOver by viewModel.mode1IsGameOver.collectAsStateWithLifecycle()
    val searchQuery by viewModel.mode1SearchQuery.collectAsStateWithLifecycle()
    val suggestions by viewModel.mode1Suggestions.collectAsStateWithLifecycle()

    val isGreenTurn = currentTurn == XoxPlayer.GREEN
    val turnColor = if (isGreenTurn) FootballPitchGreen else DuelRed
    val activeTeamName = if (isGreenTurn) {
        if (lang == AppLanguage.TR) "1. Oyuncu (Yeşil)" else "Player 1 (Green)"
    } else {
        if (isPassAndPlay || isDuel) {
            if (lang == AppLanguage.TR) "2. Oyuncu (Kırmızı)" else "Player 2 (Red)"
        } else opponentName
    }

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (timeLeft <= 5) 1.2f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(400),
            repeatMode = RepeatMode.Reverse
        ),
        label = "timerPulse"
    )

    val timerProgress = (timeLeft / 15f).coerceIn(0f, 1f)
    val timerColor by animateColorAsState(
        targetValue = when {
            timeLeft <= 4 -> DuelRed
            timeLeft <= 8 -> GoldenTrophy
            else -> FootballPitchGreen
        },
        label = "timerColor"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Mode 1: Blue Background with tactical pitch texture
        Image(
            painter = painterResource(id = R.drawable.img_mode_blue_bg),
            contentDescription = "Mavi Taktik Arka Plan",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Gradient overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0x990A192F),
                            Color(0x44001E3D),
                            Color(0xEE05101E)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // TOP NAVIGATION BAR
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.testTag("mode1_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Geri",
                        tint = Color.White
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = if (lang == AppLanguage.TR) "15-SANİYE OYUNCU TAHMİNİ" else "15-SECOND PLAYER GUESS",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = if (lang == AppLanguage.TR) "Tur $round / 5" else "Round $round / 5",
                        color = ElectricBlue,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF0F2648))
                        .border(1.dp, Color(0xFF1E4976), RoundedCornerShape(10.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (lang == AppLanguage.TR) "Skor: $score - $opponentScore" else "Score: $score - $opponentScore",
                        color = GoldenTrophy,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // TURN INDICATOR & 15-SECOND TIMER
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xDD0E2342)),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, turnColor),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .scale(pulseScale)
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(timerColor)
                    ) {
                        Text(
                            text = "$timeLeft",
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "SIRA: $activeTeamName",
                                color = turnColor,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black
                            )
                            Text(
                                text = "15 Sn.",
                                color = Color(0xFFB0BEC5),
                                fontSize = 11.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        LinearProgressIndicator(
                            progress = { timerProgress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = timerColor,
                            trackColor = Color(0x66000000)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // FEEDBACK BANNER
            AnimatedVisibility(
                visible = feedback != null,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                feedback?.let { msg ->
                    Card(
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (feedbackSuccess) Color(0xEE1B5E20) else Color(0xDD1E293B)
                        ),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (feedbackSuccess) FootballPitchGreen else turnColor
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = msg,
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                    }
                }
            }

            // INPUT / AUTOCOMPLETE FIELD (Enabled for the active user)
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.onMode1QueryChanged(it) },
                label = { Text("Oyuncu ismi ara...") },
                placeholder = { Text("Örn: Arda Güler, Haaland, Vinicius...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = turnColor
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xDD0B1E38),
                    unfocusedContainerColor = Color(0xAA0B1E38),
                    focusedBorderColor = turnColor,
                    unfocusedBorderColor = Color(0xFF1E88E5),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("mode1_guess_input")
            )

            // IF THERE ARE AUTOCOMPLETE SUGGESTIONS: SHOW OVERLAY DROPDOWN
            if (suggestions.isNotEmpty()) {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xF50D274C)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF2196F3)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                        .height(180.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(suggestions) { item ->
                            Card(
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xAA1E3A60)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { viewModel.submitMode1Guess(item) }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = item.countryFlag, fontSize = 18.sp)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = item.name,
                                            color = Color.White,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "${item.team} • ${item.league} • #${item.jerseyNumber}",
                                            color = Color(0xFF90CAF9),
                                            fontSize = 10.sp
                                        )
                                    }
                                    Text(
                                        text = "Tahmin Et",
                                        color = turnColor,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // GUESS ATTEMPTS & COMPARISONS LIST
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "YAPILAN TAHMİNLER & İPUCU KARŞILAŞTIRMASI",
                    color = Color(0xFF90CAF9),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "🟢 Doğru  🔴 Yanlış",
                    color = Color(0xFFCFD8DC),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            if (attempts.isEmpty()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("⚽", fontSize = 32.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Bir futbolcu tahmini yap!\nEşleşen ipuçları yeşil, farklı olanlar kırmızı yanacak.",
                            color = Color(0xFFB0BEC5),
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(attempts) { attempt ->
                        ClueComparisonRow(attempt = attempt)
                    }
                }
            }
        }
    }

    // GAME OVER / ROUND FINISHED DIALOG
    if (isGameOver) {
        val userScore = score
        val oppScore = opponentScore
        val isWin = userScore > oppScore
        AlertDialog(
            onDismissRequest = {},
            containerColor = Color(0xFF0D1B2A),
            title = {
                Text(
                    text = if (isWin) (if (lang == AppLanguage.TR) "🏆 ZAFER SENİN!" else "🏆 VICTORY!") else (if (lang == AppLanguage.TR) "MAÇ BİTTİ" else "GAME OVER"),
                    color = if (isWin) GoldenTrophy else Color.White,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (lang == AppLanguage.TR) "5 tur tamamlandı!" else "5 rounds completed!",
                        color = Color(0xFFB0BEC5),
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "$score ${if (lang == AppLanguage.TR) "Puan" else "Pts"}",
                        color = GoldenTrophy,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Black
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "$opponentName: $oppScore ${if (lang == AppLanguage.TR) "Puan" else "Pts"}",
                        color = DuelRed,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (isWin) (if (lang == AppLanguage.TR) "+80 RP Kazandın!" else "+80 RP Won!") else "-25 RP",
                        color = if (isWin) FootballPitchGreen else DuelRed,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.startMode1Game(
                            isDuel = isDuel,
                            isPassAndPlay = isPassAndPlay,
                            opponentName = opponentName
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = FootballPitchGreen)
                ) {
                    Text(
                        text = Strings.get("play_again", lang),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = onNavigateBack,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF263238))
                ) {
                    Text(
                        text = Strings.get("back_to_menu", lang),
                        color = Color.White
                    )
                }
            }
        )
    }
}
