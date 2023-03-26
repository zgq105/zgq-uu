package com.guoqiang.uu.data

import androidx.room.*

/**
 * author: zgq
 * date: 2023/3/25 15:44
 * destcription:
 */
@Dao
interface MessageDao {
    @Query("SELECT * FROM message")
    fun getAll(): List<Message>

    @Query("SELECT * FROM message WHERE mid IN (:midList)")
    fun loadAllByIds(midList: List<String>): List<Message>

    @Query("SELECT * FROM message WHERE content = :content LIMIT 1")
    fun findByContent(content: String): Message

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entities: List<Message>)

    @Delete
    fun delete(entity: Message)

    @Query("SELECT * FROM message WHERE uid in (:userIds) ORDER BY u_time DESC LIMIT :pageSize OFFSET :offset")
    suspend fun getMessages(offset: Int, pageSize: Int, userIds: List<String>): List<Message>

}