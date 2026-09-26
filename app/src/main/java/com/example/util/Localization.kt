package com.example.util

enum class AppLanguage(val code: String, val displayName: String, val flag: String) {
    TR("tr", "Türkçe", "🇹🇷"),
    EN("en", "English", "🇬🇧")
}

object Strings {
    fun get(key: String, lang: AppLanguage): String {
        return translations[key]?.get(lang) ?: key
    }

    private val translations = mapOf(
        "app_title" to mapOf(
            AppLanguage.TR to "FOOTBALLER GUESSING ARENA",
            AppLanguage.EN to "FOOTBALLER GUESSING ARENA"
        ),
        "app_subtitle" to mapOf(
            AppLanguage.TR to "Futbol Bilgi Yarışması, Oyuncu Tahmini & Canlı Düellolar",
            AppLanguage.EN to "Football Quiz, Player Guessing & Live Duels"
        ),
        // Navigation & tabs
        "nav_home" to mapOf(AppLanguage.TR to "Ana Sayfa", AppLanguage.EN to "Home"),
        "nav_duel" to mapOf(AppLanguage.TR to "Düello", AppLanguage.EN to "Duel"),
        "nav_leaderboard" to mapOf(AppLanguage.TR to "Sıralama", AppLanguage.EN to "Leaderboard"),
        "nav_league" to mapOf(AppLanguage.TR to "Ligler", AppLanguage.EN to "Leagues"),
        "nav_profile" to mapOf(AppLanguage.TR to "Profil", AppLanguage.EN to "Profile"),

        // Game Modes
        "mode_daily_title" to mapOf(AppLanguage.TR to "Günün Gizemli Futbolcusu", AppLanguage.EN to "Daily Mystery Footballer"),
        "mode_daily_desc" to mapOf(
            AppLanguage.TR to "Her gün yeni bir futbolcu! İpuçlarıyla en az denemede bul.",
            AppLanguage.EN to "A new player every day! Guess with clues in the fewest tries."
        ),
        "mode_unlimited_title" to mapOf(AppLanguage.TR to "Sınırsız Tahmin Modu", AppLanguage.EN to "Unlimited Guessing Mode"),
        "mode_unlimited_desc" to mapOf(
            AppLanguage.TR to "İstediğin kadar oyna! 100+ dünya yıldızı futbolcu seni bekliyor.",
            AppLanguage.EN to "Play as much as you want! 100+ world stars await."
        ),
        "mode_xox_title" to mapOf(AppLanguage.TR to "Futbol XOX Düellosu", AppLanguage.EN to "Football Tic-Tac-Toe"),
        "mode_xox_desc" to mapOf(
            AppLanguage.TR to "Kulüp ve milli takım kriterlerini eşleştirerek 3'lü seri yap!",
            AppLanguage.EN to "Match club & country criteria to complete a 3-in-a-row line!"
        ),
        "mode_duel_title" to mapOf(AppLanguage.TR to "Arkadaşla / Çevrimdışı Düello", AppLanguage.EN to "Duel with Friends / Online"),
        "mode_duel_desc" to mapOf(
            AppLanguage.TR to "Oda kur, arkadaşına meydan oku ve RP puanlarını kap!",
            AppLanguage.EN to "Create a room, challenge friends and earn RP!"
        ),
        "mode_pass_and_play" to mapOf(AppLanguage.TR to "Yan Yana Oyna (Aynı Cihaz)", AppLanguage.EN to "Pass & Play (Same Device)"),
        "play_now" to mapOf(AppLanguage.TR to "Hemen Oyna", AppLanguage.EN to "Play Now"),

        // Attributes & Clues
        "attr_nationality" to mapOf(AppLanguage.TR to "Uyruk", AppLanguage.EN to "Nationality"),
        "attr_league" to mapOf(AppLanguage.TR to "Lig", AppLanguage.EN to "League"),
        "attr_club" to mapOf(AppLanguage.TR to "Kulüp", AppLanguage.EN to "Club"),
        "attr_position" to mapOf(AppLanguage.TR to "Mevki", AppLanguage.EN to "Position"),
        "attr_age" to mapOf(AppLanguage.TR to "Yaş", AppLanguage.EN to "Age"),
        "attr_jersey" to mapOf(AppLanguage.TR to "Forma No", AppLanguage.EN to "Jersey No"),
        "attr_foot" to mapOf(AppLanguage.TR to "Ayak", AppLanguage.EN to "Foot"),
        "clue_older" to mapOf(AppLanguage.TR to "Daha Yaşlı ↑", AppLanguage.EN to "Older ↑"),
        "clue_younger" to mapOf(AppLanguage.TR to "Daha Genç ↓", AppLanguage.EN to "Younger ↓"),
        "clue_higher_no" to mapOf(AppLanguage.TR to "Daha Büyük No ↑", AppLanguage.EN to "Higher No ↑"),
        "clue_lower_no" to mapOf(AppLanguage.TR to "Daha Küçük No ↓", AppLanguage.EN to "Lower No ↓"),
        "clue_match" to mapOf(AppLanguage.TR to "Eşleşti", AppLanguage.EN to "Matched"),
        "search_player_hint" to mapOf(AppLanguage.TR to "Futbolcu adı yaz...", AppLanguage.EN to "Type player name..."),
        "make_guess" to mapOf(AppLanguage.TR to "Tahmin Et", AppLanguage.EN to "Guess"),
        "reveal_clue" to mapOf(AppLanguage.TR to "Ek İpucu Al", AppLanguage.EN to "Get Clue"),
        "attempts_remaining" to mapOf(AppLanguage.TR to "Kalan Hak", AppLanguage.EN to "Guesses Left"),
        "attempts_made" to mapOf(AppLanguage.TR to "Deneme", AppLanguage.EN to "Attempts"),

        // Dialogs & Results
        "win_title" to mapOf(AppLanguage.TR to "MÜKEMMEL! BİLDİN! 🏆", AppLanguage.EN to "BRILLIANT! YOU GUESSED IT! 🏆"),
        "win_subtitle" to mapOf(AppLanguage.TR to "Futbol zekanı konuşturdun!", AppLanguage.EN to "Your football knowledge shines!"),
        "lose_title" to mapOf(AppLanguage.TR to "MAALESEF OLMADI! ❌", AppLanguage.EN to "GAME OVER! ❌"),
        "lose_subtitle" to mapOf(AppLanguage.TR to "Tüm tahmin hakların bitti.", AppLanguage.EN to "All guesses have been used."),
        "secret_player_is" to mapOf(AppLanguage.TR to "Aranan Futbolcu:", AppLanguage.EN to "Secret Footballer:"),
        "play_again" to mapOf(AppLanguage.TR to "Yeni Oyuncu", AppLanguage.EN to "Play Again"),
        "back_to_menu" to mapOf(AppLanguage.TR to "Ana Menü", AppLanguage.EN to "Main Menu"),

        // XOX
        "xox_your_turn" to mapOf(AppLanguage.TR to "Sıra Sende!", AppLanguage.EN to "Your Turn!"),
        "xox_opponent_turn" to mapOf(AppLanguage.TR to "Rakibin Sırası...", AppLanguage.EN to "Opponent's Turn..."),
        "xox_select_player" to mapOf(AppLanguage.TR to "Her iki şartı sağlayan futbolcuyu seç:", AppLanguage.EN to "Select a player matching both criteria:"),
        "xox_draw" to mapOf(AppLanguage.TR to "DOSTLUK KAZANDI (BERABERLİK)", AppLanguage.EN to "DRAW MATCH!"),
        "xox_victory" to mapOf(AppLanguage.TR to "KAZANDIN! TEBRİKLER!", AppLanguage.EN to "VICTORY! CONGRATULATIONS!"),
        "xox_defeat" to mapOf(AppLanguage.TR to "KAYBETTİN!", AppLanguage.EN to "DEFEAT!"),

        // Auth
        "auth_title" to mapOf(AppLanguage.TR to "FOOTBALLER GUESSING ARENA", AppLanguage.EN to "FOOTBALLER GUESSING ARENA"),
        "auth_subtitle" to mapOf(AppLanguage.TR to "Bilgi Yarışması, XOX & Arkadaş Düelloları", AppLanguage.EN to "Quiz, Tic-Tac-Toe & Friend Duels"),
        "google_login" to mapOf(AppLanguage.TR to "Google ile Hızlı Giriş Yap", AppLanguage.EN to "Sign in with Google"),
        "guest_login" to mapOf(AppLanguage.TR to "Misafir Olarak Devam Et (Kayıtsız Oyna)", AppLanguage.EN to "Continue as Guest (Play without account)"),
        "or_create_account" to mapOf(AppLanguage.TR to "VEYA HESAP OLUŞTUR", AppLanguage.EN to "OR CREATE ACCOUNT"),
        "tab_register" to mapOf(AppLanguage.TR to "Kayıt Ol", AppLanguage.EN to "Register"),
        "tab_login" to mapOf(AppLanguage.TR to "Giriş Yap", AppLanguage.EN to "Log In"),
        "username_label" to mapOf(AppLanguage.TR to "Kullanıcı Adı Seç:", AppLanguage.EN to "Choose Username:"),
        "username_login_label" to mapOf(AppLanguage.TR to "Kullanıcı Adı veya E-Posta:", AppLanguage.EN to "Username or Email:"),
        "password_label" to mapOf(AppLanguage.TR to "Giriş Şifresi Belirle (En az 4 karakter):", AppLanguage.EN to "Choose Password (At least 4 chars):"),
        "password_login_label" to mapOf(AppLanguage.TR to "Şifre:", AppLanguage.EN to "Password:"),
        "email_optional" to mapOf(AppLanguage.TR to "E-Posta (İsteğe bağlı):", AppLanguage.EN to "Email (Optional):"),
        "select_avatar" to mapOf(AppLanguage.TR to "Avatar Seçimi:", AppLanguage.EN to "Select Avatar:"),
        "favorite_club" to mapOf(AppLanguage.TR to "Favori Takımın:", AppLanguage.EN to "Favorite Club:"),
        "btn_register" to mapOf(AppLanguage.TR to "Şifreli Profilimi Oluştur ve Başla", AppLanguage.EN to "Create Password Protected Profile"),
        "btn_login" to mapOf(AppLanguage.TR to "Şifreyle Giriş Yap", AppLanguage.EN to "Log In with Password"),

        // Friends & Duel
        "friends_title" to mapOf(AppLanguage.TR to "ARKADAŞLARIM", AppLanguage.EN to "MY FRIENDS"),
        "add_friend_btn" to mapOf(AppLanguage.TR to "+ Kullanıcı Adıyla Ekle", AppLanguage.EN to "+ Add by Username"),
        "online_status" to mapOf(AppLanguage.TR to "Çevrimiçi", AppLanguage.EN to "Online"),
        "offline_status" to mapOf(AppLanguage.TR to "Çevrimdışı", AppLanguage.EN to "Offline"),
        "challenge_guess" to mapOf(AppLanguage.TR to "Tahmin", AppLanguage.EN to "Guess"),
        "challenge_xox" to mapOf(AppLanguage.TR to "XOX", AppLanguage.EN to "XOX"),
        "player_not_found" to mapOf(AppLanguage.TR to "Oyuncu bulunamadı!", AppLanguage.EN to "Player not found!"),
        "registered_samples" to mapOf(AppLanguage.TR to "Oyunda Kayıtlı Örnek Kullanıcılar:", AppLanguage.EN to "Registered Sample Players:"),
        "send_request" to mapOf(AppLanguage.TR to "İstek Gönder", AppLanguage.EN to "Send Request"),
        "close" to mapOf(AppLanguage.TR to "Kapat", AppLanguage.EN to "Close"),
        "incoming_requests" to mapOf(AppLanguage.TR to "GELEN İSTEKLER", AppLanguage.EN to "INCOMING REQUESTS"),
        "accept" to mapOf(AppLanguage.TR to "Kabul Et", AppLanguage.EN to "Accept"),
        "reject" to mapOf(AppLanguage.TR to "Reddet", AppLanguage.EN to "Decline"),
        "create_room" to mapOf(AppLanguage.TR to "Oda Oluştur", AppLanguage.EN to "Create Room"),
        "join_room" to mapOf(AppLanguage.TR to "Odaya Katıl", AppLanguage.EN to "Join Room"),
        "room_code" to mapOf(AppLanguage.TR to "Oda Kodu", AppLanguage.EN to "Room Code"),

        // Profile & Stats
        "streak" to mapOf(AppLanguage.TR to "Seri", AppLanguage.EN to "Streak"),
        "wins" to mapOf(AppLanguage.TR to "Galibiyet", AppLanguage.EN to "Wins"),
        "losses" to mapOf(AppLanguage.TR to "Mağlubiyet", AppLanguage.EN to "Losses"),
        "draws" to mapOf(AppLanguage.TR to "Beraberlik", AppLanguage.EN to "Draws"),
        "win_rate" to mapOf(AppLanguage.TR to "Kazanma Oranı", AppLanguage.EN to "Win Rate"),
        "total_games" to mapOf(AppLanguage.TR to "Toplam Maç", AppLanguage.EN to "Total Games"),
        "achievements" to mapOf(AppLanguage.TR to "BAŞARILAR & ROZETLER", AppLanguage.EN to "ACHIEVEMENTS & BADGES"),
        "match_history" to mapOf(AppLanguage.TR to "SON MAÇLAR GEÇMİŞİ", AppLanguage.EN to "RECENT MATCH HISTORY"),
        "language_select" to mapOf(AppLanguage.TR to "Dil / Language", AppLanguage.EN to "Language / Dil"),
        "switch_to_en" to mapOf(AppLanguage.TR to "English'e Geç", AppLanguage.EN to "English Active"),
        "switch_to_tr" to mapOf(AppLanguage.TR to "Türkçe'ye Geç", AppLanguage.EN to "Switch to Turkish"),
        "guest_warning" to mapOf(
            AppLanguage.TR to "Misafir olarak oynuyorsunuz. İlerlemenizi kaydetmek için hesap oluşturun!",
            AppLanguage.EN to "Playing as guest. Create an account to save your progress!"
        )
    )
}
