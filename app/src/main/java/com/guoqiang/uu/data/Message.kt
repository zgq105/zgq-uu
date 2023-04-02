package com.guoqiang.uu.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * author: zgq
 * date: 2023/3/25 15:24
 * destcription:
 */
@Entity("message")
data class Message(
    @PrimaryKey @ColumnInfo(name = "mid") val mid: String,
    @ColumnInfo("content") val content: String,
    @ColumnInfo("from") val from: Int,//1是机器人，2是用户消息
    @ColumnInfo("c_time") val cTime: Date?,
    @ColumnInfo("u_time") val uTime: Date?,
    @ColumnInfo(name = "uid") val uid: String, //所属用户
    @ColumnInfo(name = "session_id") val sessionId: String, //所属会话
)

enum class MessageSource(val value: Int) {
    ROBOT(1),
    VISITOR(2)
}
