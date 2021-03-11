package com.example.sampleapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class UserEntity(
    @PrimaryKey
    var uid: Long,
    var start: Long = 0L,
    var cigPerDay: Int = 0,
    var inPack: Int = 0,
    var years: Int = 0,
    var price: Float = 0f,
    var goal: Long = 0L
)