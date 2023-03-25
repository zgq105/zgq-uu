package com.guoqiang.uu.data

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

/**
 * author: zgq
 * date: 2023/3/25 16:44
 * destcription:
 */
data class UserContainsMessageSession(
    @Embedded
    val user: User,
    @Relation(
        parentColumn = "uid",
        entityColumn = "m_sid",
        associateBy = Junction(UserMessageSessionRef::class)
    )
    val messageSessions: List<MessageSession>
)