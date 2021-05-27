package com.example.apicalling

import android.app.Application

class LogsApplication:Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { LogsDataSource(database.logsDao()) }

    init {

    }

    companion object{
        private var logsInstance:LogsApplication?=null
        private var instance :Logs?=null


        fun getInstance():LogsApplication?{
            return logsInstance
        }

        fun getLogs():Logs{
            if(instance!=null)
                return instance!!
            else{
                instance=Logs()
                return instance!!
            }
        }
    }
    override fun onCreate() {
        super.onCreate()
        logsInstance=this
    }
}