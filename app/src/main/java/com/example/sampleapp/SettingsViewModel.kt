package com.example.sampleapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.db.AppDatabase
import com.example.sampleapp.db.User
import com.example.sampleapp.models.SettingsInputItem
import com.example.sampleapp.repo.AppRepo
import kotlinx.coroutines.launch


class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: AppRepo

    internal val user: LiveData<User>
    internal val state: MutableLiveData<State> = MutableLiveData()

    sealed class State {
        object Done : State()
        object Loading: State()
        object Error: State()
    }

    init {
        val userDao = AppDatabase.getDatabase(application, viewModelScope).userDao()
        repo = AppRepo(userDao)
        user = repo.user
    }

    fun setState(state: State) {
        this.state.postValue(state)
    }

    fun setUserData(user: User) = viewModelScope.launch {
        repo.insert(user)
    }

    fun incInputValue(input: SettingsInputItem) {
        val res = input.value?.toInt()?.plus(1)
        input.value = res.toString()
    }

    fun decInputValue(input: SettingsInputItem) {
        val res = input.value?.toInt()

        if (res != null) {
            when (res > 0) {
                true -> {
                    val newVal = res - 1
                    input.value = newVal.toString()
                }
                false -> {}
            }
        }
    }
}