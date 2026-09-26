package com.example.data.local

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    fun getUserProfile(): Flow<UserProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateProfile(profile: UserProfileEntity)
}

@Dao
interface MatchHistoryDao {
    @Query("SELECT * FROM match_history ORDER BY timestamp DESC LIMIT 30")
    fun getAllMatches(): Flow<List<MatchHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatch(match: MatchHistoryEntity)
}

@Dao
interface AchievementDao {
    @Query("SELECT * FROM achievements")
    fun getAllAchievements(): Flow<List<AchievementEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateAchievement(achievement: AchievementEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(achievements: List<AchievementEntity>)
}

@Dao
interface FriendDao {
    @Query("SELECT * FROM friends WHERE friendshipStatus = 'ACCEPTED' ORDER BY isOnline DESC, addedAt DESC")
    fun getAcceptedFriends(): Flow<List<FriendEntity>>

    @Query("SELECT * FROM friends WHERE friendshipStatus != 'ACCEPTED' ORDER BY addedAt DESC")
    fun getPendingRequests(): Flow<List<FriendEntity>>

    @Query("SELECT * FROM friends WHERE LOWER(name) = LOWER(:name) OR LOWER(name) = LOWER('@' || :name) OR LOWER(REPLACE(name, '@', '')) = LOWER(REPLACE(:name, '@', '')) LIMIT 1")
    suspend fun getFriendByName(name: String): FriendEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFriend(friend: FriendEntity)

    @Query("UPDATE friends SET friendshipStatus = 'ACCEPTED' WHERE id = :friendId")
    suspend fun acceptFriendRequest(friendId: String)

    @Query("DELETE FROM friends WHERE id = :friendId")
    suspend fun deleteFriend(friendId: String)
}

@Dao
interface AccountDao {
    @Query("SELECT * FROM user_accounts ORDER BY rankPoints DESC")
    fun getAllAccounts(): Flow<List<UserAccountEntity>>

    @Query("SELECT * FROM user_accounts ORDER BY rankPoints DESC")
    suspend fun getAllAccountsList(): List<UserAccountEntity>

    @Query("SELECT * FROM user_accounts WHERE LOWER(username) = LOWER(:username) OR LOWER(username) = LOWER('@' || :username) OR LOWER(REPLACE(username, '@', '')) = LOWER(REPLACE(:username, '@', '')) LIMIT 1")
    suspend fun findByUsername(username: String): UserAccountEntity?

    @Query("SELECT * FROM user_accounts WHERE LOWER(email) = LOWER(:email) LIMIT 1")
    suspend fun findByEmail(email: String): UserAccountEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(account: UserAccountEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(accounts: List<UserAccountEntity>)
}

@Database(
    entities = [
        UserProfileEntity::class,
        MatchHistoryEntity::class,
        AchievementEntity::class,
        FriendEntity::class,
        UserAccountEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun matchHistoryDao(): MatchHistoryDao
    abstract fun achievementDao(): AchievementDao
    abstract fun friendDao(): FriendDao
    abstract fun accountDao(): AccountDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "futbol_arena_db"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
