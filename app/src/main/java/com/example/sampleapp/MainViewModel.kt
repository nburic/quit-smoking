package com.example.sampleapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.data.AppRepo
import com.example.sampleapp.data.db.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: AppRepo) : ViewModel() {

    val user: LiveData<UserEntity> = repository.observeUser()
    private var startEpoch: Long? = null

    fun setStartEpoch(epoch: Long?) {
        startEpoch = epoch
    }

    fun getStartEpoch(): Long {
        return startEpoch ?: user.value?.start ?: 0L
    }

    fun setUserData(user: UserEntity) {
        viewModelScope.launch {
            repository.setUser(user)
        }
    }


    fun calculateNotSmoked(): Int {
//        userEntity.value?.let {
//            val days = calculateDifferenceToDays(it.start) ?: return 0
//
//            return (days.toFloat() * it.cigPerDay.toFloat()).toInt()
//        }
        return 0
    }

    fun getSmokeFreeDays(): Int {
//        val startDate = userEntity.value?.start ?: return 0
//        return calculateDifferenceToDays(startDate) ?: 0
        return 0
    }

    fun getRegainedDays(): Int {
        val notSmoked = calculateNotSmoked()
        return calculateLifeRegained(notSmoked)
    }

    private fun calculateLifeRegained(notSmoked: Int): Int {
        return notSmoked * 11 / 60 / 24
    }

    fun getGoalPercentage(user: UserEntity): Float {
        val startDate: Long = user.start / 1000
        val goalDate: Long = user.goal / 1000

        val limit = goalDate - startDate
        val current = System.currentTimeMillis() / 1000 - startDate
        val percent = (current.toDouble() / limit.toDouble())
        val progress = (percent * 100).toFloat()

        return when (progress >= 100) {
            true -> 100f
            false -> progress
        }
    }
}