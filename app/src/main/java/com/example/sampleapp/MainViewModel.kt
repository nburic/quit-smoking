package com.example.sampleapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.data.AppRepo
import com.example.sampleapp.data.db.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: AppRepo) : ViewModel() {

    private val _user: MutableLiveData<UserEntity> = MutableLiveData()
    val user: LiveData<UserEntity> = _user

    init {
        viewModelScope.launch {
            _user.value = repository.getUser()
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