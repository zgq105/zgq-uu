package com.guoqiang.uu.data

import androidx.room.TypeConverter
import java.util.*

/**
 * author: zgq
 * date: 2023/3/25 20:00
 * destcription:
 */
class Converters {
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }
}
