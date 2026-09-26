package com.example.data

import com.example.data.model.FootballPlayer
import com.example.data.model.XoxBoardConfig

object FootballDatabase {

    val players: List<FootballPlayer> = listOf(
        FootballPlayer(
            id = "vinicius_jr",
            name = "Vinicius Junior",
            country = "Brezilya",
            countryFlag = "🇧🇷",
            league = "La Liga",
            team = "Real Madrid",
            position = "FOR",
            age = 24,
            jerseyNumber = 7,
            clubsPlayedFor = listOf("Flamengo", "Real Madrid")
        ),
        FootballPlayer(
            id = "erling_haaland",
            name = "Erling Haaland",
            country = "Norveç",
            countryFlag = "🇳🇴",
            league = "Premier League",
            team = "Manchester City",
            position = "FOR",
            age = 24,
            jerseyNumber = 9,
            clubsPlayedFor = listOf("Bryne", "Molde", "RB Salzburg", "Borussia Dortmund", "Manchester City")
        ),
        FootballPlayer(
            id = "kylian_mbappe",
            name = "Kylian Mbappé",
            country = "Fransa",
            countryFlag = "🇫🇷",
            league = "La Liga",
            team = "Real Madrid",
            position = "FOR",
            age = 25,
            jerseyNumber = 9,
            clubsPlayedFor = listOf("Monaco", "Paris Saint-Germain", "Real Madrid")
        ),
        FootballPlayer(
            id = "jude_bellingham",
            name = "Jude Bellingham",
            country = "İngiltere",
            countryFlag = "🏴󠁧󠁢󠁥󠁮󠁧󠁿",
            league = "La Liga",
            team = "Real Madrid",
            position = "OS",
            age = 21,
            jerseyNumber = 5,
            clubsPlayedFor = listOf("Birmingham City", "Borussia Dortmund", "Real Madrid")
        ),
        FootballPlayer(
            id = "lionel_messi",
            name = "Lionel Messi",
            country = "Arjantin",
            countryFlag = "🇦🇷",
            league = "MLS",
            team = "Inter Miami",
            position = "SĞO",
            age = 37,
            jerseyNumber = 10,
            clubsPlayedFor = listOf("Barcelona", "Paris Saint-Germain", "Inter Miami")
        ),
        FootballPlayer(
            id = "cristiano_ronaldo",
            name = "Cristiano Ronaldo",
            country = "Portekiz",
            countryFlag = "🇵🇹",
            league = "Saudi Pro League",
            team = "Al-Nassr",
            position = "FOR",
            age = 40,
            jerseyNumber = 7,
            clubsPlayedFor = listOf("Sporting CP", "Manchester United", "Real Madrid", "Juventus", "Al-Nassr")
        ),
        FootballPlayer(
            id = "arda_guler",
            name = "Arda Güler",
            country = "Türkiye",
            countryFlag = "🇹🇷",
            league = "La Liga",
            team = "Real Madrid",
            position = "OOS",
            age = 20,
            jerseyNumber = 15,
            clubsPlayedFor = listOf("Gençlerbirliği", "Fenerbahçe", "Real Madrid")
        ),
        FootballPlayer(
            id = "mauro_icardi",
            name = "Mauro Icardi",
            country = "Arjantin",
            countryFlag = "🇦🇷",
            league = "Süper Lig",
            team = "Galatasaray",
            position = "FOR",
            age = 32,
            jerseyNumber = 9,
            clubsPlayedFor = listOf("Sampdoria", "Inter", "Paris Saint-Germain", "Galatasaray")
        ),
        FootballPlayer(
            id = "victor_osimhen",
            name = "Victor Osimhen",
            country = "Nijerya",
            countryFlag = "🇳🇬",
            league = "Süper Lig",
            team = "Galatasaray",
            position = "FOR",
            age = 26,
            jerseyNumber = 45,
            clubsPlayedFor = listOf("Wolfsburg", "Charleroi", "Lille", "Napoli", "Galatasaray")
        ),
        FootballPlayer(
            id = "edin_dzeko",
            name = "Edin Dzeko",
            country = "Bosna Hersek",
            countryFlag = "🇧🇦",
            league = "Süper Lig",
            team = "Fenerbahçe",
            position = "FOR",
            age = 39,
            jerseyNumber = 9,
            clubsPlayedFor = listOf("Wolfsburg", "Manchester City", "Roma", "Inter", "Fenerbahçe")
        ),
        FootballPlayer(
            id = "baris_alper_yilmaz",
            name = "Barış Alper Yılmaz",
            country = "Türkiye",
            countryFlag = "🇹🇷",
            league = "Süper Lig",
            team = "Galatasaray",
            position = "SĞK",
            age = 24,
            jerseyNumber = 53,
            clubsPlayedFor = listOf("Ankara Demirspor", "Keçiörengücü", "Galatasaray")
        ),
        FootballPlayer(
            id = "kevin_de_bruyne",
            name = "Kevin De Bruyne",
            country = "Belçika",
            countryFlag = "🇧🇪",
            league = "Premier League",
            team = "Manchester City",
            position = "OS",
            age = 33,
            jerseyNumber = 17,
            clubsPlayedFor = listOf("Genk", "Chelsea", "Werder Bremen", "Wolfsburg", "Manchester City")
        ),
        FootballPlayer(
            id = "mohamed_salah",
            name = "Mohamed Salah",
            country = "Mısır",
            countryFlag = "🇪🇬",
            league = "Premier League",
            team = "Liverpool",
            position = "SĞK",
            age = 32,
            jerseyNumber = 11,
            clubsPlayedFor = listOf("Basel", "Chelsea", "Fiorentina", "Roma", "Liverpool")
        ),
        FootballPlayer(
            id = "harry_kane",
            name = "Harry Kane",
            country = "İngiltere",
            countryFlag = "🏴󠁧󠁢󠁥󠁮󠁧󠁿",
            league = "Bundesliga",
            team = "Bayern Münih",
            position = "FOR",
            age = 31,
            jerseyNumber = 9,
            clubsPlayedFor = listOf("Tottenham Hotspur", "Leicester City", "Norwich City", "Millwall", "Bayern Münih")
        ),
        FootballPlayer(
            id = "lamine_yamal",
            name = "Lamine Yamal",
            country = "İspanya",
            countryFlag = "🇪🇸",
            league = "La Liga",
            team = "Barcelona",
            position = "SĞK",
            age = 17,
            jerseyNumber = 19,
            clubsPlayedFor = listOf("Barcelona")
        ),
        FootballPlayer(
            id = "rodri",
            name = "Rodri",
            country = "İspanya",
            countryFlag = "🇪🇸",
            league = "Premier League",
            team = "Manchester City",
            position = "DOS",
            age = 28,
            jerseyNumber = 16,
            clubsPlayedFor = listOf("Villarreal", "Atletico Madrid", "Manchester City")
        ),
        FootballPlayer(
            id = "bukayo_saka",
            name = "Bukayo Saka",
            country = "İngiltere",
            countryFlag = "🏴󠁧󠁢󠁥󠁮󠁧󠁿",
            league = "Premier League",
            team = "Arsenal",
            position = "SĞK",
            age = 23,
            jerseyNumber = 7,
            clubsPlayedFor = listOf("Arsenal")
        ),
        FootballPlayer(
            id = "hakan_calhanoglu",
            name = "Hakan Çalhanoğlu",
            country = "Türkiye",
            countryFlag = "🇹🇷",
            league = "Serie A",
            team = "Inter",
            position = "OS",
            age = 31,
            jerseyNumber = 20,
            clubsPlayedFor = listOf("Karlsruher", "Hamburg", "Bayer Leverkusen", "AC Milan", "Inter")
        ),
        FootballPlayer(
            id = "semih_kilicsoy",
            name = "Semih Kılıçsoy",
            country = "Türkiye",
            countryFlag = "🇹🇷",
            league = "Süper Lig",
            team = "Beşiktaş",
            position = "FOR",
            age = 19,
            jerseyNumber = 9,
            clubsPlayedFor = listOf("Beşiktaş")
        ),
        FootballPlayer(
            id = "ferdi_kadioglu",
            name = "Ferdi Kadıoğlu",
            country = "Türkiye",
            countryFlag = "🇹🇷",
            league = "Premier League",
            team = "Brighton",
            position = "SLB",
            age = 25,
            jerseyNumber = 24,
            clubsPlayedFor = listOf("NEC Nijmegen", "Fenerbahçe", "Brighton")
        ),
        FootballPlayer(
            id = "kerem_aktürkoglu",
            name = "Kerem Aktürkoğlu",
            country = "Türkiye",
            countryFlag = "🇹🇷",
            league = "Primeira Liga",
            team = "Benfica",
            position = "SLK",
            age = 26,
            jerseyNumber = 17,
            clubsPlayedFor = listOf("Başakşehir", "Erzincanspor", "Galatasaray", "Benfica")
        ),
        FootballPlayer(
            id = "kenan_yildiz",
            name = "Kenan Yıldız",
            country = "Türkiye",
            countryFlag = "🇹🇷",
            league = "Serie A",
            team = "Juventus",
            position = "SLK",
            age = 19,
            jerseyNumber = 10,
            clubsPlayedFor = listOf("Bayern Münih", "Juventus")
        ),
        FootballPlayer(
            id = "florian_wirtz",
            name = "Florian Wirtz",
            country = "Almanya",
            countryFlag = "🇩🇪",
            league = "Bundesliga",
            team = "Bayer Leverkusen",
            position = "OOS",
            age = 21,
            jerseyNumber = 10,
            clubsPlayedFor = listOf("Köln", "Bayer Leverkusen")
        ),
        FootballPlayer(
            id = "luka_modric",
            name = "Luka Modric",
            country = "Hırvatistan",
            countryFlag = "🇭🇷",
            league = "La Liga",
            team = "Real Madrid",
            position = "OS",
            age = 39,
            jerseyNumber = 10,
            clubsPlayedFor = listOf("Dinamo Zagreb", "Tottenham Hotspur", "Real Madrid")
        ),
        FootballPlayer(
            id = "robert_lewandowski",
            name = "Robert Lewandowski",
            country = "Polonya",
            countryFlag = "🇵🇱",
            league = "La Liga",
            team = "Barcelona",
            position = "FOR",
            age = 36,
            jerseyNumber = 9,
            clubsPlayedFor = listOf("Lech Poznan", "Borussia Dortmund", "Bayern Münih", "Barcelona")
        ),
        FootballPlayer(
            id = "cole_palmer",
            name = "Cole Palmer",
            country = "İngiltere",
            countryFlag = "🏴󠁧󠁢󠁥󠁮󠁧󠁿",
            league = "Premier League",
            team = "Chelsea",
            position = "OOS",
            age = 22,
            jerseyNumber = 20,
            clubsPlayedFor = listOf("Manchester City", "Chelsea")
        ),
        FootballPlayer(
            id = "phil_foden",
            name = "Phil Foden",
            country = "İngiltere",
            countryFlag = "🏴󠁧󠁢󠁥󠁮󠁧󠁿",
            league = "Premier League",
            team = "Manchester City",
            position = "SLK",
            age = 24,
            jerseyNumber = 47,
            clubsPlayedFor = listOf("Manchester City")
        ),
        FootballPlayer(
            id = "declan_rice",
            name = "Declan Rice",
            country = "İngiltere",
            countryFlag = "🏴󠁧󠁢󠁥󠁮󠁧󠁿",
            league = "Premier League",
            team = "Arsenal",
            position = "DOS",
            age = 26,
            jerseyNumber = 41,
            clubsPlayedFor = listOf("West Ham United", "Arsenal")
        ),
        FootballPlayer(
            id = "antoine_griezmann",
            name = "Antoine Griezmann",
            country = "Fransa",
            countryFlag = "🇫🇷",
            league = "La Liga",
            team = "Atletico Madrid",
            position = "FOR",
            age = 33,
            jerseyNumber = 7,
            clubsPlayedFor = listOf("Real Sociedad", "Atletico Madrid", "Barcelona")
        ),
        FootballPlayer(
            id = "son_heung_min",
            name = "Son Heung-min",
            country = "Güney Kore",
            countryFlag = "🇰🇷",
            league = "Premier League",
            team = "Tottenham Hotspur",
            position = "SLK",
            age = 32,
            jerseyNumber = 7,
            clubsPlayedFor = listOf("Hamburg", "Bayer Leverkusen", "Tottenham Hotspur")
        ),
        FootballPlayer(
            id = "federico_valverde",
            name = "Federico Valverde",
            country = "Uruguay",
            countryFlag = "🇺🇾",
            league = "La Liga",
            team = "Real Madrid",
            position = "OS",
            age = 26,
            jerseyNumber = 8,
            clubsPlayedFor = listOf("Penarol", "Deportivo La Coruna", "Real Madrid")
        ),
        FootballPlayer(
            id = "lautaro_martinez",
            name = "Lautaro Martínez",
            country = "Arjantin",
            countryFlag = "🇦🇷",
            league = "Serie A",
            team = "Inter",
            position = "FOR",
            age = 27,
            jerseyNumber = 10,
            clubsPlayedFor = listOf("Racing Club", "Inter")
        ),
        FootballPlayer(
            id = "pedri",
            name = "Pedri",
            country = "İspanya",
            countryFlag = "🇪🇸",
            league = "La Liga",
            team = "Barcelona",
            position = "OS",
            age = 22,
            jerseyNumber = 8,
            clubsPlayedFor = listOf("Las Palmas", "Barcelona")
        ),
        FootballPlayer(
            id = "jamal_musiala",
            name = "Jamal Musiala",
            country = "Almanya",
            countryFlag = "🇩🇪",
            league = "Bundesliga",
            team = "Bayern Münih",
            position = "OOS",
            age = 22,
            jerseyNumber = 42,
            clubsPlayedFor = listOf("Chelsea", "Bayern Münih")
        ),
        FootballPlayer(
            id = "virgil_van_dijk",
            name = "Virgil van Dijk",
            country = "Hollanda",
            countryFlag = "🇳🇱",
            league = "Premier League",
            team = "Liverpool",
            position = "STP",
            age = 33,
            jerseyNumber = 4,
            clubsPlayedFor = listOf("Groningen", "Celtic", "Southampton", "Liverpool")
        ),
        FootballPlayer(
            id = "thibaut_courtois",
            name = "Thibaut Courtois",
            country = "Belçika",
            countryFlag = "🇧🇪",
            league = "La Liga",
            team = "Real Madrid",
            position = "KL",
            age = 32,
            jerseyNumber = 1,
            clubsPlayedFor = listOf("Genk", "Atletico Madrid", "Chelsea", "Real Madrid")
        ),
        FootballPlayer(
            id = "alisson_becker",
            name = "Alisson Becker",
            country = "Brezilya",
            countryFlag = "🇧🇷",
            league = "Premier League",
            team = "Liverpool",
            position = "KL",
            age = 32,
            jerseyNumber = 1,
            clubsPlayedFor = listOf("Internacional", "Roma", "Liverpool")
        ),
        FootballPlayer(
            id = "fernando_muslera",
            name = "Fernando Muslera",
            country = "Uruguay",
            countryFlag = "🇺🇾",
            league = "Süper Lig",
            team = "Galatasaray",
            position = "KL",
            age = 38,
            jerseyNumber = 1,
            clubsPlayedFor = listOf("Nacional", "Lazio", "Galatasaray")
        ),
        FootballPlayer(
            id = "dominik_livakovic",
            name = "Dominik Livakovic",
            country = "Hırvatistan",
            countryFlag = "🇭🇷",
            league = "Süper Lig",
            team = "Fenerbahçe",
            position = "KL",
            age = 30,
            jerseyNumber = 40,
            clubsPlayedFor = listOf("NK Zagreb", "Dinamo Zagreb", "Fenerbahçe")
        ),
        FootballPlayer(
            id = "mert_gunok",
            name = "Mert Günok",
            country = "Türkiye",
            countryFlag = "🇹🇷",
            league = "Süper Lig",
            team = "Beşiktaş",
            position = "KL",
            age = 36,
            jerseyNumber = 34,
            clubsPlayedFor = listOf("Fenerbahçe", "Bursaspor", "Başakşehir", "Beşiktaş")
        ),
        FootballPlayer(
            id = "ugurcan_cakir",
            name = "Uğurcan Çakır",
            country = "Türkiye",
            countryFlag = "🇹🇷",
            league = "Süper Lig",
            team = "Trabzonspor",
            position = "KL",
            age = 28,
            jerseyNumber = 1,
            clubsPlayedFor = listOf("1461 Trabzon", "Trabzonspor")
        ),
        FootballPlayer(
            id = "richarlison",
            name = "Richarlison",
            country = "Brezilya",
            countryFlag = "🇧🇷",
            league = "Premier League",
            team = "Tottenham Hotspur",
            position = "FOR",
            age = 27,
            jerseyNumber = 9,
            clubsPlayedFor = listOf("America Mineiro", "Fluminense", "Watford", "Everton", "Tottenham Hotspur")
        ),
        FootballPlayer(
            id = "dele_alli",
            name = "Dele Alli",
            country = "İngiltere",
            countryFlag = "🏴󠁧󠁢󠁥󠁮󠁧󠁿",
            league = "Premier League",
            team = "Everton",
            position = "OS",
            age = 28,
            jerseyNumber = 20,
            clubsPlayedFor = listOf("MK Dons", "Tottenham Hotspur", "Everton", "Beşiktaş")
        ),
        FootballPlayer(
            id = "lucas_digne",
            name = "Lucas Digne",
            country = "Fransa",
            countryFlag = "🇫🇷",
            league = "Premier League",
            team = "Aston Villa",
            position = "SLB",
            age = 31,
            jerseyNumber = 12,
            clubsPlayedFor = listOf("Lille", "Paris Saint-Germain", "Roma", "Barcelona", "Everton", "Aston Villa")
        ),
        FootballPlayer(
            id = "idrissa_gueye",
            name = "Idrissa Gueye",
            country = "Senegal",
            countryFlag = "🇸🇳",
            league = "Premier League",
            team = "Everton",
            position = "DOS",
            age = 35,
            jerseyNumber = 27,
            clubsPlayedFor = listOf("Lille", "Aston Villa", "Everton", "Paris Saint-Germain")
        ),
        FootballPlayer(
            id = "ashley_young",
            name = "Ashley Young",
            country = "İngiltere",
            countryFlag = "🏴󠁧󠁢󠁥󠁮󠁧󠁿",
            league = "Premier League",
            team = "Everton",
            position = "SLB",
            age = 39,
            jerseyNumber = 18,
            clubsPlayedFor = listOf("Watford", "Aston Villa", "Manchester United", "Inter", "Everton")
        ),
        FootballPlayer(
            id = "joshua_king",
            name = "Joshua King",
            country = "Norveç",
            countryFlag = "🇳🇴",
            league = "Ligue 1",
            team = "Toulouse",
            position = "FOR",
            age = 33,
            jerseyNumber = 11,
            clubsPlayedFor = listOf("Manchester United", "Blackburn", "Bournemouth", "Everton", "Watford", "Fenerbahçe")
        ),
        FootballPlayer(
            id = "asmir_begovic",
            name = "Asmir Begovic",
            country = "Bosna Hersek",
            countryFlag = "🇧🇦",
            league = "Premier League",
            team = "Everton",
            position = "KL",
            age = 37,
            jerseyNumber = 31,
            clubsPlayedFor = listOf("Portsmouth", "Stoke City", "Chelsea", "Bournemouth", "AC Milan", "Everton", "QPR")
        ),
        FootballPlayer(
            id = "arnaut_danjuma",
            name = "Arnaut Danjuma",
            country = "Hollanda",
            countryFlag = "🇳🇱",
            league = "La Liga",
            team = "Girona",
            position = "SLK",
            age = 28,
            jerseyNumber = 11,
            clubsPlayedFor = listOf("NEC", "Club Brugge", "Bournemouth", "Villarreal", "Tottenham Hotspur", "Everton")
        ),
        FootballPlayer(
            id = "callum_wilson",
            name = "Callum Wilson",
            country = "İngiltere",
            countryFlag = "🏴󠁧󠁢󠁥󠁮󠁧󠁿",
            league = "Premier League",
            team = "Newcastle United",
            position = "FOR",
            age = 33,
            jerseyNumber = 9,
            clubsPlayedFor = listOf("Coventry City", "Bournemouth", "Newcastle United")
        ),
        FootballPlayer(
            id = "ryan_fraser",
            name = "Ryan Fraser",
            country = "İskoçya",
            countryFlag = "🏴󠁧󠁢󠁳󠁣󠁴󠁿",
            league = "Premier League",
            team = "Southampton",
            position = "SLK",
            age = 31,
            jerseyNumber = 14,
            clubsPlayedFor = listOf("Aberdeen", "Bournemouth", "Ipswich Town", "Newcastle United", "Southampton")
        ),
        FootballPlayer(
            id = "kieran_trippier",
            name = "Kieran Trippier",
            country = "İngiltere",
            countryFlag = "🏴󠁧󠁢󠁥󠁮󠁧󠁿",
            league = "Premier League",
            team = "Newcastle United",
            position = "SĞB",
            age = 34,
            jerseyNumber = 2,
            clubsPlayedFor = listOf("Manchester City", "Burnley", "Tottenham Hotspur", "Atletico Madrid", "Newcastle United")
        ),
        FootballPlayer(
            id = "moussa_sissoko",
            name = "Moussa Sissoko",
            country = "Fransa",
            countryFlag = "🇫🇷",
            league = "Premier League",
            team = "Watford",
            position = "OS",
            age = 35,
            jerseyNumber = 17,
            clubsPlayedFor = listOf("Toulouse", "Newcastle United", "Tottenham Hotspur", "Watford", "Nantes")
        ),
        FootballPlayer(
            id = "shay_given",
            name = "Shay Given",
            country = "İrlanda",
            countryFlag = "🇮🇪",
            league = "Emekli",
            team = "Newcastle United",
            position = "KL",
            age = 48,
            jerseyNumber = 1,
            clubsPlayedFor = listOf("Blackburn", "Newcastle United", "Manchester City", "Aston Villa", "Stoke City")
        ),
        FootballPlayer(
            id = "matt_targett",
            name = "Matt Targett",
            country = "İngiltere",
            countryFlag = "🏴󠁧󠁢󠁥󠁮󠁧󠁿",
            league = "Premier League",
            team = "Newcastle United",
            position = "SLB",
            age = 29,
            jerseyNumber = 13,
            clubsPlayedFor = listOf("Southampton", "Fulham", "Aston Villa", "Newcastle United")
        ),
        FootballPlayer(
            id = "jake_livermore",
            name = "Jake Livermore",
            country = "İngiltere",
            countryFlag = "🏴󠁧󠁢󠁥󠁮󠁧󠁿",
            league = "Championship",
            team = "Watford",
            position = "DOS",
            age = 35,
            jerseyNumber = 8,
            clubsPlayedFor = listOf("Tottenham Hotspur", "Hull City", "West Bromwich Albion", "Watford")
        ),
        FootballPlayer(
            id = "tom_huddlestone",
            name = "Tom Huddlestone",
            country = "İngiltere",
            countryFlag = "🏴󠁧󠁢󠁥󠁮󠁧󠁿",
            league = "Emekli",
            team = "Tottenham Hotspur",
            position = "DOS",
            age = 38,
            jerseyNumber = 6,
            clubsPlayedFor = listOf("Derby County", "Tottenham Hotspur", "Wolves", "Hull City", "Manchester United")
        ),
        FootballPlayer(
            id = "ahmed_elmohamady",
            name = "Ahmed Elmohamady",
            country = "Mısır",
            countryFlag = "🇪🇬",
            league = "Emekli",
            team = "Hull City",
            position = "SĞB",
            age = 37,
            jerseyNumber = 27,
            clubsPlayedFor = listOf("Sunderland", "Hull City", "Aston Villa")
        ),
        FootballPlayer(
            id = "robert_snodgrass",
            name = "Robert Snodgrass",
            country = "İskoçya",
            countryFlag = "🏴󠁧󠁢󠁳󠁣󠁴󠁿",
            league = "Emekli",
            team = "West Ham United",
            position = "OS",
            age = 37,
            jerseyNumber = 11,
            clubsPlayedFor = listOf("Leeds United", "Norwich City", "Hull City", "West Ham United", "Aston Villa", "West Bromwich Albion")
        ),
        FootballPlayer(
            id = "james_chester",
            name = "James Chester",
            country = "Galler",
            countryFlag = "🏴󠁧󠁢󠁷󠁬󠁳󠁿",
            league = "League Two",
            team = "Salford City",
            position = "STP",
            age = 36,
            jerseyNumber = 5,
            clubsPlayedFor = listOf("Manchester United", "Hull City", "West Bromwich Albion", "Aston Villa", "Stoke City", "Derby County")
        ),
        FootballPlayer(
            id = "mateo_kovacic",
            name = "Mateo Kovacic",
            country = "Hırvatistan",
            countryFlag = "🇭🇷",
            league = "Premier League",
            team = "Manchester City",
            position = "OS",
            age = 30,
            jerseyNumber = 8,
            clubsPlayedFor = listOf("Dinamo Zagreb", "Inter", "Real Madrid", "Chelsea", "Manchester City")
        ),
        FootballPlayer(
            id = "antonio_rudiger",
            name = "Antonio Rüdiger",
            country = "Almanya",
            countryFlag = "🇩🇪",
            league = "La Liga",
            team = "Real Madrid",
            position = "STP",
            age = 32,
            jerseyNumber = 22,
            clubsPlayedFor = listOf("Stuttgart", "Roma", "Chelsea", "Real Madrid")
        ),
        FootballPlayer(
            id = "eden_hazard",
            name = "Eden Hazard",
            country = "Belçika",
            countryFlag = "🇧🇪",
            league = "Emekli",
            team = "Chelsea",
            position = "SLK",
            age = 34,
            jerseyNumber = 10,
            clubsPlayedFor = listOf("Lille", "Chelsea", "Real Madrid")
        ),
        FootballPlayer(
            id = "ronaldo_nazario",
            name = "Ronaldo Nazario",
            country = "Brezilya",
            countryFlag = "🇧🇷",
            league = "Efsane",
            team = "Real Madrid",
            position = "FOR",
            age = 48,
            jerseyNumber = 9,
            clubsPlayedFor = listOf("Cruzeiro", "PSV Eindhoven", "Barcelona", "Inter", "Real Madrid", "AC Milan", "Corinthians")
        ),
        FootballPlayer(
            id = "wesley_sneijder",
            name = "Wesley Sneijder",
            country = "Hollanda",
            countryFlag = "🇳🇱",
            league = "Efsane",
            team = "Galatasaray",
            position = "OOS",
            age = 40,
            jerseyNumber = 10,
            clubsPlayedFor = listOf("Ajax", "Real Madrid", "Inter", "Galatasaray", "Nice", "Al-Gharafa")
        ),
        FootballPlayer(
            id = "achraf_hakimi",
            name = "Achraf Hakimi",
            country = "Fas",
            countryFlag = "🇲🇦",
            league = "Ligue 1",
            team = "Paris Saint-Germain",
            position = "SĞB",
            age = 26,
            jerseyNumber = 2,
            clubsPlayedFor = listOf("Real Madrid", "Borussia Dortmund", "Inter", "Paris Saint-Germain")
        ),
        FootballPlayer(
            id = "ilkay_gundogan",
            name = "İlkay Gündoğan",
            country = "Almanya",
            countryFlag = "🇩🇪",
            league = "Premier League",
            team = "Manchester City",
            position = "OS",
            age = 34,
            jerseyNumber = 19,
            clubsPlayedFor = listOf("Nürnberg", "Borussia Dortmund", "Manchester City", "Barcelona")
        ),
        FootballPlayer(
            id = "sergio_aguero",
            name = "Sergio Agüero",
            country = "Arjantin",
            countryFlag = "🇦🇷",
            league = "Efsane",
            team = "Manchester City",
            position = "FOR",
            age = 36,
            jerseyNumber = 10,
            clubsPlayedFor = listOf("Independiente", "Atletico Madrid", "Manchester City", "Barcelona")
        ),
        FootballPlayer(
            id = "cesc_fabregas",
            name = "Cesc Fàbregas",
            country = "İspanya",
            countryFlag = "🇪🇸",
            league = "Efsane",
            team = "Chelsea",
            position = "OS",
            age = 37,
            jerseyNumber = 4,
            clubsPlayedFor = listOf("Arsenal", "Barcelona", "Chelsea", "Monaco", "Como")
        ),
        FootballPlayer(
            id = "zlatan_ibrahimovic",
            name = "Zlatan Ibrahimovic",
            country = "İsveç",
            countryFlag = "🇸🇪",
            league = "Efsane",
            team = "AC Milan",
            position = "FOR",
            age = 43,
            jerseyNumber = 11,
            clubsPlayedFor = listOf("Malmö", "Ajax", "Juventus", "Inter", "Barcelona", "AC Milan", "Paris Saint-Germain", "Manchester United", "LA Galaxy")
        ),
        FootballPlayer(
            id = "samuel_etoo",
            name = "Samuel Eto'o",
            country = "Kamerun",
            countryFlag = "🇨🇲",
            league = "Efsane",
            team = "Barcelona",
            position = "FOR",
            age = 44,
            jerseyNumber = 9,
            clubsPlayedFor = listOf("Real Madrid", "Mallorca", "Barcelona", "Inter", "Anzhi", "Chelsea", "Everton", "Sampdoria", "Antalyaspor", "Konyaspor")
        ),
        FootballPlayer(
            id = "thiago_silva",
            name = "Thiago Silva",
            country = "Brezilya",
            countryFlag = "🇧🇷",
            league = "Brasileirao",
            team = "Fluminense",
            position = "STP",
            age = 40,
            jerseyNumber = 3,
            clubsPlayedFor = listOf("Juventude", "Fluminense", "AC Milan", "Paris Saint-Germain", "Chelsea")
        ),
        FootballPlayer(
            id = "david_luiz",
            name = "David Luiz",
            country = "Brezilya",
            countryFlag = "🇧🇷",
            league = "Brasileirao",
            team = "Flamengo",
            position = "STP",
            age = 37,
            jerseyNumber = 23,
            clubsPlayedFor = listOf("Vitoria", "Benfica", "Chelsea", "Paris Saint-Germain", "Arsenal", "Flamengo")
        ),
        FootballPlayer(
            id = "michy_batshuayi",
            name = "Michy Batshuayi",
            country = "Belçika",
            countryFlag = "🇧🇪",
            league = "Süper Lig",
            team = "Galatasaray",
            position = "FOR",
            age = 31,
            jerseyNumber = 44,
            clubsPlayedFor = listOf("Standard Liege", "Marseille", "Chelsea", "Borussia Dortmund", "Valencia", "Crystal Palace", "Beşiktaş", "Fenerbahçe", "Galatasaray")
        ),
        FootballPlayer(
            id = "caner_erkin",
            name = "Caner Erkin",
            country = "Türkiye",
            countryFlag = "🇹🇷",
            league = "Süper Lig",
            team = "Eyüpspor",
            position = "SLB",
            age = 36,
            jerseyNumber = 88,
            clubsPlayedFor = listOf("Manisaspor", "CSKA Moskova", "Galatasaray", "Fenerbahçe", "Inter", "Beşiktaş", "Fatih Karagümrük", "Başakşehir", "Eyüpspor")
        ),
        FootballPlayer(
            id = "burak_yilmaz",
            name = "Burak Yılmaz",
            country = "Türkiye",
            countryFlag = "🇹🇷",
            league = "Efsane",
            team = "Galatasaray",
            position = "FOR",
            age = 39,
            jerseyNumber = 17,
            clubsPlayedFor = listOf("Antalyaspor", "Beşiktaş", "Manisaspor", "Fenerbahçe", "Eskişehirspor", "Trabzonspor", "Galatasaray", "Beijing Guoan", "Lille", "Fortuna Sittard")
        ),
        FootballPlayer(
            id = "emre_belozoglu",
            name = "Emre Belözoğlu",
            country = "Türkiye",
            countryFlag = "🇹🇷",
            league = "Efsane",
            team = "Fenerbahçe",
            position = "OS",
            age = 44,
            jerseyNumber = 5,
            clubsPlayedFor = listOf("Galatasaray", "Inter", "Newcastle United", "Fenerbahçe", "Atletico Madrid", "Başakşehir")
        ),
        FootballPlayer(
            id = "gedson_fernandes",
            name = "Gedson Fernandes",
            country = "Portekiz",
            countryFlag = "🇵🇹",
            league = "Süper Lig",
            team = "Beşiktaş",
            position = "OS",
            age = 26,
            jerseyNumber = 83,
            clubsPlayedFor = listOf("Benfica", "Tottenham Hotspur", "Galatasaray", "Rizespor", "Beşiktaş")
        ),
        FootballPlayer(
            id = "ryan_babel",
            name = "Ryan Babel",
            country = "Hollanda",
            countryFlag = "🇳🇱",
            league = "Emekli",
            team = "Galatasaray",
            position = "SLK",
            age = 38,
            jerseyNumber = 11,
            clubsPlayedFor = listOf("Ajax", "Liverpool", "Hoffenheim", "Kasımpaşa", "Al-Ain", "Deportivo La Coruna", "Beşiktaş", "Fulham", "Galatasaray", "Eyüpspor")
        ),
        FootballPlayer(
            id = "dusan_tadic",
            name = "Dusan Tadic",
            country = "Sırbistan",
            countryFlag = "🇷🇸",
            league = "Süper Lig",
            team = "Fenerbahçe",
            position = "SLK",
            age = 36,
            jerseyNumber = 10,
            clubsPlayedFor = listOf("Vojvodina", "Groningen", "Twente", "Southampton", "Ajax", "Fenerbahçe")
        ),
        FootballPlayer(
            id = "ciro_immobile",
            name = "Ciro Immobile",
            country = "İtalya",
            countryFlag = "🇮🇹",
            league = "Süper Lig",
            team = "Beşiktaş",
            position = "FOR",
            age = 35,
            jerseyNumber = 17,
            clubsPlayedFor = listOf("Juventus", "Genoa", "Torino", "Borussia Dortmund", "Sevilla", "Lazio", "Beşiktaş")
        ),
        FootballPlayer(
            id = "rafa_silva",
            name = "Rafa Silva",
            country = "Portekiz",
            countryFlag = "🇵🇹",
            league = "Süper Lig",
            team = "Beşiktaş",
            position = "OOS",
            age = 31,
            jerseyNumber = 27,
            clubsPlayedFor = listOf("Feirense", "Braga", "Benfica", "Beşiktaş")
        ),
        FootballPlayer(
            id = "dries_mertens",
            name = "Dries Mertens",
            country = "Belçika",
            countryFlag = "🇧🇪",
            league = "Süper Lig",
            team = "Galatasaray",
            position = "OOS",
            age = 37,
            jerseyNumber = 10,
            clubsPlayedFor = listOf("Gent", "Utrecht", "PSV Eindhoven", "Napoli", "Galatasaray")
        ),
        FootballPlayer(
            id = "lucas_torreira",
            name = "Lucas Torreira",
            country = "Uruguay",
            countryFlag = "🇺🇾",
            league = "Süper Lig",
            team = "Galatasaray",
            position = "DOS",
            age = 29,
            jerseyNumber = 34,
            clubsPlayedFor = listOf("Pescara", "Sampdoria", "Arsenal", "Atletico Madrid", "Fiorentina", "Galatasaray")
        ),
        FootballPlayer(
            id = "fred",
            name = "Fred",
            country = "Brezilya",
            countryFlag = "🇧🇷",
            league = "Süper Lig",
            team = "Fenerbahçe",
            position = "OS",
            age = 32,
            jerseyNumber = 13,
            clubsPlayedFor = listOf("Internacional", "Shakhtar Donetsk", "Manchester United", "Fenerbahçe")
        ),
        FootballPlayer(
            id = "sofyan_amrabat",
            name = "Sofyan Amrabat",
            country = "Fas",
            countryFlag = "🇲🇦",
            league = "Süper Lig",
            team = "Fenerbahçe",
            position = "DOS",
            age = 28,
            jerseyNumber = 34,
            clubsPlayedFor = listOf("Utrecht", "Feyenoord", "Club Brugge", "Verona", "Fiorentina", "Manchester United", "Fenerbahçe")
        ),
        FootballPlayer(
            id = "en_nesyri",
            name = "Youssef En-Nesyri",
            country = "Fas",
            countryFlag = "🇲🇦",
            league = "Süper Lig",
            team = "Fenerbahçe",
            position = "FOR",
            age = 27,
            jerseyNumber = 19,
            clubsPlayedFor = listOf("Malaga", "Leganes", "Sevilla", "Fenerbahçe")
        ),
        FootballPlayer(
            id = "allan_saint_maximin",
            name = "Allan Saint-Maximin",
            country = "Fransa",
            countryFlag = "🇫🇷",
            league = "Süper Lig",
            team = "Fenerbahçe",
            position = "SLK",
            age = 28,
            jerseyNumber = 97,
            clubsPlayedFor = listOf("Saint-Etienne", "Monaco", "Hannover", "Bastia", "Nice", "Newcastle United", "Al-Ahli", "Fenerbahçe")
        ),
        FootballPlayer(
            id = "davinson_sanchez",
            name = "Davinson Sánchez",
            country = "Kolombiya",
            countryFlag = "🇨🇴",
            league = "Süper Lig",
            team = "Galatasaray",
            position = "STP",
            age = 28,
            jerseyNumber = 6,
            clubsPlayedFor = listOf("Atletico Nacional", "Ajax", "Tottenham Hotspur", "Galatasaray")
        ),
        FootballPlayer(
            id = "al_musrati",
            name = "Al-Musrati",
            country = "Libya",
            countryFlag = "🇱🇾",
            league = "Süper Lig",
            team = "Beşiktaş",
            position = "DOS",
            age = 28,
            jerseyNumber = 28,
            clubsPlayedFor = listOf("Vitoria Guimaraes", "Rio Ave", "Braga", "Beşiktaş")
        ),
        FootballPlayer(
            id = "didier_drogba",
            name = "Didier Drogba",
            country = "Fildişi Sahili",
            countryFlag = "🇨🇮",
            league = "Efsane",
            team = "Chelsea",
            position = "FOR",
            age = 47,
            jerseyNumber = 11,
            clubsPlayedFor = listOf("Le Mans", "Guingamp", "Marseille", "Chelsea", "Shanghai Shenhua", "Galatasaray", "Montreal Impact", "Phoenix Rising")
        ),
        FootballPlayer(
            id = "robin_van_persie",
            name = "Robin van Persie",
            country = "Hollanda",
            countryFlag = "🇳🇱",
            league = "Efsane",
            team = "Arsenal",
            position = "FOR",
            age = 41,
            jerseyNumber = 11,
            clubsPlayedFor = listOf("Feyenoord", "Arsenal", "Manchester United", "Fenerbahçe")
        ),
        FootballPlayer(
            id = "alex_de_souza",
            name = "Alex de Souza",
            country = "Brezilya",
            countryFlag = "🇧🇷",
            league = "Efsane",
            team = "Fenerbahçe",
            position = "OOS",
            age = 47,
            jerseyNumber = 10,
            clubsPlayedFor = listOf("Coritiba", "Palmeiras", "Flamengo", "Cruzeiro", "Parma", "Fenerbahçe")
        ),
        FootballPlayer(
            id = "gheorghe_hagi",
            name = "Gheorghe Hagi",
            country = "Romanya",
            countryFlag = "🇷🇴",
            league = "Efsane",
            team = "Galatasaray",
            position = "OOS",
            age = 60,
            jerseyNumber = 10,
            clubsPlayedFor = listOf("Farul Constanta", "Sportul Studentesc", "Steaua Bükreş", "Real Madrid", "Brescia", "Barcelona", "Galatasaray")
        ),
        FootballPlayer(
            id = "thierry_henry",
            name = "Thierry Henry",
            country = "Fransa",
            countryFlag = "🇫🇷",
            league = "Efsane",
            team = "Arsenal",
            position = "FOR",
            age = 47,
            jerseyNumber = 14,
            clubsPlayedFor = listOf("Monaco", "Juventus", "Arsenal", "Barcelona", "New York Red Bulls")
        ),
        FootballPlayer(
            id = "ronaldinho",
            name = "Ronaldinho",
            country = "Brezilya",
            countryFlag = "🇧🇷",
            league = "Efsane",
            team = "Barcelona",
            position = "SLK",
            age = 45,
            jerseyNumber = 10,
            clubsPlayedFor = listOf("Gremio", "Paris Saint-Germain", "Barcelona", "AC Milan", "Flamengo", "Atletico Mineiro", "Queretaro", "Fluminense")
        ),
        FootballPlayer(
            id = "zinedine_zidane",
            name = "Zinedine Zidane",
            country = "Fransa",
            countryFlag = "🇫🇷",
            league = "Efsane",
            team = "Real Madrid",
            position = "OOS",
            age = 52,
            jerseyNumber = 5,
            clubsPlayedFor = listOf("Cannes", "Bordeaux", "Juventus", "Real Madrid")
        ),
        FootballPlayer(
            id = "toni_kroos",
            name = "Toni Kroos",
            country = "Almanya",
            countryFlag = "🇩🇪",
            league = "Efsane",
            team = "Real Madrid",
            position = "OS",
            age = 35,
            jerseyNumber = 8,
            clubsPlayedFor = listOf("Hansa Rostock", "Bayern Münih", "Bayer Leverkusen", "Real Madrid")
        ),
        FootballPlayer(
            id = "mesut_ozil",
            name = "Mesut Özil",
            country = "Almanya",
            countryFlag = "🇩🇪",
            league = "Efsane",
            team = "Arsenal",
            position = "OOS",
            age = 36,
            jerseyNumber = 10,
            clubsPlayedFor = listOf("Schalke 04", "Werder Bremen", "Real Madrid", "Arsenal", "Fenerbahçe", "Başakşehir")
        ),
        FootballPlayer(
            id = "luis_suarez",
            name = "Luis Suárez",
            country = "Uruguay",
            countryFlag = "🇺🇾",
            league = "MLS",
            team = "Inter Miami",
            position = "FOR",
            age = 38,
            jerseyNumber = 9,
            clubsPlayedFor = listOf("Nacional", "Groningen", "Ajax", "Liverpool", "Barcelona", "Atletico Madrid", "Gremio", "Inter Miami")
        ),
        FootballPlayer(
            id = "kai_havertz",
            name = "Kai Havertz",
            country = "Almanya",
            countryFlag = "🇩🇪",
            league = "Premier League",
            team = "Arsenal",
            position = "FOR",
            age = 25,
            jerseyNumber = 29,
            clubsPlayedFor = listOf("Bayer Leverkusen", "Chelsea", "Arsenal")
        ),
        FootballPlayer(
            id = "raheem_sterling",
            name = "Raheem Sterling",
            country = "İngiltere",
            countryFlag = "🏴󠁧󠁢󠁥󠁮󠁧󠁿",
            league = "Premier League",
            team = "Arsenal",
            position = "SLK",
            age = 30,
            jerseyNumber = 30,
            clubsPlayedFor = listOf("QPR", "Liverpool", "Manchester City", "Chelsea", "Arsenal")
        ),
        FootballPlayer(
            id = "frank_lampard",
            name = "Frank Lampard",
            country = "İngiltere",
            countryFlag = "🏴󠁧󠁢󠁥󠁮󠁧󠁿",
            league = "Efsane",
            team = "Chelsea",
            position = "OS",
            age = 46,
            jerseyNumber = 8,
            clubsPlayedFor = listOf("West Ham United", "Swansea", "Chelsea", "Manchester City", "New York City")
        ),
        FootballPlayer(
            id = "fernando_torres",
            name = "Fernando Torres",
            country = "İspanya",
            countryFlag = "🇪🇸",
            league = "Efsane",
            team = "Liverpool",
            position = "FOR",
            age = 41,
            jerseyNumber = 9,
            clubsPlayedFor = listOf("Atletico Madrid", "Liverpool", "Chelsea", "AC Milan", "Sagan Tosu")
        ),
        FootballPlayer(
            id = "paul_pogba",
            name = "Paul Pogba",
            country = "Fransa",
            countryFlag = "🇫🇷",
            league = "Serie A",
            team = "Juventus",
            position = "OS",
            age = 32,
            jerseyNumber = 10,
            clubsPlayedFor = listOf("Le Havre", "Manchester United", "Juventus")
        ),
        FootballPlayer(
            id = "pepe",
            name = "Pepe",
            country = "Portekiz",
            countryFlag = "🇵🇹",
            league = "Efsane",
            team = "Real Madrid",
            position = "STP",
            age = 42,
            jerseyNumber = 3,
            clubsPlayedFor = listOf("Maritimo", "Porto", "Real Madrid", "Beşiktaş")
        ),
        FootballPlayer(
            id = "ricardo_quaresma",
            name = "Ricardo Quaresma",
            country = "Portekiz",
            countryFlag = "🇵🇹",
            league = "Efsane",
            team = "Beşiktaş",
            position = "SĞK",
            age = 41,
            jerseyNumber = 7,
            clubsPlayedFor = listOf("Sporting CP", "Barcelona", "Porto", "Inter", "Chelsea", "Beşiktaş", "Al-Ahli", "Kasımpaşa", "Vitoria Guimaraes")
        ),
        FootballPlayer(
            id = "felipe_melo",
            name = "Felipe Melo",
            country = "Brezilya",
            countryFlag = "🇧🇷",
            league = "Brasileirao",
            team = "Fluminense",
            position = "DOS",
            age = 41,
            jerseyNumber = 30,
            clubsPlayedFor = listOf("Flamengo", "Cruzeiro", "Gremio", "Mallorca", "Racing Santander", "Almeria", "Fiorentina", "Juventus", "Galatasaray", "Inter", "Palmeiras", "Fluminense")
        ),
        FootballPlayer(
            id = "lukas_podolski",
            name = "Lukas Podolski",
            country = "Almanya",
            countryFlag = "🇩🇪",
            league = "Ekstraklasa",
            team = "Gornik Zabrze",
            position = "FOR",
            age = 39,
            jerseyNumber = 10,
            clubsPlayedFor = listOf("Köln", "Bayern Münih", "Arsenal", "Inter", "Galatasaray", "Vissel Kobe", "Antalyaspor", "Gornik Zabrze")
        )
    )

