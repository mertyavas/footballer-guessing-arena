package com.example

import com.example.data.FootballDatabase
import com.example.data.model.LeagueTier
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun testPlayerDatabaseContainsPlayers() {
        assertTrue(FootballDatabase.players.isNotEmpty())
        val vinicius = FootballDatabase.players.find { it.id == "vinicius_jr" }
        assertNotNull(vinicius)
        assertEquals("Vinicius Junior", vinicius?.name)
        assertEquals("Real Madrid", vinicius?.team)
    }

    @Test
    fun testPlayerSearch() {
        val results = FootballDatabase.searchPlayers("Vinicius")
        assertTrue(results.any { it.name.contains("Vinicius", ignoreCase = true) })
    }

    @Test
    fun testXoxIntersections() {
        val richarlison = FootballDatabase.players.find { it.id == "richarlison" }
        assertNotNull(richarlison)
        val matchesBoth = FootballDatabase.checkPlayerMatchesBoth(
            richarlison!!,
            "Everton",
            "Tottenham Hotspur"
        )
        assertTrue(matchesBoth)
    }

    @Test
    fun testLeagueTierFromRp() {
        assertEquals(LeagueTier.AMATOR, LeagueTier.fromRp(200))
        assertEquals(LeagueTier.TFF_3_LIG, LeagueTier.fromRp(650))
        assertEquals(LeagueTier.TFF_2_LIG, LeagueTier.fromRp(1400))
        assertEquals(LeagueTier.TRENDYOL_1_LIG, LeagueTier.fromRp(2500))
        assertEquals(LeagueTier.SUPER_LIG, LeagueTier.fromRp(4000))
        assertEquals(LeagueTier.CHAMPIONS_LEAGUE, LeagueTier.fromRp(6500))
    }

    @Test
    fun testDynamicBoardsAvailable() {
        assertTrue(FootballDatabase.boardConfigs.size >= 5)
        val randomBoard = FootballDatabase.getRandomBoardConfig()
        assertNotNull(randomBoard)
        assertEquals(3, randomBoard.rowClubs.size)
        assertEquals(3, randomBoard.colClubs.size)
    }

    @Test
    fun testClueComparisonLogic() {
        val arda = FootballDatabase.players.find { it.id == "arda_guler" }!!
        val hakan = FootballDatabase.players.find { it.id == "hakan_calhanoglu" }!!

        // If target is Hakan (31, Inter, Serie A, OS) and guess is Arda (20, Real Madrid, La Liga, OOS):
        val isCountryMatch = arda.country.equals(hakan.country, ignoreCase = true)
        val isTeamMatch = arda.team.equals(hakan.team, ignoreCase = true)
        val ageHint = if (hakan.age > arda.age) "↑" else "↓"

        assertTrue(isCountryMatch) // both Turkey
        assertFalse(isTeamMatch)   // Real Madrid != Inter
        assertEquals("↑", ageHint) // Hakan is older (31 > 20)
    }

    @Test
    fun testAuthAndFriendValidationModels() {
        val successAuth = com.example.data.model.AuthResult.Success("Kayıt başarılı")
        val errorAuth = com.example.data.model.AuthResult.Error("Hatalı şifre!")
        assertTrue(successAuth.message.contains("başarılı"))
        assertEquals("Hatalı şifre!", errorAuth.message)

        val notFoundFriend = com.example.data.model.FriendRequestResult.Error("Oyuncu bulunamadı! Yalnızca kayıtlı kullanıcılar eklenebilir.")
        assertTrue(notFoundFriend.message.contains("Oyuncu bulunamadı"))
    }
}
