package com.example.apicalling

import androidx.room.*
import com.example.apicalling.model.User

@Dao
interface LogsDao {

    @Query("SELECT * FROM logs")
    fun getAll():List<Logs>

    @Insert
    fun insertAll(vararg logs: Logs)

    @Delete
    fun delete(logs: Logs)

    @Update
    fun updateLog(vararg logs: Logs)

    @Query("Update logs set response =:response Where id=:id")
    fun update(id:String,response:String)


}