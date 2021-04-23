package com.example.sampleapp.data.db.user

import androidx.room.Embedded
import androidx.room.Relation
import com.example.sampleapp.data.db.store.StoreItemEntity

data class UserWithStoreItems(
        @Embedded val user: UserEntity,
        @Relation(
                parentColumn = "uid",
                entityColumn = "userId"
        )
        val storeItems: List<StoreItemEntity>
)
