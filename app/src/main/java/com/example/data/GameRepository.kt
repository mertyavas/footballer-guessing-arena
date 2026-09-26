package com.example.data

import com.example.data.local.AppDatabase
import com.example.data.local.MatchHistoryEntity
import com.example.data.local.UserAccountEntity
import com.example.data.local.UserProfileEntity
import com.example.data.model.AchievementBadge
import com.example.data.model.AuthResult
import com.example.data.model.FriendItem
import com.example.data.model.FriendRequestResult
import com.example.data.model.LeaderboardEntry
import com.example.data.model.LeagueTier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GameRepository(private val database: AppDatabase) {

    val userProfile: Flow<UserProfileEntity> = database.userDao().getUserProfile().map {
        it ?: UserProfileEntity().also { defaultProfile ->
            database.userDao().insertOrUpdateProfile(defaultProfile)
        }
    }

    val matchHistory: Flow<List<MatchHistoryEntity>> = database.matchHistoryDao().getAllMatches()

    suspend fun updateProfile(profile: UserProfileEntity) {
        database.userDao().insertOrUpdateProfile(profile)
    }

    suspend fun recordMatch(
        mode: String,
        opponentName: String,
        userScore: Int,
        opponentScore: Int,
        isWin: Boolean,
        isDraw: Boolean,
        rpChange: Int,
        currentProfile: UserProfileEntity
    ) {
        val resultStr = if (isWin) "WIN" else if (isDraw) "DRAW" else "LOSS"
        val match = MatchHistoryEntity(
            gameMode = mode,
            opponentName = opponentName,
            userScore = userScore,
            opponentScore = opponentScore,
            result = resultStr,
            rpChange = rpChange
        )
        database.matchHistoryDao().insertMatch(match)

        val newRp = (currentProfile.rankPoints + rpChange).coerceAtLeast(0)
        val newWins = if (isWin) currentProfile.totalWins + 1 else currentProfile.totalWins
        val newLosses = if (!isWin && !isDraw) currentProfile.totalLosses + 1 else currentProfile.totalLosses
        val newDraws = if (isDraw) currentProfile.totalDraws + 1 else currentProfile.totalDraws
        val newStreak = if (isWin) currentProfile.currentStreak + 1 else 0
        val highestStreak = maxOf(currentProfile.highestStreak, newStreak)
        val coinsEarned = if (isWin) 50 else if (isDraw) 20 else 10

        val updated = currentProfile.copy(
            rankPoints = newRp,
            totalWins = newWins,
            totalLosses = newLosses,
            totalDraws = newDraws,
            currentStreak = newStreak,
            highestStreak = highestStreak,
            coins = currentProfile.coins + coinsEarned
        )
        database.userDao().insertOrUpdateProfile(updated)
    }

    fun getAchievements(profile: UserProfileEntity): List<AchievementBadge> {
        val totalMatches = profile.totalWins + profile.totalLosses + profile.totalDraws
        return listOf(
            AchievementBadge(
                id = "first_win",
                title = "İlk Zafer",
                description = "Herhangi bir modda ilk galibiyetini al",
                iconEmoji = "🏆",
                target = 1,
                current = profile.totalWins.coerceAtMost(1),
                isUnlocked = profile.totalWins >= 1,
                rpReward = 100
            ),
            AchievementBadge(
                id = "win_streak_3",
                title = "Alev Aldın!",
                description = "Üst üste 3 galibiyet serisi yakala",
                iconEmoji = "🔥",
                target = 3,
                current = profile.highestStreak.coerceAtMost(3),
                isUnlocked = profile.highestStreak >= 3,
                rpReward = 200
            ),
            AchievementBadge(
                id = "xox_master",
                title = "XOX Taktisyeni",
                description = "Futbol XOX modunda 5 zafer elde et",
                iconEmoji = "⚔️",
                target = 5,
                current = profile.totalWins.coerceAtMost(5),
                isUnlocked = profile.totalWins >= 5,
                rpReward = 250
            ),
            AchievementBadge(
                id = "speed_demon",
                title = "15 Saniye Canavarı",
                description = "Oyuncu Tahmin modunda 10 tur tamamla",
                iconEmoji = "⚡",
                target = 10,
                current = totalMatches.coerceAtMost(10),
                isUnlocked = totalMatches >= 10,
                rpReward = 300
            ),
            AchievementBadge(
                id = "super_lig",
                title = "Süper Lig Temsilcisi",
                description = "Süper Lig kademesine yüksel (3500+ RP)",
                iconEmoji = "🌟",
                target = 3500,
                current = profile.rankPoints.coerceAtMost(3500),
                isUnlocked = profile.rankPoints >= 3500,
                rpReward = 500
            ),
            AchievementBadge(
                id = "champions_elite",
                title = "Şampiyonlar Ligi Yıldızı",
                description = "5500 RP toplayarak Şampiyonlar Ligi Elit kademesine çık",
                iconEmoji = "👑",
                target = 5500,
                current = profile.rankPoints.coerceAtMost(5500),
                isUnlocked = profile.rankPoints >= 5500,
                rpReward = 1000
            ),
            AchievementBadge(
                id = "century_club",
                title = "Futbol Ansiklopedisi",
                description = "Toplamda 25 maç tamamla",
                iconEmoji = "📖",
                target = 25,
                current = totalMatches.coerceAtMost(25),
                isUnlocked = totalMatches >= 25,
                rpReward = 400
            )
        )
    }

    val friends: Flow<List<FriendItem>> = database.friendDao().getAcceptedFriends().map { list ->
        list.map {
            FriendItem(
                id = it.id,
                name = it.name,
                avatarId = it.avatarId,
                isOnline = it.isOnline,
                status = it.status,
                rankPoints = it.rankPoints,
                favoriteClub = it.favoriteClub,
                friendshipStatus = it.friendshipStatus
            )
        }
    }

    val pendingRequests: Flow<List<FriendItem>> = database.friendDao().getPendingRequests().map { list ->
        list.map {
            FriendItem(
                id = it.id,
                name = it.name,
                avatarId = it.avatarId,
                isOnline = it.isOnline,
                status = it.status,
                rankPoints = it.rankPoints,
                favoriteClub = it.favoriteClub,
                friendshipStatus = it.friendshipStatus
            )
        }
    }

    val registeredAccounts: Flow<List<UserAccountEntity>> = database.accountDao().getAllAccounts()

    private val defaultSeedAccounts = listOf(
        UserAccountEntity("@EfsaneKral_7", "kral@arena.com", "123456", 3, "Real Madrid", 7, 7890, "Efsane Golcü", true),
        UserAccountEntity("@FutbolProfesoru", "prof@arena.com", "123456", 5, "Manchester City", 10, 7240, "Taktik Ustası", true),
        UserAccountEntity("@Icardi_Wanda99", "icardi@arena.com", "123456", 2, "Galatasaray", 99, 6850, "Aşk Adamı", true),
        UserAccountEntity("@Dzeko_Bosna", "dzeko@arena.com", "123456", 4, "Fenerbahçe", 9, 6120, "Bosna Elması", false),
        UserAccountEntity("@Semih_Kartal", "semih@arena.com", "123456", 1, "Beşiktaş", 9, 5740, "Genç Kartal", true),
        UserAccountEntity("@Bellingham_Jude", "jude@arena.com", "123456", 6, "Real Madrid", 5, 5310, "Hey Jude", false),
        UserAccountEntity("@Arda_Guler_Fan", "arda@arena.com", "123456", 2, "Real Madrid", 15, 4150, "Türk İncisi", true),
        UserAccountEntity("@Hakan_10", "hakan@arena.com", "123456", 4, "Inter", 20, 3920, "Frikik Ustası", true),
        UserAccountEntity("@TaktikDehası", "taktik@arena.com", "123456", 1, "Arsenal", 8, 3640, "Orkestra Şefi", false),
        UserAccountEntity("@BarisAlperBaros", "baris@arena.com", "123456", 3, "Galatasaray", 53, 3410, "Yorulmaz Dinamo", true),
        UserAccountEntity("@Burak_CR7", "burak@arena.com", "123456", 3, "Real Madrid", 7, 2850, "Siuuu Ustası", true),
        UserAccountEntity("@Kartal_Sergen", "sergen@arena.com", "123456", 1, "Beşiktaş", 10, 2410, "Sol Ayak Sihirbazı", false),
        UserAccountEntity("@PremierLigAsigi", "premier@arena.com", "123456", 5, "Liverpool", 11, 2120, "Anfield Ruhlu", true),
        UserAccountEntity("@Kerem99", "kerem@arena.com", "123456", 2, "Galatasaray", 7, 1640, "Sihirbaz", true),
        UserAccountEntity("@Emre_Madrid", "emre@arena.com", "123456", 5, "Fenerbahçe", 10, 1120, "Kadıköy Boğası", false),
        UserAccountEntity("@AmatörPanter", "panter@arena.com", "123456", 4, "Trabzonspor", 1, 890, "Uçan Kaleci", false),
        UserAccountEntity("@SokakFutbolcusu", "sokak@arena.com", "123456", 6, "Barcelona", 10, 620, "Bileklerine Hakim", true),
        UserAccountEntity("@CimbomAslani", "cimbom@arena.com", "123456", 2, "Galatasaray", 10, 410, "Tribün Çocuğu", true)
    )

    suspend fun ensureSeedAccounts() {
        database.accountDao().insertAll(defaultSeedAccounts)
    }

    suspend fun sendFriendRequest(rawInput: String, currentUserUsername: String): FriendRequestResult {
        ensureSeedAccounts()
        val trimmed = rawInput.trim()
        if (trimmed.isBlank()) {
            return FriendRequestResult.Error("Lütfen bir kullanıcı adı girin.")
        }

        val normalizedInput = if (trimmed.startsWith("@")) trimmed else "@$trimmed"
        val currentNormalized = if (currentUserUsername.startsWith("@")) currentUserUsername else "@$currentUserUsername"

        // Cannot add self
        if (normalizedInput.equals(currentNormalized, ignoreCase = true) ||
            trimmed.replace("@", "").equals(currentUserUsername.replace("@", ""), ignoreCase = true)
        ) {
            return FriendRequestResult.Error("Kendinizi arkadaş olarak ekleyemezsiniz!")
        }

        // Validate that user is registered in the game
        val targetAccount = database.accountDao().findByUsername(trimmed)
            ?: return FriendRequestResult.Error("Oyuncu bulunamadı! '$trimmed' adında kayıtlı bir oyuncu oyunda bulunmuyor. Yalnızca kayıtlı kullanıcılar arkadaş eklenebilir.")

        // Check if already friends or in pending request
        val existingFriend = database.friendDao().getFriendByName(targetAccount.username)
        if (existingFriend != null) {
            return FriendRequestResult.Error("${targetAccount.username} zaten arkadaş listenizde veya bekleyen isteğiniz var.")
        }

        // Insert friend request with REAL target user account details!
        val friend = com.example.data.local.FriendEntity(
            id = "fr_${System.currentTimeMillis()}",
            name = targetAccount.username,
            avatarId = targetAccount.avatarId,
            isOnline = targetAccount.isOnline,
            status = if (targetAccount.isOnline) "Çevrimiçi" else "Çevrimdışı",
            rankPoints = targetAccount.rankPoints,
            favoriteClub = targetAccount.favoriteClub,
            friendshipStatus = "PENDING_OUTGOING"
        )
        database.friendDao().insertFriend(friend)

        return FriendRequestResult.Success("${targetAccount.username} adlı kayıtlı oyuncuya arkadaşlık isteği gönderildi!")
    }

    suspend fun acceptFriendRequest(friendId: String) {
        database.friendDao().acceptFriendRequest(friendId)
    }

    suspend fun rejectFriendRequest(friendId: String) {
        database.friendDao().deleteFriend(friendId)
    }

    suspend fun deleteFriend(id: String) {
        database.friendDao().deleteFriend(id)
    }

    suspend fun registerUserWithPassword(
        username: String,
        email: String,
        passwordInput: String,
        favoriteClub: String,
        avatarId: Int,
        jerseyNumber: Int
    ): AuthResult {
        ensureSeedAccounts()
        val cleanName = if (username.trim().startsWith("@")) username.trim() else "@${username.trim()}"
        if (username.trim().length < 3) {
            return AuthResult.Error("Kullanıcı adı en az 3 karakter olmalıdır.")
        }
        if (passwordInput.length < 4) {
            return AuthResult.Error("Şifre en az 4 karakter olmalıdır.")
        }

        // Check if username already exists
        val existing = database.accountDao().findByUsername(cleanName)
        if (existing != null) {
            return AuthResult.Error("Bu kullanıcı adı ($cleanName) zaten kayıtlı! Lütfen farklı bir kullanıcı adı seçin.")
        }

        // Check email if provided
        if (email.isNotBlank()) {
            val existingEmail = database.accountDao().findByEmail(email.trim())
            if (existingEmail != null) {
                return AuthResult.Error("Bu e-posta adresi ile zaten kayıtlı bir hesap var.")
            }
        }

        val newAccount = UserAccountEntity(
            username = cleanName,
            email = email.trim(),
            password = passwordInput,
            avatarId = avatarId,
            favoriteClub = favoriteClub,
            jerseyNumber = jerseyNumber,
            rankPoints = 1250,
            title = "Kayıtlı Yıldız",
            isOnline = true
        )
        database.accountDao().insertAccount(newAccount)

        val code = "FC-${kotlin.random.Random.nextInt(1000, 9999)}"
        val profile = UserProfileEntity(
            id = 1,
            username = cleanName,
            email = email.trim(),
            isGuest = false,
            isLoggedIn = true,
            friendCode = code,
            avatarId = avatarId,
            favoriteClub = favoriteClub,
            jerseyNumber = jerseyNumber,
            title = "Kayıtlı Yıldız",
            rankPoints = 1250,
            coins = 1000
        )
        database.userDao().insertOrUpdateProfile(profile)

        return AuthResult.Success("Tebrikler $cleanName! Hesabınız şifrenizle birlikte başarıyla oluşturuldu.")
    }

    suspend fun loginUserWithPassword(usernameOrEmail: String, passwordInput: String): AuthResult {
        ensureSeedAccounts()
        val query = usernameOrEmail.trim()
        if (query.isBlank()) {
            return AuthResult.Error("Lütfen kullanıcı adınızı veya e-postanızı girin.")
        }
        if (passwordInput.isBlank()) {
            return AuthResult.Error("Lütfen şifrenizi girin.")
        }

        val account = database.accountDao().findByUsername(query)
            ?: database.accountDao().findByEmail(query)
            ?: return AuthResult.Error("Oyuncu bulunamadı! '$query' adında kayıtlı bir hesap sistemde bulunmuyor.")

        if (account.password != passwordInput) {
            return AuthResult.Error("Hatalı şifre! Girdiğiniz şifre uyuşmuyor.")
        }

        val code = "FC-${kotlin.random.Random.nextInt(1000, 9999)}"
        val profile = UserProfileEntity(
            id = 1,
            username = account.username,
            email = account.email,
            isGuest = false,
            isLoggedIn = true,
            friendCode = code,
            avatarId = account.avatarId,
            favoriteClub = account.favoriteClub,
            jerseyNumber = account.jerseyNumber,
            title = account.title,
            rankPoints = account.rankPoints,
            coins = 1000
        )
        database.userDao().insertOrUpdateProfile(profile)

        return AuthResult.Success("Hoş geldin ${account.username}! Giriş başarılı.")
    }

    suspend fun registerUser(
        username: String,
        email: String,
        favoriteClub: String,
        avatarId: Int,
        jerseyNumber: Int
    ) {
        registerUserWithPassword(username, email, "123456", favoriteClub, avatarId, jerseyNumber)
    }

    suspend fun signInWithGoogle(email: String, displayName: String) {
        val cleanName = "@" + (displayName.lowercase().replace(" ", "_").ifBlank { "google_user" })
        val code = "FC-${kotlin.random.Random.nextInt(1000, 9999)}"
        val profile = UserProfileEntity(
            id = 1,
            username = cleanName,
            email = email,
            isGuest = false,
            isLoggedIn = true,
            friendCode = code,
            avatarId = 1,
            favoriteClub = "Galatasaray",
            jerseyNumber = 10,
            title = "Google Yıldızı",
            rankPoints = 1500,
            coins = 1200
        )
        database.userDao().insertOrUpdateProfile(profile)
    }

    suspend fun playAsGuest(guestNumber: Int = kotlin.random.Random.nextInt(100, 999)) {
        val code = "FC-${kotlin.random.Random.nextInt(1000, 9999)}"
        val profile = UserProfileEntity(
            id = 1,
            username = "Misafir_$guestNumber",
            email = "",
            isGuest = true,
            isLoggedIn = false,
            friendCode = code,
            avatarId = 1,
            favoriteClub = "Real Madrid",
            jerseyNumber = 10,
            title = "Misafir Oyuncu",
            rankPoints = 1200,
            coins = 800
        )
        database.userDao().insertOrUpdateProfile(profile)
    }

    fun getLeaderboard(currentUserProfile: UserProfileEntity): List<LeaderboardEntry> {
        val baseUsers = mutableListOf(
            LeaderboardEntry(1, "EfsaneKral_7", 3, "Real Madrid", 7890, LeagueTier.CHAMPIONS_LEAGUE, "%84"),
            LeaderboardEntry(2, "FutbolProfesoru", 5, "Manchester City", 7240, LeagueTier.CHAMPIONS_LEAGUE, "%79"),
            LeaderboardEntry(3, "Icardi_Wanda99", 2, "Galatasaray", 6850, LeagueTier.CHAMPIONS_LEAGUE, "%76"),
            LeaderboardEntry(4, "Dzeko_Bosna", 4, "Fenerbahçe", 6120, LeagueTier.CHAMPIONS_LEAGUE, "%72"),
            LeaderboardEntry(5, "Semih_Kartal", 1, "Beşiktaş", 5740, LeagueTier.CHAMPIONS_LEAGUE, "%69"),
            LeaderboardEntry(6, "Bellingham_Jude", 6, "Real Madrid", 5310, LeagueTier.SUPER_LIG, "%67"),
            LeaderboardEntry(7, "Arda_Guler_Fan", 2, "Real Madrid", 4150, LeagueTier.SUPER_LIG, "%64"),
            LeaderboardEntry(8, "Hakan_10", 4, "Inter", 3920, LeagueTier.SUPER_LIG, "%61"),
            LeaderboardEntry(9, "TaktikDehası", 1, "Arsenal", 3640, LeagueTier.SUPER_LIG, "%59"),
            LeaderboardEntry(10, "BarisAlperBaros", 3, "Galatasaray", 3410, LeagueTier.TRENDYOL_1_LIG, "%58"),
            LeaderboardEntry(11, "Burak_CR7", 3, "Real Madrid", 2850, LeagueTier.TRENDYOL_1_LIG, "%57"),
            LeaderboardEntry(12, "Kartal_Sergen", 1, "Beşiktaş", 2410, LeagueTier.TRENDYOL_1_LIG, "%55"),
            LeaderboardEntry(13, "PremierLigAsigi", 5, "Liverpool", 2120, LeagueTier.TRENDYOL_1_LIG, "%53"),
            LeaderboardEntry(14, "Kerem99", 2, "Galatasaray", 1640, LeagueTier.TFF_2_LIG, "%51"),
            LeaderboardEntry(15, "Emre_Madrid", 5, "Fenerbahçe", 1120, LeagueTier.TFF_2_LIG, "%48"),
            LeaderboardEntry(16, "AmatörPanter", 4, "Trabzonspor", 890, LeagueTier.TFF_3_LIG, "%45"),
            LeaderboardEntry(17, "SokakFutbolcusu", 6, "Barcelona", 620, LeagueTier.TFF_3_LIG, "%42"),
            LeaderboardEntry(18, "CimbomAslani", 2, "Galatasaray", 410, LeagueTier.AMATOR, "%38")
        )

        // Insert current user based on RP
        val userTier = LeagueTier.fromRp(currentUserProfile.rankPoints)
        val totalGames = currentUserProfile.totalWins + currentUserProfile.totalLosses
        val winRate = if (totalGames > 0) "%${(currentUserProfile.totalWins * 100) / totalGames}" else "%50"

        val userEntry = LeaderboardEntry(
            rank = 0,
            username = "${currentUserProfile.username} (Sen)",
            avatarId = currentUserProfile.avatarId,
            favoriteClub = currentUserProfile.favoriteClub,
            rankPoints = currentUserProfile.rankPoints,
            leagueTier = userTier,
            winRate = winRate,
            isCurrentUser = true
        )

        val all = (baseUsers + userEntry).sortedByDescending { it.rankPoints }
        return all.mapIndexed { index, entry ->
            entry.copy(rank = index + 1)
        }
    }
}
