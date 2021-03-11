package com.example.sampleapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM User WHERE uid = 0")
    suspend fun get(): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userEntity: UserEntity)

    @Query("DELETE FROM User WHERE uid = 0")
    suspend fun delete()

    @Query("UPDATE User SET goal = :timestamp WHERE uid = 0")
    suspend fun updateGoal(timestamp: Long)
}