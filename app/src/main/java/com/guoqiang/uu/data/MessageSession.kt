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
@Entity("message_session")
data class MessageSession(
    @PrimaryKey @ColumnInfo(name = "m_sid") val mSid: String,
    @ColumnInfo("c_time") val cTime: Date?,
    @ColumnInfo("u_time") val uTime: Date?,
    @ColumnInfo("type") val type: Int,//1.ai翻译官，2.ai解惑，3.ai玄机，4.ai作家，5.ai厨师，6.ai诗人，7.ai文生图
)