    // XOX Boards - Expanding to 10+ dynamic rotating configurations!
    val boardConfigs: List<XoxBoardConfig> = listOf(
        XoxBoardConfig(
            id = "b1",
            title = "Premier League Klasiği",
            rowClubs = listOf("Everton", "Hull City", "Newcastle United"),
            colClubs = listOf("Bournemouth", "Tottenham Hotspur", "Aston Villa")
        ),
        XoxBoardConfig(
            id = "b2",
            title = "Avrupa Devler Arenası",
            rowClubs = listOf("Real Madrid", "Barcelona", "Paris Saint-Germain"),
            colClubs = listOf("Manchester City", "Chelsea", "Inter")
        ),
        XoxBoardConfig(
            id = "b3",
            title = "Türkiye Süper Lig Üç Büyükler",
            rowClubs = listOf("Galatasaray", "Fenerbahçe", "Beşiktaş"),
            colClubs = listOf("Inter", "Chelsea", "Trabzonspor")
        ),
        XoxBoardConfig(
            id = "b4",
            title = "İtalya & İspanya Çarpışması",
            rowClubs = listOf("Juventus", "AC Milan", "Inter"),
            colClubs = listOf("Barcelona", "Real Madrid", "Chelsea")
        ),
        XoxBoardConfig(
            id = "b5",
            title = "Premier Lig Big Six",
            rowClubs = listOf("Arsenal", "Manchester City", "Liverpool"),
            colClubs = listOf("Chelsea", "Barcelona", "Inter")
        ),
        XoxBoardConfig(
            id = "b6",
            title = "Almanya & İspanya Karşılaşması",
            rowClubs = listOf("Bayern Münih", "Bayer Leverkusen", "Borussia Dortmund"),
            colClubs = listOf("Real Madrid", "Barcelona", "Arsenal")
        ),
        XoxBoardConfig(
            id = "b7",
            title = "Süper Lig & Avrupa Köprüsü",
            rowClubs = listOf("Galatasaray", "Fenerbahçe", "Beşiktaş"),
            colClubs = listOf("Arsenal", "Real Madrid", "Inter")
        ),
        XoxBoardConfig(
            id = "b8",
            title = "Şampiyonlar Ligi Yıldızları",
            rowClubs = listOf("Real Madrid", "Manchester City", "Liverpool"),
            colClubs = listOf("Chelsea", "Inter", "Barcelona")
        )
    )

