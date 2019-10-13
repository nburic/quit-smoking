package com.example.sampleapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.db.AppDatabase
import com.example.sampleapp.db.User
import com.example.sampleapp.repo.AppRepo


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: AppRepo

    internal val user: LiveData<User>
    internal val state: MutableLiveData<State> = MutableLiveData()

    sealed class State {
        object Done : State()
        object Loading: State()
        object Error: State()
    }

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repo = AppRepo(userDao)
        user = repo.user
    }

    fun setState(state: State) {
        this.state.postValue(state)
    }

    fun inputDataNotSet(): Boolean {
        return user.value?.date == null
    }
}