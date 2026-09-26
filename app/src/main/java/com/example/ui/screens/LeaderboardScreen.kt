package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.PlayerAvatarView
import com.example.ui.theme.CardBorder
import com.example.ui.theme.DeepBlueBg
import com.example.ui.theme.FootballPitchGreen
import com.example.ui.theme.GoldenTrophy
import com.example.ui.theme.SurfaceNavy
import com.example.ui.theme.SurfaceNavyVariant
import com.example.ui.theme.TextMuted
import com.example.viewmodel.GameViewModel

@Composable
fun LeaderboardScreen(
    viewModel: GameViewModel,
    onNavigateBack: () -> Unit
) {
    BackHandler {
        onNavigateBack()
    }

    val leaderboard = viewModel.leaderboard

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepBlueBg)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.testTag("leaderboard_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Geri",
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "LİDERLER SIRALAMASI",
                            color = Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            text = "Gerçek zamanlı lig ve RP puan durumu",
                            color = TextMuted,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // Top 3 Podium Card
            if (leaderboard.size >= 3) {
                item {
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceNavyVariant),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            // 2nd Place
                            val second = leaderboard[1]
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("🥈 2.", fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color(0xFFCFD8DC))
                                Spacer(modifier = Modifier.height(6.dp))
                                PlayerAvatarView(avatarId = second.avatarId, size = 52.dp, borderColor = Color(0xFFB0BEC5))
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(second.username, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold, maxLines = 1)
                                Text("${second.rankPoints} RP", color = GoldenTrophy, fontSize = 11.sp, fontWeight = FontWeight.Black)
                            }

                            // 1st Place (Crown)
                            val first = leaderboard[0]
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("👑 1.", fontSize = 16.sp, fontWeight = FontWeight.Black, color = GoldenTrophy)
                                Spacer(modifier = Modifier.height(4.dp))
                                PlayerAvatarView(avatarId = first.avatarId, size = 64.dp, borderColor = GoldenTrophy)
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(first.username, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, maxLines = 1)
                                Text("${first.rankPoints} RP", color = GoldenTrophy, fontSize = 12.sp, fontWeight = FontWeight.Black)
                            }

                            // 3rd Place
                            val third = leaderboard[2]
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("🥉 3.", fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color(0xFFCD7F32))
                                Spacer(modifier = Modifier.height(6.dp))
                                PlayerAvatarView(avatarId = third.avatarId, size = 48.dp, borderColor = Color(0xFFCD7F32))
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(third.username, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold, maxLines = 1)
                                Text("${third.rankPoints} RP", color = GoldenTrophy, fontSize = 11.sp, fontWeight = FontWeight.Black)
                            }
                        }
                    }
                }
            }

            // List of Players
            items(leaderboard) { entry ->
                val isMe = entry.isCurrentUser
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isMe) Color(0xFF0F326A) else SurfaceNavy
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        if (isMe) 2.dp else 1.dp,
                        if (isMe) FootballPitchGreen else CardBorder
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Rank Number
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(
                                    when (entry.rank) {
                                        1 -> GoldenTrophy
                                        2 -> Color(0xFFB0BEC5)
                                        3 -> Color(0xFFCD7F32)
                                        else -> Color(0xFF1E293B)
                                    }
                                )
                        ) {
                            Text(
                                text = "${entry.rank}",
                                color = if (entry.rank <= 3) Color.Black else Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        PlayerAvatarView(
                            avatarId = entry.avatarId,
                            size = 38.dp,
                            borderColor = entry.leagueTier.color
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = entry.username,
                                    color = if (isMe) FootballPitchGreen else Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "${entry.leagueTier.badgeIcon} ${entry.leagueTier.displayName}",
                                    color = entry.leagueTier.color,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = " • ${entry.favoriteClub}",
                                    color = TextMuted,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "${entry.rankPoints} RP",
                                color = GoldenTrophy,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Black
                            )
                            Text(
                                text = "Kazanma: ${entry.winRate}",
                                color = FootballPitchGreen,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }
        }
    }
}
