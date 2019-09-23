package com.example.sampleapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class UserEntity(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "smoked_per_day") val smokedPerDay: Int?,
    @ColumnInfo(name = "in_pack") val inPack: Int?,
    @ColumnInfo(name = "years") val years: Float?,
    @ColumnInfo(name = "price") val price: Float?,
    @ColumnInfo(name = "currency") val currency: String?
)