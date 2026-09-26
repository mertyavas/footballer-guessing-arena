package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: Int = 1,
    val username: String = "Misafir_10",
    val email: String = "",
    val isGuest: Boolean = true,
    val isLoggedIn: Boolean = false,
    val friendCode: String = "FC-7842",
    val avatarId: Int = 1,
    val favoriteClub: String = "Real Madrid",
    val jerseyNumber: Int = 10,
    val title: String = "Genç Yetenek",
    val rankPoints: Int = 1250,
    val totalWins: Int = 12,
    val totalLosses: Int = 4,
    val totalDraws: Int = 2,
    val highestStreak: Int = 5,
    val currentStreak: Int = 2,
    val coins: Int = 850
)

@Entity(tableName = "match_history")
data class MatchHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val gameMode: String,
    val opponentName: String,
    val userScore: Int,
    val opponentScore: Int,
    val result: String, // WIN, LOSS, DRAW
    val rpChange: Int,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "achievements")
data class AchievementEntity(
    @PrimaryKey val id: String,
    val currentProgress: Int,
    val isUnlocked: Boolean
)

@Entity(tableName = "friends")
data class FriendEntity(
    @PrimaryKey val id: String,
    val name: String,
    val avatarId: Int = 1,
    val isOnline: Boolean = true,
    val status: String = "Çevrimiçi",
    val rankPoints: Int = 1200,
    val favoriteClub: String = "Galatasaray",
    val friendshipStatus: String = "ACCEPTED", // PENDING_INCOMING, PENDING_OUTGOING, ACCEPTED
    val addedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "user_accounts")
data class UserAccountEntity(
    @PrimaryKey val username: String, // standardized with leading "@", e.g. "@mert94"
    val email: String = "",
    val password: String, // stored password
    val avatarId: Int = 1,
    val favoriteClub: String = "Galatasaray",
    val jerseyNumber: Int = 10,
    val rankPoints: Int = 1250,
    val title: String = "Kayıtlı Oyuncu",
    val isOnline: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)
