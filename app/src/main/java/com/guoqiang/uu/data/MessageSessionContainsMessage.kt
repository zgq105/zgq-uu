package com.guoqiang.uu.data

import androidx.room.Embedded
import androidx.room.Relation

/**
 * author: zgq
 * date: 2023/3/25 16:44
 * destcription:
 */
data class MessageSessionContainsMessage (
    @Embedded
    val messageSession: MessageSession,
    @Relation(
        parentColumn = "m_sid",
        entityColumn = "session_id"
    )
    val messages: List<Message>
)