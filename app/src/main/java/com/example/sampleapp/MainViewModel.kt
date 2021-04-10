package com.example.sampleapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.data.AppRepo
import com.example.sampleapp.data.db.store.StoreItemEntity
import com.example.sampleapp.data.db.user.UserEntity
import com.example.sampleapp.util.DateConverters.getGoalIndex
import com.example.sampleapp.util.DateConverters.getGoalTimestamp
import com.example.sampleapp.util.Epoch.calcDifferenceToDays
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: AppRepo) : ViewModel() {

    val user: LiveData<UserEntity> = repository.observeUser()
    val store: LiveData<List<StoreItemEntity>> = repository.observeStore()
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

    fun setGoal(position: Int) {
        viewModelScope.launch {
            val start = user.value?.start ?: return@launch
            val timestamp = getGoalTimestamp(position, start)
            repository.updateGoal(timestamp)
        }
    }

    fun getGoal(): Int {
        val start = user.value?.start ?: return 0
        val goal = user.value?.goal ?: return 0

        return getGoalIndex(start, goal)
    }

    fun calculateNotSmoked(): Int {
        return user.value?.let {
            val days = calcDifferenceToDays(it.start)

            (days.toFloat() * it.cigPerDay.toFloat()).toInt()
        } ?: 0
    }

    fun getSmokeFreeDays(): Int {
        return user.value?.let {
            calcDifferenceToDays(it.start)
        } ?: 0
    }

    fun getRegainedDays(): Int {
        val notSmoked = calculateNotSmoked()
        return calculateLifeRegained(notSmoked)
    }

    private fun calculateLifeRegained(notSmoked: Int): Int {
        return notSmoked * 11 / 60 / 24
    }

    fun addStoreItem(item: StoreItemEntity) {
        viewModelScope.launch {
            repository.addStoreItem(item)
        }
    }

    fun removeStoreItem(id: Int) {
        viewModelScope.launch {
            repository.removeStoreItem(id)
        }
    }
}