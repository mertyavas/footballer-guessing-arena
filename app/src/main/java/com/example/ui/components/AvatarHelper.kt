package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.FootballPitchGreen
import com.example.ui.theme.GoldenTrophy

object AvatarHelper {
    val avatarEmojis = listOf(
        "⚡", // 0: Default
        "🦁", // 1: Aslan Golcü
        "🦅", // 2: Kartal Forvet
        "🦊", // 3: Kurnaz 10 Numara
        "🧤", // 4: Panter Kaleci
        "👑", // 5: Kral Maestro
        "⚡", // 6: Yıldız Kanat
        "🛡️", // 7: Çelik Defans
        "🔥"  // 8: Ateşli Forvet
    )

    val avatarTitles = listOf(
        "Oyuncu",
        "Aslan Golcü",
        "Kartal Forvet",
        "Kurnaz Maestro",
        "Panter Kaleci",
        "Kral 10 Numara",
        "Rüzgarın Oğlu",
        "Çelik Stoper",
        "Ateşli Yıldız"
    )

    val popularClubs = listOf(
        "Real Madrid",
        "Galatasaray",
        "Fenerbahçe",
        "Beşiktaş",
        "Barcelona",
        "Manchester City",
        "Arsenal",
        "Liverpool",
        "Bayern Münih",
        "Inter",
        "Trabzonspor"
    )

    fun getAvatarEmoji(id: Int): String {
        return avatarEmojis.getOrElse(id) { "⚽" }
    }
}

@Composable
fun PlayerAvatarView(
    avatarId: Int,
    size: Dp = 48.dp,
    borderColor: Color = FootballPitchGreen,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFF1E293B), Color(0xFF0F172A))
                )
            )
            .border(2.dp, borderColor, CircleShape)
    ) {
        Text(
            text = AvatarHelper.getAvatarEmoji(avatarId),
            fontSize = (size.value * 0.48).sp
        )
    }
}
