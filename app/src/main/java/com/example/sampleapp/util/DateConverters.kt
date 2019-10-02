package com.example.sampleapp.util

import androidx.room.TypeConverter
import java.sql.Date


object DateConverters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date {
        return value?.let { Date(it) } ?: Date(0)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long {
        return date?.time ?: 0
    }
}