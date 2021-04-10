package com.example.sampleapp.hilt

import android.content.Context
import androidx.room.Room
import com.example.sampleapp.data.db.AppDatabase
import com.example.sampleapp.data.db.store.StoreItemDao
import com.example.sampleapp.data.db.user.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, "app.db").build()
    }

    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    fun provideStoreDao(appDatabase: AppDatabase): StoreItemDao {
        return appDatabase.storeItemDao()
    }
}