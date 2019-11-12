package com.example.sampleapp.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM user_table WHERE uid = 0")
    fun get(): LiveData<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("DELETE FROM user_table WHERE uid = 0")
    suspend fun delete()

    @Query("UPDATE user_table SET goal = :timestamp, goalIndex = :index WHERE uid = 0")
    suspend fun updateGoal(timestamp: Long, index: Int)
}