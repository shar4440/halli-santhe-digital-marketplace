package com.example.hallisanthedigital.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hallisanthedigital.dao.*
import com.example.hallisanthedigital.model.*

@Database(
    entities = [
        User::class,
        Product::class,
        ChatMessage::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun productDao(): ProductDao

    abstract fun chatDao(): ChatDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context
        ): AppDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "halli_market_db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                INSTANCE = instance
                instance
            }
        }
    }
}