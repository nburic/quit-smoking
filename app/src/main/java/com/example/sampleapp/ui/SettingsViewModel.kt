package com.example.sampleapp.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sampleapp.db.User


class SettingsViewModel : ViewModel() {

    sealed class State {
        object Done : State()
        object Loading: State()
        object Error: State()
    }

    val state: MutableLiveData<State> = MutableLiveData()

    private fun setState(state: State) {
        this.state.postValue(state)
    }

    fun updateUser(user: User) {
        Log.d("!!!", "!!!Inserting into db $user")
    }
}