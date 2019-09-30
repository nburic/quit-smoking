package com.example.sampleapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData


class MainViewModel(application: Application) : AndroidViewModel(application) {
    internal val state: MutableLiveData<State> = MutableLiveData()

    sealed class State {
        object Done : State()
        object Loading: State()
        object Error: State()
    }

    fun setState(state: State) {
        this.state.postValue(state)
    }
}