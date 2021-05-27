package com.example.apicalling

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Logs::class),version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun logsDao() : LogsDao

    companion object{
        @Volatile
        private var INSTANCE:AppDatabase?= null
        fun getDatabase(context: Context):AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "Logs_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
        private var instance:AppDatabase?= null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it }
        }
        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AppDatabase::class.java, "logs.db")
            .build()
    }
}