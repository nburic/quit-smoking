package com.example.sampleapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sampleapp.data.db.store.StoreItemDao
import com.example.sampleapp.data.db.store.StoreItemEntity
import com.example.sampleapp.data.db.user.UserDao
import com.example.sampleapp.data.db.user.UserEntity


@Database(entities = [
    UserEntity::class,
    StoreItemEntity::class
], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun storeItemDao(): StoreItemDao
}