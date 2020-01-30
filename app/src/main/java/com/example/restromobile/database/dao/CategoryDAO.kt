package com.example.restromobile.database.dao

import androidx.room.*
import com.example.restromobile.database.model.Categories

@Dao
interface CategoryDAO {
    @Insert
    fun add(categories: Categories)

    @Update
    fun update(categories: Categories)

    @Query(value = "Select * from Categories")
    fun getAllCategory():List<Categories>

    @Query(value = "DELETE FROM Categories WHERE CategoryID=:categoryID")
    fun deleteByID(categoryID:String)

}