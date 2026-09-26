package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.FootballDatabase
import com.example.data.GameRepository
import com.example.data.local.AppDatabase
import com.example.data.local.UserAccountEntity
import com.example.data.local.UserProfileEntity
import com.example.data.model.AttributeClue
import com.example.data.model.AuthResult
import com.example.data.model.FootballPlayer
import com.example.data.model.FriendItem
import com.example.data.model.FriendRequestResult
import com.example.data.model.GuessAttempt
import com.example.data.model.LeaderboardEntry
import com.example.data.model.LeagueTier
import com.example.data.model.Screen
import com.example.data.model.XoxBoardConfig
import com.example.data.model.XoxCell
import com.example.data.model.XoxPlayer
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = GameRepository(AppDatabase.getDatabase(application))

    init {
        viewModelScope.launch {
            repository.ensureSeedAccounts()
        }
    }

    val profile: StateFlow<UserProfileEntity> = repository.userProfile.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UserProfileEntity()
    )

    private val _appLanguage = MutableStateFlow(com.example.util.AppLanguage.TR)
    val appLanguage: StateFlow<com.example.util.AppLanguage> = _appLanguage.asStateFlow()

    fun setLanguage(lang: com.example.util.AppLanguage) {
        _appLanguage.value = lang
    }

    fun toggleLanguage() {
        _appLanguage.value = if (_appLanguage.value == com.example.util.AppLanguage.TR) {
            com.example.util.AppLanguage.EN
        } else {
            com.example.util.AppLanguage.TR
        }
    }

    // Navigation backstack
    private val _screenStack = MutableStateFlow<List<Screen>>(listOf(Screen.Home))
    val currentScreen: StateFlow<Screen> = MutableStateFlow<Screen>(Screen.Home).also { flow ->
        viewModelScope.launch {
            _screenStack.collect { stack ->
                flow.value = stack.lastOrNull() ?: Screen.Home
            }
        }
    }

    fun navigateTo(screen: Screen) {
        val current = _screenStack.value.toMutableList()
        current.add(screen)
        _screenStack.value = current
    }

    fun navigateBack(): Boolean {
        val current = _screenStack.value.toMutableList()
        return if (current.size > 1) {
            current.removeAt(current.size - 1)
            _screenStack.value = current
            true
        } else {
            false
        }
    }

    // -------------------------------------------------------------
    // MODE 1: 15-SECOND PLAYER GUESS STATE WITH CLUE COMPARISON & TURNS
    // -------------------------------------------------------------
    private val _mode1CurrentPlayer = MutableStateFlow<FootballPlayer?>(null)
    val mode1CurrentPlayer: StateFlow<FootballPlayer?> = _mode1CurrentPlayer.asStateFlow()

    private val _mode1TimeLeft = MutableStateFlow(15)
    val mode1TimeLeft: StateFlow<Int> = _mode1TimeLeft.asStateFlow()

    private val _mode1Round = MutableStateFlow(1)
    val mode1Round: StateFlow<Int> = _mode1Round.asStateFlow()

    private val _mode1Score = MutableStateFlow(0)
    val mode1Score: StateFlow<Int> = _mode1Score.asStateFlow()

    private val _mode1OpponentScore = MutableStateFlow(0)
    val mode1OpponentScore: StateFlow<Int> = _mode1OpponentScore.asStateFlow()

    private val _mode1Streak = MutableStateFlow(0)
    val mode1Streak: StateFlow<Int> = _mode1Streak.asStateFlow()

    private val _mode1CurrentTurn = MutableStateFlow(XoxPlayer.GREEN)
    val mode1CurrentTurn: StateFlow<XoxPlayer> = _mode1CurrentTurn.asStateFlow()

    private val _mode1Attempts = MutableStateFlow<List<GuessAttempt>>(emptyList())
    val mode1Attempts: StateFlow<List<GuessAttempt>> = _mode1Attempts.asStateFlow()

    private val _mode1Feedback = MutableStateFlow<String?>(null)
    val mode1Feedback: StateFlow<String?> = _mode1Feedback.asStateFlow()

    private val _mode1FeedbackSuccess = MutableStateFlow(false)
    val mode1FeedbackSuccess: StateFlow<Boolean> = _mode1FeedbackSuccess.asStateFlow()

    private val _mode1RevealedPlayer = MutableStateFlow<FootballPlayer?>(null)
    val mode1RevealedPlayer: StateFlow<FootballPlayer?> = _mode1RevealedPlayer.asStateFlow()

    private val _mode1IsGameOver = MutableStateFlow(false)
    val mode1IsGameOver: StateFlow<Boolean> = _mode1IsGameOver.asStateFlow()

    private val _mode1SearchQuery = MutableStateFlow("")
    val mode1SearchQuery: StateFlow<String> = _mode1SearchQuery.asStateFlow()

    private val _mode1Suggestions = MutableStateFlow<List<FootballPlayer>>(emptyList())
    val mode1Suggestions: StateFlow<List<FootballPlayer>> = _mode1Suggestions.asStateFlow()

    private var mode1TimerJob: Job? = null
    var isMode1Duel = false
        private set
    var isMode1PassAndPlay = false
        private set
    var mode1OpponentName = "Rakip"
        private set

    fun startMode1Game(
        isDuel: Boolean = false,
        isPassAndPlay: Boolean = false,
        opponentName: String = "Rakip"
    ) {
        isMode1Duel = isDuel
        isMode1PassAndPlay = isPassAndPlay
        this.mode1OpponentName = opponentName

        _mode1Round.value = 1
        _mode1Score.value = 0
        _mode1OpponentScore.value = 0
        _mode1Streak.value = 0
        _mode1IsGameOver.value = false
        _mode1SearchQuery.value = ""
        _mode1Suggestions.value = emptyList()
        _mode1Feedback.value = null
        _mode1RevealedPlayer.value = null

        loadNextMode1Player()
    }

    private fun loadNextMode1Player() {
        val pool = FootballDatabase.players
        _mode1CurrentPlayer.value = pool.random()
        _mode1TimeLeft.value = 15
        _mode1CurrentTurn.value = XoxPlayer.GREEN
        _mode1Attempts.value = emptyList()
        _mode1SearchQuery.value = ""
        _mode1Suggestions.value = emptyList()
        _mode1Feedback.value = null
        _mode1RevealedPlayer.value = null

        startMode1Timer()
    }

    private fun startMode1Timer() {
        mode1TimerJob?.cancel()
        _mode1TimeLeft.value = 15
        mode1TimerJob = viewModelScope.launch {
            while (_mode1TimeLeft.value > 0) {
                delay(1000)
                _mode1TimeLeft.value -= 1
            }
            onMode1TimeExpired()
        }
    }

    private fun onMode1TimeExpired() {
        if (_mode1RevealedPlayer.value != null || _mode1IsGameOver.value) return

        val turn = _mode1CurrentTurn.value
        val turnName = if (turn == XoxPlayer.GREEN) "Yeşil" else "Kırmızı"
        val nextTurn = if (turn == XoxPlayer.GREEN) XoxPlayer.RED else XoxPlayer.GREEN
        val nextTurnName = if (nextTurn == XoxPlayer.GREEN) "Yeşil" else "Kırmızı"

        _mode1FeedbackSuccess.value = false
        _mode1Feedback.value = "$turnName takımın 15 saniyesi doldu! Sıra $nextTurnName takıma geçti."
        _mode1CurrentTurn.value = nextTurn

        // Süre dolduğunda bölüm bitmez; sıra diğer oyuncuya geçer ve doğru tahmin bulunana kadar devam eder!
        startMode1Timer()

        // If now bot's turn
        if (!isMode1PassAndPlay && _mode1CurrentTurn.value == XoxPlayer.RED) {
            triggerBotMode1Guess()
        }
    }

    fun onMode1QueryChanged(query: String) {
        _mode1SearchQuery.value = query
        _mode1Suggestions.value = FootballDatabase.searchPlayers(query)
    }

    fun submitMode1Guess(guessedPlayer: FootballPlayer) {
        if (_mode1RevealedPlayer.value != null) return
        val secret = _mode1CurrentPlayer.value ?: return
        mode1TimerJob?.cancel()

        val isCountryMatch = guessedPlayer.country.equals(secret.country, ignoreCase = true)
        val isLeagueMatch = guessedPlayer.league.equals(secret.league, ignoreCase = true)
        val isTeamMatch = guessedPlayer.team.equals(secret.team, ignoreCase = true)
        val isPositionMatch = guessedPlayer.position.equals(secret.position, ignoreCase = true)

        val isAgeMatch = guessedPlayer.age == secret.age
        val ageHint = if (!isAgeMatch) {
            if (secret.age > guessedPlayer.age) "↑" else "↓"
        } else null

        val isNumberMatch = guessedPlayer.jerseyNumber == secret.jerseyNumber
        val numberHint = if (!isNumberMatch) {
            if (secret.jerseyNumber > guessedPlayer.jerseyNumber) "↑" else "↓"
        } else null

        val isFullMatch = guessedPlayer.id == secret.id ||
                (isCountryMatch && isLeagueMatch && isTeamMatch && isPositionMatch && isAgeMatch && isNumberMatch)

        val turn = _mode1CurrentTurn.value
        val isP1 = turn == XoxPlayer.GREEN

        val attempt = GuessAttempt(
            player = guessedPlayer,
            isPlayerOne = isP1,
            playerNameTitle = if (isP1) "1. Oyuncu (Yeşil)" else (if (isMode1PassAndPlay || isMode1Duel) "2. Oyuncu (Kırmızı)" else mode1OpponentName),
            countryClue = AttributeClue(
                label = "ÜLKE",
                value = guessedPlayer.countryFlag,
                subValue = guessedPlayer.country,
                isMatch = isCountryMatch
            ),
            leagueClue = AttributeClue(
                label = "LİG",
                value = guessedPlayer.league,
                isMatch = isLeagueMatch
            ),
            teamClue = AttributeClue(
                label = "TAKIM",
                value = guessedPlayer.team,
                isMatch = isTeamMatch
            ),
            positionClue = AttributeClue(
                label = "POZ",
                value = guessedPlayer.position,
                isMatch = isPositionMatch
            ),
            ageClue = AttributeClue(
                label = "YAŞ",
                value = "${guessedPlayer.age}",
                isMatch = isAgeMatch,
                directionHint = ageHint
            ),
            numberClue = AttributeClue(
                label = "NO.",
                value = "${guessedPlayer.jerseyNumber}",
                isMatch = isNumberMatch,
                directionHint = numberHint
            ),
            isFullMatch = isFullMatch
        )

        _mode1Attempts.value = listOf(attempt) + _mode1Attempts.value
        _mode1SearchQuery.value = ""
        _mode1Suggestions.value = emptyList()

        if (isFullMatch) {
            val speedBonus = _mode1TimeLeft.value * 10
            val totalEarned = 150 + speedBonus
            if (isP1) {
                _mode1Score.value += totalEarned
                _mode1Streak.value += 1
            } else {
                _mode1OpponentScore.value += totalEarned
            }

            _mode1FeedbackSuccess.value = true
            _mode1Feedback.value = "DOĞRU TAHMİN! ${guessedPlayer.name} bilindi! (+$totalEarned Puan)"
            _mode1RevealedPlayer.value = secret

            scheduleNextMode1Round()
        } else {
            // Wrong guess -> Turn passes to the other player!
            val nextTurn = if (isP1) XoxPlayer.RED else XoxPlayer.GREEN
            val nextTurnName = if (nextTurn == XoxPlayer.GREEN) "Yeşil Takım" else "Kırmızı Takım"
            _mode1CurrentTurn.value = nextTurn
            _mode1FeedbackSuccess.value = false
            _mode1Feedback.value = "${guessedPlayer.name} tam eşleşmedi. İpuçları eklendi, sıra $nextTurnName'da!"

            startMode1Timer()

            if (!isMode1PassAndPlay && nextTurn == XoxPlayer.RED) {
                triggerBotMode1Guess()
            }
        }
    }

    private fun triggerBotMode1Guess() {
        viewModelScope.launch {
            delay(2200)
            if (_mode1RevealedPlayer.value != null || _mode1CurrentTurn.value != XoxPlayer.RED) return@launch

            val secret = _mode1CurrentPlayer.value ?: return@launch
            val candidates = FootballDatabase.players.filter { p ->
                _mode1Attempts.value.none { it.player.id == p.id }
            }

            // AI might guess smartly or random plausible
            val matching = candidates.filter { it.country == secret.country || it.league == secret.league }
            val botPick = if (matching.isNotEmpty() && Random.nextInt(100) < 55) {
                matching.random()
            } else {
                candidates.randomOrNull() ?: secret
            }

            submitMode1Guess(botPick)
        }
    }

    private fun scheduleNextMode1Round() {
        viewModelScope.launch {
            delay(2800)
            if (_mode1Round.value >= 5) {
                finishMode1Game()
            } else {
                _mode1Round.value += 1
                loadNextMode1Player()
            }
        }
    }

    private fun finishMode1Game() {
        _mode1IsGameOver.value = true
        val userScore = _mode1Score.value
        val oppScore = _mode1OpponentScore.value
        val isWin = userScore > oppScore
        val isDraw = userScore == oppScore
        val rpChange = if (isWin) 80 else if (isDraw) 20 else -25

        viewModelScope.launch {
            repository.recordMatch(
                mode = "15-Saniye Oyuncu Tahmin",
                opponentName = if (isMode1PassAndPlay) "2. Oyuncu" else mode1OpponentName,
                userScore = userScore,
                opponentScore = oppScore,
                isWin = isWin,
                isDraw = isDraw,
                rpChange = rpChange,
                currentProfile = profile.value
            )
        }
    }

    // -------------------------------------------------------------
    // MODE 2: FOOTBALL XOX (DYNAMIC BOARDS & TURN-PASS ON WRONG GUESS)
    // -------------------------------------------------------------
    private val _xoxBoardConfig = MutableStateFlow(FootballDatabase.getRandomBoardConfig())
    val xoxBoardConfig: StateFlow<XoxBoardConfig> = _xoxBoardConfig.asStateFlow()

    private val _xoxCells = MutableStateFlow(
        List(9) { index -> XoxCell(row = index / 3, col = index % 3) }
    )
    val xoxCells: StateFlow<List<XoxCell>> = _xoxCells.asStateFlow()

    private val _xoxCurrentTurn = MutableStateFlow(XoxPlayer.GREEN)
    val xoxCurrentTurn: StateFlow<XoxPlayer> = _xoxCurrentTurn.asStateFlow()

    private val _xoxWinner = MutableStateFlow<XoxPlayer?>(null)
    val xoxWinner: StateFlow<XoxPlayer?> = _xoxWinner.asStateFlow()

    private val _xoxIsDraw = MutableStateFlow(false)
    val xoxIsDraw: StateFlow<Boolean> = _xoxIsDraw.asStateFlow()

    private val _xoxWinningLine = MutableStateFlow<List<Int>?>(null)
    val xoxWinningLine: StateFlow<List<Int>?> = _xoxWinningLine.asStateFlow()

    private val _xoxSelectedCellIndex = MutableStateFlow<Int?>(null)
    val xoxSelectedCellIndex: StateFlow<Int?> = _xoxSelectedCellIndex.asStateFlow()

    private val _xoxSearchQuery = MutableStateFlow("")
    val xoxSearchQuery: StateFlow<String> = _xoxSearchQuery.asStateFlow()

    private val _xoxSuggestions = MutableStateFlow<List<FootballPlayer>>(emptyList())
    val xoxSuggestions: StateFlow<List<FootballPlayer>> = _xoxSuggestions.asStateFlow()

    private val _xoxNotificationMessage = MutableStateFlow<String?>(null)
    val xoxNotificationMessage: StateFlow<String?> = _xoxNotificationMessage.asStateFlow()

    private var xoxIsPassAndPlay = false
    private var xoxOpponentName = "Rakip Bot"

    fun startMode2Game(isPassAndPlay: Boolean = false, opponentName: String = "Rakip Bot") {
        xoxIsPassAndPlay = isPassAndPlay
        xoxOpponentName = opponentName

        // DYNAMIC: Pick a random fresh board every game!
        val randomConfig = FootballDatabase.getRandomBoardConfig(excludeId = _xoxBoardConfig.value.id)
        _xoxBoardConfig.value = randomConfig

        _xoxCells.value = List(9) { index -> XoxCell(row = index / 3, col = index % 3) }
        _xoxCurrentTurn.value = XoxPlayer.GREEN
        _xoxWinner.value = null
        _xoxIsDraw.value = false
        _xoxWinningLine.value = null
        _xoxSelectedCellIndex.value = null
        _xoxSearchQuery.value = ""
        _xoxSuggestions.value = emptyList()
        _xoxNotificationMessage.value = "Yeni tahta yüklendi: ${randomConfig.title}. Başarılar!"
    }

    fun randomizeXoxBoard() {
        val newConfig = FootballDatabase.getRandomBoardConfig(excludeId = _xoxBoardConfig.value.id)
        _xoxBoardConfig.value = newConfig
        _xoxCells.value = List(9) { index -> XoxCell(row = index / 3, col = index % 3) }
        _xoxCurrentTurn.value = XoxPlayer.GREEN
        _xoxWinner.value = null
        _xoxIsDraw.value = false
        _xoxWinningLine.value = null
        _xoxSelectedCellIndex.value = null
        _xoxNotificationMessage.value = "Yeni tahta seçildi: ${newConfig.title}"
    }

    fun selectXoxCell(index: Int) {
        if (_xoxWinner.value != null || _xoxIsDraw.value) return
        val cell = _xoxCells.value.getOrNull(index) ?: return
        if (cell.claimedBy != null) return

        _xoxSelectedCellIndex.value = index
        _xoxSearchQuery.value = ""
        _xoxSuggestions.value = emptyList()
    }

    fun closeXoxDialog() {
        _xoxSelectedCellIndex.value = null
        _xoxSearchQuery.value = ""
        _xoxSuggestions.value = emptyList()
    }

    fun onXoxQueryChanged(query: String) {
        _xoxSearchQuery.value = query
        _xoxSuggestions.value = FootballDatabase.searchPlayers(query)
    }

    fun submitXoxPlayer(player: FootballPlayer) {
        val cellIndex = _xoxSelectedCellIndex.value ?: return
        val rowIdx = cellIndex / 3
        val colIdx = cellIndex % 3
        val config = _xoxBoardConfig.value
        val rowClub = config.rowClubs[rowIdx]
        val colClub = config.colClubs[colIdx]

        // Check if already used in this match
        val alreadyUsed = _xoxCells.value.any { it.playerName.equals(player.name, ignoreCase = true) }
        val isValid = !alreadyUsed && FootballDatabase.checkPlayerMatchesBoth(player, rowClub, colClub)

        val turn = _xoxCurrentTurn.value
        val isGreen = turn == XoxPlayer.GREEN
        val currentTeamName = if (isGreen) "Yeşil Takım" else "Kırmızı Takım"
        val nextTeamName = if (isGreen) "Kırmızı Takım" else "Yeşil Takım"

        if (!isValid) {
            // YANLIŞ SEÇİM: KULLANICI YANLIŞ TAHMİN YAPTIĞINDA SIRA DİĞER KULLANICIYA GEÇER!
            closeXoxDialog()
            _xoxNotificationMessage.value = "${player.name} bu iki kulüpte de oynamadı! Sıra $nextTeamName'a geçti."
            _xoxCurrentTurn.value = if (isGreen) XoxPlayer.RED else XoxPlayer.GREEN

            // If now bot's turn:
            if (!xoxIsPassAndPlay && _xoxCurrentTurn.value == XoxPlayer.RED) {
                triggerBotXoxMove()
            }
            return
        }

        // DOĞRU SEÇİM: Kare o takımın rengine boyanır
        val currentCells = _xoxCells.value.toMutableList()
        currentCells[cellIndex] = XoxCell(
            row = rowIdx,
            col = colIdx,
            claimedBy = turn,
            playerName = player.name,
            playerClub = player.team
        )
        _xoxCells.value = currentCells
        closeXoxDialog()
        _xoxNotificationMessage.value = "${player.name} doğru! $currentTeamName kareyi aldı."

        checkXoxGameResult()
    }

    private fun checkXoxGameResult() {
        val cells = _xoxCells.value
        val winPatterns = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8), // Rows
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8), // Cols
            listOf(0, 4, 8), listOf(2, 4, 6)             // Diagonals
        )

        for (pattern in winPatterns) {
            val p0 = cells[pattern[0]].claimedBy
            val p1 = cells[pattern[1]].claimedBy
            val p2 = cells[pattern[2]].claimedBy
            if (p0 != null && p0 == p1 && p1 == p2) {
                _xoxWinner.value = p0
                _xoxWinningLine.value = pattern
                handleXoxGameOver(winner = p0, isDraw = false)
                return
            }
        }

        // Check draw
        if (cells.all { it.claimedBy != null }) {
            _xoxIsDraw.value = true
            handleXoxGameOver(winner = null, isDraw = true)
            return
        }

        // Switch turn to other player
        _xoxCurrentTurn.value = if (_xoxCurrentTurn.value == XoxPlayer.GREEN) XoxPlayer.RED else XoxPlayer.GREEN

        // If single player vs Bot and it's Bot's turn (RED):
        if (!xoxIsPassAndPlay && _xoxCurrentTurn.value == XoxPlayer.RED) {
            triggerBotXoxMove()
        }
    }

    private fun triggerBotXoxMove() {
        viewModelScope.launch {
            delay(1400)
            val availableIndices = _xoxCells.value.indices.filter { _xoxCells.value[it].claimedBy == null }
            if (availableIndices.isEmpty() || _xoxWinner.value != null || _xoxIsDraw.value) return@launch

            val chosenIndex = availableIndices.random()
            val rowIdx = chosenIndex / 3
            val colIdx = chosenIndex % 3
            val config = _xoxBoardConfig.value
            val rowClub = config.rowClubs[rowIdx]
            val colClub = config.colClubs[colIdx]

            val matching = FootballDatabase.findMatchingPlayersForIntersection(rowClub, colClub)
                .filter { p -> _xoxCells.value.none { it.playerName.equals(p.name, ignoreCase = true) } }

            // Bot tries to find a matching player (or 25% chance of wrong guess to pass turn back)
            if (matching.isNotEmpty() && Random.nextInt(100) < 75) {
                val botPlayer = matching.random()
                val currentCells = _xoxCells.value.toMutableList()
                currentCells[chosenIndex] = XoxCell(
                    row = rowIdx,
                    col = colIdx,
                    claimedBy = XoxPlayer.RED,
                    playerName = botPlayer.name,
                    playerClub = botPlayer.team
                )
                _xoxCells.value = currentCells
                _xoxNotificationMessage.value = "Kırmızı Takım (Bot) ${botPlayer.name} ile kareyi aldı!"
                checkXoxGameResult()
            } else {
                // Bot made a wrong move! Turn passes back to User!
                _xoxNotificationMessage.value = "Kırmızı Takım yanlış seçim yaptı! Sıra Yeşil Takıma geçti."
                _xoxCurrentTurn.value = XoxPlayer.GREEN
            }
        }
    }

    private fun handleXoxGameOver(winner: XoxPlayer?, isDraw: Boolean) {
        val isWin = winner == XoxPlayer.GREEN
        val rpChange = if (isWin) 100 else if (isDraw) 20 else -30

        viewModelScope.launch {
            repository.recordMatch(
                mode = "Futbol XOX (3x3)",
                opponentName = if (xoxIsPassAndPlay) "2. Oyuncu (Kırmızı)" else xoxOpponentName,
                userScore = if (isWin) 1 else 0,
                opponentScore = if (winner == XoxPlayer.RED) 1 else 0,
                isWin = isWin,
                isDraw = isDraw,
                rpChange = rpChange,
                currentProfile = profile.value
            )
        }
    }

    // -------------------------------------------------------------
    // DUEL / ROOM LOBBY STATE
    // -------------------------------------------------------------
    private val _roomCode = MutableStateFlow("FUT-${Random.nextInt(100, 999)}")
    val roomCode: StateFlow<String> = _roomCode.asStateFlow()

    private val _joinCodeInput = MutableStateFlow("")
    val joinCodeInput: StateFlow<String> = _joinCodeInput.asStateFlow()

    val friendsList: StateFlow<List<FriendItem>> = repository.friends.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val pendingRequests: StateFlow<List<FriendItem>> = repository.pendingRequests.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val registeredAccounts: StateFlow<List<UserAccountEntity>> = repository.registeredAccounts.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> = _authError.asStateFlow()

    private val _authSuccess = MutableStateFlow<String?>(null)
    val authSuccess: StateFlow<String?> = _authSuccess.asStateFlow()

    fun clearAuthMessages() {
        _authError.value = null
        _authSuccess.value = null
    }

    fun registerWithPassword(
        username: String,
        email: String,
        passwordInput: String,
        favoriteClub: String,
        avatarId: Int,
        jerseyNumber: Int,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            _authError.value = null
            _authSuccess.value = null
            when (val result = repository.registerUserWithPassword(
                username = username,
                email = email,
                passwordInput = passwordInput,
                favoriteClub = favoriteClub,
                avatarId = avatarId,
                jerseyNumber = jerseyNumber
            )) {
                is AuthResult.Success -> {
                    _authSuccess.value = result.message
                    delay(300)
                    onSuccess()
                }
                is AuthResult.Error -> {
                    _authError.value = result.message
                }
            }
        }
    }

    fun loginWithPassword(
        usernameOrEmail: String,
        passwordInput: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            _authError.value = null
            _authSuccess.value = null
            when (val result = repository.loginUserWithPassword(usernameOrEmail, passwordInput)) {
                is AuthResult.Success -> {
                    _authSuccess.value = result.message
                    delay(300)
                    onSuccess()
                }
                is AuthResult.Error -> {
                    _authError.value = result.message
                }
            }
        }
    }

    private val _friendAddError = MutableStateFlow<String?>(null)
    val friendAddError: StateFlow<String?> = _friendAddError.asStateFlow()

    private val _friendAddSuccess = MutableStateFlow<String?>(null)
    val friendAddSuccess: StateFlow<String?> = _friendAddSuccess.asStateFlow()

    fun clearFriendMessages() {
        _friendAddError.value = null
        _friendAddSuccess.value = null
    }

    fun sendFriendRequest(targetUsername: String, onFinished: (Boolean) -> Unit) {
        viewModelScope.launch {
            _friendAddError.value = null
            _friendAddSuccess.value = null
            val currentUserName = profile.value.username
            when (val result = repository.sendFriendRequest(targetUsername, currentUserName)) {
                is FriendRequestResult.Success -> {
                    _friendAddSuccess.value = result.message
                    onFinished(true)
                    // Auto-accept simulation after brief delay:
                    delay(3000)
                    val pending = pendingRequests.value
                    val target = pending.find {
                        it.name.equals(targetUsername, ignoreCase = true) ||
                        it.name.equals("@$targetUsername", ignoreCase = true) ||
                        it.name.replace("@", "").equals(targetUsername.replace("@", ""), ignoreCase = true)
                    }
                    target?.let {
                        repository.acceptFriendRequest(it.id)
                    }
                }
                is FriendRequestResult.Error -> {
                    _friendAddError.value = result.message
                    onFinished(false)
                }
            }
        }
    }

    fun acceptFriendRequest(friendId: String) {
        viewModelScope.launch {
            repository.acceptFriendRequest(friendId)
        }
    }

    fun rejectFriendRequest(friendId: String) {
        viewModelScope.launch {
            repository.rejectFriendRequest(friendId)
        }
    }

    fun removeFriend(id: String) {
        viewModelScope.launch {
            repository.deleteFriend(id)
        }
    }

    fun registerUser(
        username: String,
        email: String,
        favoriteClub: String,
        avatarId: Int,
        jerseyNumber: Int
    ) {
        registerWithPassword(username, email, "123456", favoriteClub, avatarId, jerseyNumber, onSuccess = {})
    }

    fun signInWithGoogle(email: String = "mert.yavas.94@gmail.com", name: String = "Mert Yavaş") {
        viewModelScope.launch {
            repository.signInWithGoogle(email, name)
        }
    }

    fun playAsGuest() {
        viewModelScope.launch {
            repository.playAsGuest()
        }
    }

    fun onJoinCodeChanged(code: String) {
        _joinCodeInput.value = code.uppercase()
    }

    fun generateNewRoomCode() {
        _roomCode.value = "FUT-${Random.nextInt(100, 999)}"
    }

    fun sendChallengeToFriend(friend: FriendItem, mode: Screen) {
        viewModelScope.launch {
            delay(1200)
            navigateTo(mode)
        }
    }

    // -------------------------------------------------------------
    // LEADERBOARD & STATS
    // -------------------------------------------------------------
    val leaderboard: List<LeaderboardEntry>
        get() = repository.getLeaderboard(profile.value)

    val matchHistory = repository.matchHistory.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val achievements: List<com.example.data.model.AchievementBadge>
        get() = repository.getAchievements(profile.value)

    fun updateProfile(
        username: String,
        avatarId: Int,
        favoriteClub: String,
        jerseyNumber: Int,
        title: String
    ) {
        viewModelScope.launch {
            val updated = profile.value.copy(
                username = username.trim().ifBlank { "Futbolcu_10" },
                avatarId = avatarId,
                favoriteClub = favoriteClub,
                jerseyNumber = jerseyNumber,
                title = title
            )
            repository.updateProfile(updated)
        }
    }
}