    fun getRandomBoardConfig(excludeId: String? = null): XoxBoardConfig {
        val pool = if (excludeId != null) boardConfigs.filter { it.id != excludeId } else boardConfigs
        return pool.random()
    }

    fun checkPlayerMatchesBoth(player: FootballPlayer, clubA: String, clubB: String): Boolean {
        val playedClubs = player.clubsPlayedFor.map { normalize(it) }
        val curTeam = normalize(player.team)
        val allClubs = (playedClubs + curTeam).toSet()

        val normA = normalize(clubA)
        val normB = normalize(clubB)

        val hasA = allClubs.any { it.contains(normA) || normA.contains(it) }
        val hasB = allClubs.any { it.contains(normB) || normB.contains(it) }

        return hasA && hasB
    }

    private fun normalize(str: String): String {
        return str.lowercase()
            .replace("ı", "i")
            .replace("ğ", "g")
            .replace("ü", "u")
            .replace("ş", "s")
            .replace("ö", "o")
            .replace("ç", "c")
            .trim()
    }

    fun searchPlayers(query: String): List<FootballPlayer> {
        if (query.isBlank()) return emptyList()
        val q = normalize(query)
        return players.filter {
            normalize(it.name).contains(q) ||
            normalize(it.team).contains(q) ||
            normalize(it.country).contains(q)
        }.take(8)
    }

    fun findMatchingPlayersForIntersection(clubA: String, clubB: String): List<FootballPlayer> {
        return players.filter { checkPlayerMatchesBoth(it, clubA, clubB) }
    }
}
