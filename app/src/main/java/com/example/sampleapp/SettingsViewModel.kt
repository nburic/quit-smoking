package com.example.sampleapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.data.db.AppDatabase
import com.example.sampleapp.data.db.UserEntity
import com.example.sampleapp.models.SettingsInputItem
import com.example.sampleapp.data.AppRepo
import kotlinx.coroutines.launch


class SettingsViewModel(application: Application) : AndroidViewModel(application) {

//    private val repo: AppRepo
//
//    internal val userEntity: LiveData<UserEntity>
//    internal val state: MutableLiveData<State> = MutableLiveData()
//
//    internal var dateTimestamp: MutableLiveData<Long> = MutableLiveData()
//
//    sealed class State {
//        object Done : State()
//        object Loading: State()
//        object Error: State()
//    }
//
//    init {
//        val userDao = AppDatabase.getDatabase(application).userDao()
//        repo = AppRepo(userDao)
//        userEntity = repo.userEntity
//    }
//
//    fun setState(state: State) {
//        this.state.postValue(state)
//    }
//
//    fun setUserData(userEntity: UserEntity) = viewModelScope.launch {
//        repo.insert(userEntity)
//    }
//
//    fun incInputValue(input: SettingsInputItem) {
//        if (input.value == null) {
//            input.value = 0
//        }
//
//        input.value = input.value?.plus(1)
//    }
//
//    fun decInputValue(input: SettingsInputItem) {
//        val res = input.value ?: return
//
//        if (res > 0) {
//            input.value = res - 1
//        }
//    }
}