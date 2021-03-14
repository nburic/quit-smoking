package com.example.sampleapp.data

import androidx.lifecycle.LiveData
import com.example.sampleapp.data.db.UserDao
import com.example.sampleapp.data.db.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class AppRepo @Inject constructor(private val userDao: UserDao) {

    suspend fun insert(userEntity: UserEntity) {
        userDao.insert(userEntity)
    }

    suspend fun updateGoal(timestamp: Long) {
        userDao.updateGoal(timestamp)
    }

    suspend fun getUser(): UserEntity = withContext(Dispatchers.IO) {
        userDao.get()
    }

    fun observeUser(): LiveData<UserEntity> {
        return userDao.observeUser()
    }
}