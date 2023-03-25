package com.guoqiang.uu.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.util.*

/**
 * author: zgq
 * date: 2023/3/25 17:06
 * destcription:
 */
@Entity(primaryKeys = ["uid", "m_sid"])
data class UserMessageSessionRef(
    @ColumnInfo(name = "uid") val uid: String,
    @ColumnInfo(name = "m_sid", index = true) val mSid: String,
    @ColumnInfo("c_time") val cTime: Date?,
    @ColumnInfo("u_time") val uTime: Date?
)