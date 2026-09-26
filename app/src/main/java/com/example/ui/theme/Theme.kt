package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = FootballPitchGreen,
    onPrimary = Color.Black,
    primaryContainer = FootballPitchGreenDark,
    onPrimaryContainer = Color.White,
    secondary = ElectricBlue,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF1565C0),
    onSecondaryContainer = Color.White,
    tertiary = GoldenTrophy,
    onTertiary = Color.Black,
    error = DuelRed,
    onError = Color.White,
    background = DeepBlueBg,
    onBackground = OnSurfaceWhite,
    surface = SurfaceNavy,
    onSurface = OnSurfaceWhite,
    surfaceVariant = SurfaceNavyVariant,
    onSurfaceVariant = TextMuted,
    outline = CardBorder
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
