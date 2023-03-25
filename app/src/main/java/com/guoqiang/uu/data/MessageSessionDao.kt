package com.guoqiang.uu.data

import androidx.room.*

/**
 * author: zgq
 * date: 2023/3/25 16:57
 * destcription:
 */
@Dao
interface MessageSessionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessageSession(messageSession: MessageSession)

    @Query("SELECT * FROM message_session WHERE m_sid = :mSid")
    fun getMessageSession(mSid: String): MessageSession?

    @Query("SELECT * FROM message_session")
    fun getAllMessageSessions(): List<MessageSession>

    @Update
    fun updateMessageSession(messageSession: MessageSession)

    @Delete
    fun deleteMessageSession(messageSession: MessageSession)

    @Transaction
    @Query("SELECT * FROM message_session")
    fun getMessageSessionContainsMessages(): List<MessageSessionContainsMessage>

    @Transaction
    @Query("SELECT * FROM message_session")
    fun getMessageSessionContainsUsers(): List<MessageSessionContainsUser>
}
