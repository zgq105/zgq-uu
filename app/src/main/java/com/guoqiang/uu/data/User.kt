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
@Entity("user")
data class User(
    @PrimaryKey @ColumnInfo(name = "uid") val uid: String,
    @ColumnInfo("username") val username: String?,
    @ColumnInfo("password") val password: String?,
    @ColumnInfo("token") val token: String?,
    @ColumnInfo("phone") val phone: String?,
    @ColumnInfo("age") val age: Int?,
    @ColumnInfo("sex") val sex: Int?,//1男，2女
    @ColumnInfo("avatar") val avatar: String?,
    @ColumnInfo("c_time") val cTime: Date?,
    @ColumnInfo("u_time") val uTime: Date?
)
