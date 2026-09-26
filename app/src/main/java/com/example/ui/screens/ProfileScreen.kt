package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.LeagueTier
import com.example.ui.components.AvatarHelper
import com.example.ui.components.PlayerAvatarView
import com.example.ui.theme.CardBorder
import com.example.ui.theme.DeepBlueBg
import com.example.ui.theme.DuelRed
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.FootballPitchGreen
import com.example.ui.theme.GoldenTrophy
import com.example.ui.theme.SurfaceNavy
import com.example.ui.theme.SurfaceNavyVariant
import com.example.ui.theme.TextMuted
import com.example.util.AppLanguage
import com.example.util.Strings
import com.example.viewmodel.GameViewModel

@Composable
fun ProfileScreen(
    viewModel: GameViewModel,
    onNavigateBack: () -> Unit
) {
    BackHandler {
        onNavigateBack()
    }

    val lang by viewModel.appLanguage.collectAsStateWithLifecycle()
    val profile by viewModel.profile.collectAsStateWithLifecycle()
    val matchHistory by viewModel.matchHistory.collectAsStateWithLifecycle()
    val tier = LeagueTier.fromRp(profile.rankPoints)

    var showEditDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepBlueBg)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.testTag("profile_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Geri",
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (lang == AppLanguage.TR) "PROFİL VE BAŞARILAR" else "PROFILE & ACHIEVEMENTS",
                        color = Color.White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            // Profile Card with Edit Button
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceNavyVariant),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, tier.color),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        PlayerAvatarView(
                            avatarId = profile.avatarId,
                            size = 80.dp,
                            borderColor = tier.color
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = profile.username,
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Black
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "#${profile.jerseyNumber}",
                                color = GoldenTrophy,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = "Unvan: \"${profile.title}\"",
                            color = ElectricBlue,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = tier.badgeIcon, fontSize = 14.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${tier.displayName} • ${profile.favoriteClub}",
                                color = Color(0xFFCFD8DC),
                                fontSize = 13.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(
                                onClick = { showEditDialog = true },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E3A60)),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Özelleştir", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                            Button(
                                onClick = { viewModel.navigateTo(com.example.data.model.Screen.Auth) },
                                colors = ButtonDefaults.buttonColors(containerColor = if (profile.isGuest) GoldenTrophy else Color(0xFF0D253F)),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = if (profile.isGuest) (if (lang == AppLanguage.TR) "Giriş / Kayıt" else "Log In / Register") else (if (lang == AppLanguage.TR) "Hesap Değiştir" else "Switch Account"),
                                    color = if (profile.isGuest) Color.Black else Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }

            // Language Selection Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceNavy),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("🌐", fontSize = 22.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = Strings.get("language_select", lang),
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = if (lang == AppLanguage.TR) "Uygulama dili: Türkçe" else "Current language: English",
                                    color = TextMuted,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            // Türkçe Button
                            Button(
                                onClick = { viewModel.setLanguage(AppLanguage.TR) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (lang == AppLanguage.TR) FootballPitchGreen else Color(0xFF1E293B)
                                ),
                                shape = RoundedCornerShape(10.dp),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                modifier = Modifier.height(36.dp)
                            ) {
                                Text(
                                    text = "🇹🇷 TR",
                                    color = if (lang == AppLanguage.TR) Color.Black else Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }

                            // English Button
                            Button(
                                onClick = { viewModel.setLanguage(AppLanguage.EN) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (lang == AppLanguage.EN) FootballPitchGreen else Color(0xFF1E293B)
                                ),
                                shape = RoundedCornerShape(10.dp),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                modifier = Modifier.height(36.dp)
                            ) {
                                Text(
                                    text = "🇬🇧 EN",
                                    color = if (lang == AppLanguage.EN) Color.Black else Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }

            // Career Stats Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceNavy),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = if (lang == AppLanguage.TR) "KARİYER İSTATİSTİKLERİ" else "CAREER STATS",
                            color = TextMuted,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            StatItem(Strings.get("wins", lang), "${profile.totalWins}", FootballPitchGreen)
                            StatItem(Strings.get("draws", lang), "${profile.totalDraws}", GoldenTrophy)
                            StatItem(Strings.get("losses", lang), "${profile.totalLosses}", DuelRed)
                            val total = profile.totalWins + profile.totalLosses
                            val rate = if (total > 0) "%${(profile.totalWins * 100) / total}" else "%0"
                            StatItem(Strings.get("win_rate", lang), rate, ElectricBlue)
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            StatItem(if (lang == AppLanguage.TR) "Mevcut Seri" else "Current Streak", "🔥 ${profile.currentStreak}", GoldenTrophy)
                            StatItem(if (lang == AppLanguage.TR) "En Yüksek Seri" else "Highest Streak", "⚡ ${profile.highestStreak}", ElectricBlue)
                            StatItem(if (lang == AppLanguage.TR) "Futbol Puanı" else "Rank Points", "${profile.rankPoints} RP", tier.color)
                        }
                    }
                }
            }

            // ACHIEVEMENTS SECTION (Başarı Rozetleri)
            item {
                Text(
                    text = Strings.get("achievements", lang),
                    color = TextMuted,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )
            }

            items(viewModel.achievements) { badge ->
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (badge.isUnlocked) Color(0xFF0F2C4C) else SurfaceNavy
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (badge.isUnlocked) GoldenTrophy else CardBorder
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
                            text = badge.iconEmoji,
                            fontSize = 28.sp
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = badge.title,
                                    color = if (badge.isUnlocked) GoldenTrophy else Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                if (badge.isUnlocked) {
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "KAZANILDI",
                                        color = FootballPitchGreen,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                }
                            }
                            Text(
                                text = badge.description,
                                color = Color(0xFF90A4AE),
                                fontSize = 11.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            val progress = (badge.current.toFloat() / badge.target.toFloat()).coerceIn(0f, 1f)
                            LinearProgressIndicator(
                                progress = { progress },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp)),
                                color = if (badge.isUnlocked) GoldenTrophy else ElectricBlue,
                                trackColor = Color(0xFF1E293B)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "+${badge.rpReward} RP",
                                color = GoldenTrophy,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black
                            )
                            Icon(
                                imageVector = if (badge.isUnlocked) Icons.Default.CheckCircle else Icons.Default.Lock,
                                contentDescription = null,
                                tint = if (badge.isUnlocked) FootballPitchGreen else Color(0xFF546E7A),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }

            // Recent Matches History
            if (matchHistory.isNotEmpty()) {
                item {
                    Text(
                        text = "SON MAÇLAR",
                        color = TextMuted,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                }

                items(matchHistory.take(5)) { match ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceNavy),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = match.gameMode,
                                    color = Color.White,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Rakip: ${match.opponentName}",
                                    color = TextMuted,
                                    fontSize = 11.sp
                                )
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "${match.userScore} - ${match.opponentScore}",
                                    color = Color.White,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Black
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(
                                            when (match.result) {
                                                "WIN" -> FootballPitchGreen
                                                "DRAW" -> GoldenTrophy
                                                else -> DuelRed
                                            }
                                        )
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = when (match.result) {
                                            "WIN" -> "GALİBİYET"
                                            "DRAW" -> "BERABERE"
                                            else -> "MAĞLUBİYET"
                                        },
                                        color = Color.Black,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }
        }
    }

    // PROFILE EDIT DIALOG
    if (showEditDialog) {
        var editName by remember { mutableStateOf(profile.username) }
        var editAvatar by remember { mutableIntStateOf(profile.avatarId) }
        var editClub by remember { mutableStateOf(profile.favoriteClub) }
        var editJersey by remember { mutableIntStateOf(profile.jerseyNumber) }
        var editTitle by remember { mutableStateOf(profile.title) }

        val titles = listOf(
            "Genç Yetenek",
            "XOX Dehası",
            "Futbol Profesörü",
            "Penaltı Canavarı",
            "Gol Makinesi",
            "Süper Lig Efsanesi"
        )

        Dialog(onDismissRequest = { showEditDialog = false }) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF101726)),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, ElectricBlue),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        Text(
                            text = "PROFİLİ DÜZENLE",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black
                        )
                    }

                    // Avatar Selector
                    item {
                        Text("Avatar Seç:", color = TextMuted, fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            itemsIndexed(AvatarHelper.avatarEmojis.drop(1)) { idx, emoji ->
                                val realId = idx + 1
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .size(46.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (editAvatar == realId) FootballPitchGreen.copy(alpha = 0.3f)
                                            else Color(0xFF1E293B)
                                        )
                                        .border(
                                            2.dp,
                                            if (editAvatar == realId) FootballPitchGreen else Color.Transparent,
                                            CircleShape
                                        )
                                        .clickable { editAvatar = realId }
                                ) {
                                    Text(emoji, fontSize = 22.sp)
                                }
                            }
                        }
                    }

                    // Nickname Input
                    item {
                        OutlinedTextField(
                            value = editName,
                            onValueChange = { editName = it },
                            label = { Text("Oyuncu İsmi") },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = FootballPitchGreen,
                                unfocusedBorderColor = Color(0xFF334155)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // Favorite Club Selector
                    item {
                        Text("Favori Kulüp:", color = TextMuted, fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(AvatarHelper.popularClubs) { club ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(
                                            if (editClub == club) ElectricBlue else Color(0xFF1E293B)
                                        )
                                        .clickable { editClub = club }
                                        .padding(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = club,
                                        color = Color.White,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }

                    // Title Selector
                    item {
                        Text("Unvan:", color = TextMuted, fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(titles) { t ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(
                                            if (editTitle == t) GoldenTrophy else Color(0xFF1E293B)
                                        )
                                        .clickable { editTitle = t }
                                        .padding(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = t,
                                        color = if (editTitle == t) Color.Black else Color.White,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }

                    // Action buttons
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(
                                onClick = { showEditDialog = false },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF263238))
                            ) {
                                Text("İptal")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = {
                                    viewModel.updateProfile(
                                        username = editName,
                                        avatarId = editAvatar,
                                        favoriteClub = editClub,
                                        jerseyNumber = editJersey,
                                        title = editTitle
                                    )
                                    showEditDialog = false
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = FootballPitchGreen)
                            ) {
                                Text("Kaydet", color = Color.Black, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatItem(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Black, color = color)
        Text(text = label, fontSize = 11.sp, color = TextMuted)
    }
}
