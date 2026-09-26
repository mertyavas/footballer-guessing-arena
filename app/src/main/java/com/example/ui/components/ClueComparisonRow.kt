package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AttributeClue
import com.example.data.model.GuessAttempt
import com.example.ui.theme.DuelRed
import com.example.ui.theme.FootballPitchGreen
import com.example.ui.theme.GoldenTrophy

@Composable
fun ClueComparisonRow(
    attempt: GuessAttempt,
    modifier: Modifier = Modifier
) {
    val borderColor = if (attempt.isPlayerOne) FootballPitchGreen else DuelRed

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xDD0D223F)),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, borderColor),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            // Header: Player Name + Who Guessed It
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(borderColor)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = attempt.player.name,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(borderColor.copy(alpha = 0.2f))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = attempt.playerNameTitle,
                        color = borderColor,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 6 Comparison Badges in a Row (ÜLKE, LİG, TAKIM, POZ, YAŞ, NO.)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ClueBadge(clue = attempt.countryClue)
                ClueBadge(clue = attempt.leagueClue)
                ClueBadge(clue = attempt.teamClue)
                ClueBadge(clue = attempt.positionClue)
                ClueBadge(clue = attempt.ageClue)
                ClueBadge(clue = attempt.numberClue)
            }
        }
    }
}

@Composable
fun ClueBadge(
    clue: AttributeClue,
    modifier: Modifier = Modifier
) {
    val bg = if (clue.isMatch) Color(0xFF00C853) else Color(0xFFD50000)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(horizontal = 2.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(bg)
                .border(1.5.dp, Color.White.copy(alpha = 0.7f), CircleShape)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(2.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = clue.value,
                        color = Color.White,
                        fontSize = if (clue.value.length > 7) 8.sp else 10.sp,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                    if (clue.directionHint != null) {
                        Text(
                            text = clue.directionHint,
                            color = GoldenTrophy,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }
                if (clue.subValue != null) {
                    Text(
                        text = clue.subValue,
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 7.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = clue.label,
            color = Color(0xFFB0BEC5),
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
