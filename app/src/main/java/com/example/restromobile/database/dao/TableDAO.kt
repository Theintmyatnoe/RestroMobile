package com.example.restromobile.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.restromobile.database.model.Tables

@Dao
interface TableDAO {
    @Insert
    fun add(tables: Tables)

    @Update
    fun update(tables: Tables)

    @Query(value = "Select * from Tables")
    fun getAllTables():List<Tables>

    @Query(value = "Select * from Tables where Floor=:floor")
    fun getAllTablesByFloor(floor:String):List<Tables>

    @Query(value = "Select * from Tables where TableID=:tableID ")
    fun getAllTablesByTableID(tableID: String):List<Tables>

    @Query(value = "Delete from Tables where TableID=:tableID")
    fun deleteByTableID(tableID:String)
}