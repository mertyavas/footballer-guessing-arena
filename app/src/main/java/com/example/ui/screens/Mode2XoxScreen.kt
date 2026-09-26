package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.data.model.XoxPlayer
import com.example.ui.theme.DuelRed
import com.example.ui.theme.FootballPitchGreen
import com.example.ui.theme.GoldenTrophy
import com.example.util.AppLanguage
import com.example.util.Strings
import com.example.viewmodel.GameViewModel

@Composable
fun Mode2XoxScreen(
    viewModel: GameViewModel,
    isPassAndPlay: Boolean = false,
    opponentName: String = "Rakip Bot",
    onNavigateBack: () -> Unit
) {
    BackHandler {
        onNavigateBack()
    }

    LaunchedEffect(Unit) {
        viewModel.startMode2Game(
            isPassAndPlay = isPassAndPlay,
            opponentName = opponentName
        )
    }

    val lang by viewModel.appLanguage.collectAsStateWithLifecycle()
    val boardConfig by viewModel.xoxBoardConfig.collectAsStateWithLifecycle()
    val cells by viewModel.xoxCells.collectAsStateWithLifecycle()
    val currentTurn by viewModel.xoxCurrentTurn.collectAsStateWithLifecycle()
    val winner by viewModel.xoxWinner.collectAsStateWithLifecycle()
    val isDraw by viewModel.xoxIsDraw.collectAsStateWithLifecycle()
    val winningLine by viewModel.xoxWinningLine.collectAsStateWithLifecycle()
    val selectedCellIndex by viewModel.xoxSelectedCellIndex.collectAsStateWithLifecycle()
    val searchQuery by viewModel.xoxSearchQuery.collectAsStateWithLifecycle()
    val suggestions by viewModel.xoxSuggestions.collectAsStateWithLifecycle()
    val notificationMessage by viewModel.xoxNotificationMessage.collectAsStateWithLifecycle()

    val turnColor by animateColorAsState(
        targetValue = if (currentTurn == XoxPlayer.GREEN) FootballPitchGreen else DuelRed,
        label = "turnColor"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Mode 2: Dark Chalk Slate Tactical Pitch Background
        Image(
            painter = painterResource(id = R.drawable.img_mode_dark_bg),
            contentDescription = "Siyah Taktik Arka Plan",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Dark tint
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xCC121417))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // TOP BAR
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.testTag("mode2_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Geri",
                        tint = Color.White
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "FUTBOL XOX (3x3)",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = if (isPassAndPlay) "2 Kişilik Kapışma (Yan Yana)" else "VS $opponentName",
                        color = GoldenTrophy,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                IconButton(
                    onClick = {
                        viewModel.startMode2Game(
                            isPassAndPlay = isPassAndPlay,
                            opponentName = opponentName
                        )
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Yeniden Başlat",
                        tint = Color.White
                    )
                }
            }

            // DYNAMIC BOARD HEADER & RANDOMIZE BUTTON
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tahta: ${boardConfig.title}",
                    color = GoldenTrophy,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )

                Button(
                    onClick = { viewModel.randomizeXoxBoard() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF263238)),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Casino,
                        contentDescription = null,
                        tint = FootballPitchGreen,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Farklı Takımlar",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // TURN STATUS BAR
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E2229)),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, turnColor),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Green Team (Player 1)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(14.dp)
                                .clip(CircleShape)
                                .background(FootballPitchGreen)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isPassAndPlay) "1. Oyuncu (Yeşil)" else "Sen (Yeşil)",
                            fontSize = 12.sp,
                            fontWeight = if (currentTurn == XoxPlayer.GREEN) FontWeight.Black else FontWeight.Normal,
                            color = if (currentTurn == XoxPlayer.GREEN) FootballPitchGreen else Color(0xFF90A4AE)
                        )
                    }

                    // Active turn badge
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(turnColor.copy(alpha = 0.2f))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = if (winner != null) "OYUN BİTTİ"
                            else if (isDraw) "BERABERE"
                            else if (currentTurn == XoxPlayer.GREEN) "Sıra: Yeşil Takımda"
                            else "Sıra: Kırmızı Takımda",
                            color = turnColor,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Red Team (Player 2)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (isPassAndPlay) "2. Oyuncu (Kırmızı)" else "$opponentName (Kırmızı)",
                            fontSize = 12.sp,
                            fontWeight = if (currentTurn == XoxPlayer.RED) FontWeight.Black else FontWeight.Normal,
                            color = if (currentTurn == XoxPlayer.RED) DuelRed else Color(0xFF90A4AE)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .size(14.dp)
                                .clip(CircleShape)
                                .background(DuelRed)
                        )
                    }
                }
            }

            // NOTIFICATION BANNER (e.g. "Yanlış tahmin! Sıra rakibe geçti")
            AnimatedVisibility(
                visible = notificationMessage != null,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                notificationMessage?.let { msg ->
                    Text(
                        text = msg,
                        color = turnColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // 4x4 XOX GRID (Matching Image 2 exactly!)
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0F1216)),
                border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFF37474F)),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .testTag("xox_grid_board")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(6.dp)
                ) {
                    for (row in 0..3) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            for (col in 0..3) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxSize()
                                        .padding(2.dp)
                                ) {
                                    if (row == 0 && col == 0) {
                                        // Header Corner Logo (like yellow circle logo in Image 2)
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(GoldenTrophy)
                                        ) {
                                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                Icon(
                                                    imageVector = Icons.Default.SportsSoccer,
                                                    contentDescription = null,
                                                    tint = Color.Black,
                                                    modifier = Modifier.size(24.dp)
                                                )
                                                Text(
                                                    text = "XOX",
                                                    color = Color.Black,
                                                    fontSize = 8.sp,
                                                    fontWeight = FontWeight.Black
                                                )
                                            }
                                        }
                                    } else if (row == 0) {
                                        // Top Column Header (Clubs 0, 1, 2)
                                        val colClub = boardConfig.colClubs.getOrElse(col - 1) { "" }
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(Color(0xFF1B232E))
                                                .border(1.dp, Color(0xFF2E3E50), RoundedCornerShape(8.dp))
                                                .padding(3.dp)
                                        ) {
                                            Text(
                                                text = colClub,
                                                color = Color.White,
                                                fontSize = if (colClub.length > 12) 9.sp else 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                textAlign = TextAlign.Center,
                                                lineHeight = 12.sp
                                            )
                                        }
                                    } else if (col == 0) {
                                        // Left Row Header (Clubs 0, 1, 2)
                                        val rowClub = boardConfig.rowClubs.getOrElse(row - 1) { "" }
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(Color(0xFF1B232E))
                                                .border(1.dp, Color(0xFF2E3E50), RoundedCornerShape(8.dp))
                                                .padding(3.dp)
                                        ) {
                                            Text(
                                                text = rowClub,
                                                color = Color.White,
                                                fontSize = if (rowClub.length > 12) 9.sp else 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                textAlign = TextAlign.Center,
                                                lineHeight = 12.sp
                                            )
                                        }
                                    } else {
                                        // PLAYABLE INTERSECTION CELL
                                        val innerRow = row - 1
                                        val innerCol = col - 1
                                        val cellIndex = innerRow * 3 + innerCol
                                        val cell = cells.getOrElse(cellIndex) { null }
                                        val isWinningCell = winningLine?.contains(cellIndex) == true

                                        val cellBg = when (cell?.claimedBy) {
                                            XoxPlayer.GREEN -> Color(0xE600C853)
                                            XoxPlayer.RED -> Color(0xE6D50000)
                                            null -> Color(0xFF161B22)
                                        }

                                        val cellBorder = when {
                                            isWinningCell -> GoldenTrophy
                                            cell?.claimedBy != null -> Color.White.copy(alpha = 0.6f)
                                            else -> Color(0xFF30363D)
                                        }

                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(cellBg)
                                                .border(
                                                    if (isWinningCell) 3.dp else 1.dp,
                                                    cellBorder,
                                                    RoundedCornerShape(8.dp)
                                                )
                                                .clickable(enabled = cell?.claimedBy == null && winner == null && !isDraw) {
                                                    viewModel.selectXoxCell(cellIndex)
                                                }
                                                .padding(2.dp)
                                        ) {
                                            if (cell?.claimedBy != null) {
                                                Column(
                                                    horizontalAlignment = Alignment.CenterHorizontally,
                                                    verticalArrangement = Arrangement.Center,
                                                    modifier = Modifier.padding(2.dp)
                                                ) {
                                                    Text(
                                                        text = "⚽",
                                                        fontSize = 14.sp
                                                    )
                                                    Text(
                                                        text = cell.playerName,
                                                        color = Color.White,
                                                        fontSize = if (cell.playerName.length > 14) 8.sp else 9.sp,
                                                        fontWeight = FontWeight.Black,
                                                        textAlign = TextAlign.Center,
                                                        lineHeight = 10.sp,
                                                        maxLines = 2
                                                    )
                                                }
                                            } else {
                                                Text(
                                                    text = "?",
                                                    color = Color(0xFF4A5568),
                                                    fontSize = 18.sp,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // GUESS MODAL DIALOG WHEN USER TAPS A CELL
    selectedCellIndex?.let { index ->
        val rowIdx = index / 3
        val colIdx = index % 3
        val rowClub = boardConfig.rowClubs[rowIdx]
        val colClub = boardConfig.colClubs[colIdx]

        Dialog(onDismissRequest = { viewModel.closeXoxDialog() }) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF101726)),
                border = androidx.compose.foundation.BorderStroke(2.dp, turnColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (currentTurn == XoxPlayer.GREEN) "🟢 Yeşil Takım Hamlesi" else "🔴 Kırmızı Takım Hamlesi",
                            color = turnColor,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Black
                        )
                        IconButton(onClick = { viewModel.closeXoxDialog() }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Kapat",
                                tint = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Kesişim Kulüpleri:",
                        color = Color(0xFF90A4AE),
                        fontSize = 12.sp
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF1E293B))
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = rowClub,
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = "  ✕  ",
                            color = GoldenTrophy,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF1E293B))
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = colClub,
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Text(
                        text = "Her iki kulüpte de oynamış bir futbolcu seçin.\n(Yanlış seçimde sıra diğer takıma geçer!)",
                        color = Color(0xFFCFD8DC),
                        fontSize = 11.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { viewModel.onXoxQueryChanged(it) },
                        placeholder = { Text("Oyuncu ara...") },
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
                            focusedContainerColor = Color(0xFF1E293B),
                            unfocusedContainerColor = Color(0xFF1E293B),
                            focusedBorderColor = turnColor,
                            unfocusedBorderColor = Color(0xFF334155),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("xox_player_input")
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(suggestions) { p ->
                            Card(
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { viewModel.submitXoxPlayer(p) }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = p.countryFlag, fontSize = 16.sp)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = p.name,
                                            color = Color.White,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "Oynadığı: ${p.clubsPlayedFor.joinToString(", ")}",
                                            color = Color(0xFF94A3B8),
                                            fontSize = 10.sp,
                                            maxLines = 1
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
        }
    }

    // GAME OVER / VICTORY ALERT
    if (winner != null || isDraw) {
        AlertDialog(
            onDismissRequest = {},
            containerColor = Color(0xFF121417),
            title = {
                Text(
                    text = when (winner) {
                        XoxPlayer.GREEN -> if (lang == AppLanguage.TR) "🏆 YEŞİL TAKIM KAZANDI!" else "🏆 GREEN TEAM WON!"
                        XoxPlayer.RED -> if (lang == AppLanguage.TR) "🏆 KIRMIZI TAKIM KAZANDI!" else "🏆 RED TEAM WON!"
                        null -> if (lang == AppLanguage.TR) "🤝 MAÇ BERABERE BİTTİ!" else "🤝 MATCH DRAWN!"
                    },
                    color = when (winner) {
                        XoxPlayer.GREEN -> FootballPitchGreen
                        XoxPlayer.RED -> DuelRed
                        null -> GoldenTrophy
                    },
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
                        text = if (winner != null)
                            (if (lang == AppLanguage.TR) "3'lü dizilim başarıyla tamamlandı! XOX şampiyonu belli oldu." else "3-in-a-row completed! We have an XOX winner.")
                        else
                            (if (lang == AppLanguage.TR) "Tüm kareler doldu fakat kimse 3'lü dizilim yapamadı." else "Board is full with no 3-in-a-row line."),
                        color = Color(0xFFCFD8DC),
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = if (winner == XoxPlayer.GREEN) (if (lang == AppLanguage.TR) "+100 RP Kazandın!" else "+100 RP Won!")
                        else if (isDraw) (if (lang == AppLanguage.TR) "+20 RP (Beraberlik)" else "+20 RP (Draw)")
                        else (if (lang == AppLanguage.TR) "-30 RP (Yenilgi)" else "-30 RP (Defeat)"),
                        color = if (winner == XoxPlayer.GREEN) FootballPitchGreen else GoldenTrophy,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.startMode2Game(
                            isPassAndPlay = isPassAndPlay,
                            opponentName = opponentName
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = FootballPitchGreen)
                ) {
                    Text(
                        text = if (lang == AppLanguage.TR) "Yeni Maç (Yeni Tahta)" else "Play Again (New Board)",
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
