package com.example.sampleapp.repo

import androidx.lifecycle.LiveData
import com.example.sampleapp.db.UserEntity
import com.example.sampleapp.db.UserDao

/**
 * Declares the DAO as a private property in the constructor. Pass in the DAO instead of the whole database, because you only need access to the DAO
 */
class AppRepo(private val userDao: UserDao) {

    /**
     * Room executes all queries on a separate thread. Observed LiveData will notify the observer when the data has changed.
     */
    val userEntity: LiveData<UserEntity> = userDao.get()

    /**
     * The suspend modifier tells the compiler that this must be called from a coroutine or another suspend function.
     */
    suspend fun insert(userEntity: UserEntity) {
        userDao.insert(userEntity)
    }

    suspend fun updateGoal(timestamp: Long) {
        userDao.updateGoal(timestamp)
    }
}