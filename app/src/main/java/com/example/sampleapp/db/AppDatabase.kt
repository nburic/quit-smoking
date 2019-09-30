package com.example.sampleapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    private class AppDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
//                    populateDatabase(database.userDao())
//                    getUserData(database.userDao())
                }
            }
        }

//        suspend fun populateDatabase(userDao: UserDao) {
//            userDao.delete()
//        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "app.db")
                    .addCallback(AppDatabaseCallback(scope))
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}