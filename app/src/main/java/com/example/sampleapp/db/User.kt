package com.example.sampleapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey val uid: Int = 0,
    @ColumnInfo(name = "per_day") val perDay: Int?,
    @ColumnInfo(name = "in_pack") val inPack: Int?,
    @ColumnInfo(name = "years") val years: Float?,
    @ColumnInfo(name = "price") val price: Float?,
    @ColumnInfo(name = "currency") val currency: String?
)