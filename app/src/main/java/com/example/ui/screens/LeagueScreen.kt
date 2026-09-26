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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.LeagueTier
import com.example.ui.theme.CardBorder
import com.example.ui.theme.DeepBlueBg
import com.example.ui.theme.FootballPitchGreen
import com.example.ui.theme.GoldenTrophy
import com.example.ui.theme.SurfaceNavy
import com.example.ui.theme.SurfaceNavyVariant
import com.example.ui.theme.TextMuted
import com.example.viewmodel.GameViewModel

@Composable
fun LeagueScreen(
    viewModel: GameViewModel,
    onNavigateBack: () -> Unit
) {
    BackHandler {
        onNavigateBack()
    }

    val profile by viewModel.profile.collectAsStateWithLifecycle()
    val currentTier = LeagueTier.fromRp(profile.rankPoints)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepBlueBg)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.testTag("league_back_button")
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
                            text = "LİG SİSTEMİ & KADEMELER",
                            color = Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            text = "Maç kazanarak kademe atla, ödülleri topla!",
                            color = TextMuted,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // Current Tier Banner
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceNavyVariant),
                    border = androidx.compose.foundation.BorderStroke(2.dp, currentTier.color),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = currentTier.badgeIcon,
                            fontSize = 42.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = currentTier.displayName,
                            color = currentTier.color,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${profile.rankPoints} RP",
                            color = GoldenTrophy,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        val nextTier = LeagueTier.entries.getOrNull(currentTier.ordinal + 1)
                        if (nextTier != null) {
                            val span = (nextTier.minRp - currentTier.minRp).toFloat()
                            val cur = (profile.rankPoints - currentTier.minRp).toFloat().coerceAtLeast(0f)
                            val progress = (cur / span).coerceIn(0f, 1f)
                            LinearProgressIndicator(
                                progress = { progress },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                color = nextTier.color,
                                trackColor = Color(0xFF263238)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Sonraki Lig: ${nextTier.displayName} (${nextTier.minRp} RP)",
                                color = Color(0xFFCFD8DC),
                                fontSize = 11.sp
                            )
                        } else {
                            Text(
                                text = "Tebrikler! En yüksek lig kademesindesin!",
                                color = GoldenTrophy,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // All Tiers List
            item {
                Text(
                    text = "TÜM LİG KADEMELERİ",
                    color = TextMuted,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )
            }

            items(LeagueTier.entries) { tier ->
                val isCurrent = tier == currentTier
                val isUnlocked = profile.rankPoints >= tier.minRp

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isCurrent) Color(0xFF0F326A) else SurfaceNavy
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        if (isCurrent) 2.dp else 1.dp,
                        if (isCurrent) FootballPitchGreen else CardBorder
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = tier.badgeIcon,
                            fontSize = 28.sp
                        )
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = tier.displayName,
                                    color = tier.color,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                if (isCurrent) {
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(FootballPitchGreen)
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = "MEVCUT",
                                            color = Color.Black,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Black
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = if (tier.maxRp > 10000) "${tier.minRp}+ RP" else "${tier.minRp} - ${tier.maxRp} RP",
                                color = TextMuted,
                                fontSize = 12.sp
                            )
                        }

                        Icon(
                            imageVector = if (isUnlocked) Icons.Default.Check else Icons.Default.Lock,
                            contentDescription = null,
                            tint = if (isUnlocked) FootballPitchGreen else Color(0xFF546E7A),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }
        }
    }
}
