package com.guoqiang.uu.data

import androidx.room.*

/**
 * author: zgq
 * date: 2023/3/26 20:26
 * destcription:
 */

@Dao
interface UserMessageSessionRefDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userMessageSessionRef: UserMessageSessionRef)

    @Update
    fun update(userMessageSessionRef: UserMessageSessionRef)

    @Query("SELECT * FROM user_message_session_ref WHERE uid = :uid AND m_sid = :mSid")
    fun getUserMessageSessionRef(uid: String, mSid: String): UserMessageSessionRef?

    @Query("SELECT * FROM user_message_session_ref")
    fun getAllUserMessageSessionRefs(): List<UserMessageSessionRef>

    @Delete
    fun delete(userMessageSessionRef: UserMessageSessionRef)
}
