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
    @ColumnInfo("type") val type: Int,//1.ai管家，2.ai翻译官，3.ai解惑，4.ai玄机，5.ai作家，6.ai厨师，7.ai诗人，8.ai文生图
)
