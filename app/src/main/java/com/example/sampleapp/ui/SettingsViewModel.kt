package com.example.sampleapp.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.sampleapp.db.AppDatabase
import com.example.sampleapp.db.User
import com.example.sampleapp.repo.AppRepo
import kotlinx.coroutines.launch


class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: AppRepo

    val user: LiveData<User>

    private val state: MutableLiveData<State> = MutableLiveData()

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

    private fun setState(state: State) {
        this.state.postValue(state)
    }

    fun setUserData(user: User) = viewModelScope.launch {
        repo.insert(user)
    }
}