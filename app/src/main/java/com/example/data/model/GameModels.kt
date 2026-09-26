package com.example.data.model

import androidx.compose.ui.graphics.Color
import com.example.ui.theme.TierAmator
import com.example.ui.theme.TierChampions
import com.example.ui.theme.TierLig1
import com.example.ui.theme.TierLig2
import com.example.ui.theme.TierLig3
import com.example.ui.theme.TierSuperLig

data class FootballPlayer(
    val id: String,
    val name: String,
    val country: String,
    val countryFlag: String,
    val league: String,
    val team: String,
    val position: String,
    val age: Int,
    val jerseyNumber: Int,
    val clubsPlayedFor: List<String>,
    val nationalities: List<String> = listOf(country)
)

data class AttributeClue(
    val label: String,
    val value: String,
    val subValue: String? = null,
    val isMatch: Boolean,
    val directionHint: String? = null // e.g. "↑" or "↓"
)

data class GuessAttempt(
    val player: FootballPlayer,
    val isPlayerOne: Boolean, // Green (Player 1) or Red (Player 2 / Bot)
    val playerNameTitle: String,
    val countryClue: AttributeClue,
    val leagueClue: AttributeClue,
    val teamClue: AttributeClue,
    val positionClue: AttributeClue,
    val ageClue: AttributeClue,
    val numberClue: AttributeClue,
    val isFullMatch: Boolean
)

enum class LeagueTier(
    val displayName: String,
    val minRp: Int,
    val maxRp: Int,
    val badgeIcon: String,
    val color: Color
) {
    AMATOR("Amatör Küme", 0, 499, "🛡️", TierAmator),
    TFF_3_LIG("TFF 3. Lig", 500, 999, "🥉", TierLig3),
    TFF_2_LIG("TFF 2. Lig", 1000, 1999, "🥈", TierLig2),
    TRENDYOL_1_LIG("Trendyol 1. Lig", 2000, 3499, "🥇", TierLig1),
    SUPER_LIG("Trendyol Süper Lig", 3500, 5499, "🏆", TierSuperLig),
    CHAMPIONS_LEAGUE("Şampiyonlar Ligi Elit", 5500, 99999, "⭐", TierChampions);

    companion object {
        fun fromRp(rp: Int): LeagueTier {
            return entries.findLast { rp >= it.minRp } ?: AMATOR
        }
    }
}

enum class XoxPlayer(val colorHex: Long, val displayName: String) {
    GREEN(0xFF00E676, "Yeşil Takım"),
    RED(0xFFFF1744, "Kırmızı Takım")
}

data class XoxCell(
    val row: Int,
    val col: Int,
    val claimedBy: XoxPlayer? = null,
    val playerName: String = "",
    val playerClub: String = ""
)

data class XoxBoardConfig(
    val id: String = "",
    val rowClubs: List<String>,
    val colClubs: List<String>,
    val title: String
)

data class MatchResult(
    val isWin: Boolean,
    val isDraw: Boolean = false,
    val userScore: Int,
    val opponentScore: Int,
    val rpChange: Int,
    val message: String
)

data class AchievementBadge(
    val id: String,
    val title: String,
    val description: String,
    val iconEmoji: String,
    val target: Int,
    val current: Int,
    val isUnlocked: Boolean,
    val rpReward: Int
)

data class LeaderboardEntry(
    val rank: Int,
    val username: String,
    val avatarId: Int,
    val favoriteClub: String,
    val rankPoints: Int,
    val leagueTier: LeagueTier,
    val winRate: String,
    val isCurrentUser: Boolean = false
)

data class FriendItem(
    val id: String,
    val name: String,
    val avatarId: Int,
    val isOnline: Boolean = true,
    val status: String = "Çevrimiçi", // "Çevrimiçi", "Çevrimdışı", "Oyunda"
    val rankPoints: Int = 1200,
    val favoriteClub: String = "Galatasaray",
    val friendshipStatus: String = "ACCEPTED" // "PENDING_INCOMING", "PENDING_OUTGOING", "ACCEPTED"
)

sealed class Screen {
    data object Auth : Screen()
    data object Home : Screen()
    data class Mode1Guess(
        val isDuel: Boolean = false,
        val isPassAndPlay: Boolean = false,
        val opponentName: String = "Rakip Oyuncu"
    ) : Screen()
    data class Mode2Xox(val isPassAndPlay: Boolean = false, val opponentName: String = "Rakip Bot") : Screen()
    data object DuelLobby : Screen()
    data object Leaderboard : Screen()
    data object League : Screen()
    data object Profile : Screen()
}

sealed class AuthResult {
    data class Success(val message: String) : AuthResult()
    data class Error(val message: String) : AuthResult()
}

sealed class FriendRequestResult {
    data class Success(val message: String) : FriendRequestResult()
    data class Error(val message: String) : FriendRequestResult()
}
