package com.example.sampleapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.data.AppRepo
import com.example.sampleapp.data.db.user.UserWithStoreItems
import com.example.sampleapp.data.db.store.StoreItemEntity
import com.example.sampleapp.data.db.user.UserEntity
import com.example.sampleapp.di.DependencyProvider
import com.example.sampleapp.util.DateConverters.getGoalIndex
import com.example.sampleapp.util.DateConverters.getGoalTimestamp
import com.example.sampleapp.util.Epoch.calcDifferenceToDays
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel : ViewModel() {

    @Inject
    lateinit var repository: AppRepo

    val user: LiveData<UserEntity>
    val userWithStoreItems: LiveData<UserWithStoreItems>
    val store: LiveData<List<StoreItemEntity>>

    private var startEpoch: Long? = null

    init {
        DependencyProvider.appComponent.inject(this)

        user = repository.observeUser()
        userWithStoreItems = repository.observeUserWithStoreItems()
        store = repository.observeStore()
    }

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

    fun getUser(): UserEntity? {
        return user.value
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

    fun buyStoreItem(id: Int) {
        viewModelScope.launch {
            repository.buyStoreItem(id)
        }
    }
}