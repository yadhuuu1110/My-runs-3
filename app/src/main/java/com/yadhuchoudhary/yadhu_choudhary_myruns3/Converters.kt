package com.yadhuchoudhary.yadhu_choudhary_myruns3

import androidx.core.animation.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Calendar? {
        return value?.let {
            Calendar.getInstance().apply {
                timeInMillis = it
            }
        }
    }

    @TypeConverter
    fun dateToTimestamp(calendar: Calendar?): Long? {
        return calendar?.timeInMillis
    }

    @TypeConverter
    fun fromByteArray(value: ByteArray?): ByteArray? {
        return value
    }

    @TypeConverter
    fun byteArrayToByteArray(byteArray: ByteArray?): ByteArray? {
        return byteArray
    }
}