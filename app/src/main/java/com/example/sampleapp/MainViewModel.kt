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
        return startEpoch ?: 0L
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
}