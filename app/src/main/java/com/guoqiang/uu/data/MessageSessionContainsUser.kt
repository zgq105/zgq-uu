package com.guoqiang.uu.data

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

/**
 * author: zgq
 * date: 2023/3/25 16:44
 * destcription:
 */
data class MessageSessionContainsUser(
    @Embedded
    val messageSession: MessageSession,
    @Relation(
        parentColumn = "m_sid",
        entityColumn = "uid",
        associateBy = Junction(UserMessageSessionRef::class)
    )
    val users: List<User>
)