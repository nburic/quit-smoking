package com.example.sampleapp.data.db.store

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sampleapp.util.empty

@Entity(tableName = "StoreItem")
data class StoreItemEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String = String.empty,
    var price: Float = 0f,
    var bought: Boolean = false,
    var userId: Int = 0
)