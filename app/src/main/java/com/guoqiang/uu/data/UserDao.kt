package com.guoqiang.uu.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * author: zgq
 * date: 2023/3/25 15:44
 * destcription:
 */
@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE username = :username LIMIT 1")
    fun findByName(username: String): User

    @Insert
    fun insertAll(vararg users: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(users: List<User>)

    @Delete
    fun delete(user: User)

    @Transaction
    @Query("SELECT * FROM user")
    fun getUserContainsMessageSessions(): List<UserContainsMessageSession>

    @Query("SELECT * FROM user ORDER BY u_time DESC LIMIT :pageSize OFFSET :offset")
    suspend fun getUsers(offset: Int, pageSize: Int): List<User>

}