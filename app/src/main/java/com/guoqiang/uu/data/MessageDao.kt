package com.guoqiang.uu.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

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

    @Insert
    fun insertAll(vararg entities: Message)

    @Delete
    fun delete(entity: Message)

}