package com.example.sampleapp.di

import com.example.sampleapp.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
internal interface AppComponent {
    fun inject(obj: MainViewModel)
}