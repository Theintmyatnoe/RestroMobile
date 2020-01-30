package com.example.restromobile.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.restromobile.database.model.MenusItem

@Dao
interface MenuItemDAO {
    @Insert
    fun add(menusItem: MenusItem)

    @Update
    fun update(menusItem: MenusItem)

    @Query(value = "Select * from MenusItem")
    fun getAllMenuItem():List<MenusItem>

    @Query(value = "Select * from MenusItem WHERE Menu_itemID=:menItemID")
    fun getAllMenuItemByID(menItemID: String):List<MenusItem>

    @Query(value = "Select * from MenusItem WHERE CategoryName=:categoryName")
    fun getAllMenuItemByCategory(categoryName: String):List<MenusItem>

    @Query(value = "DELETE FROM MenusItem WHERE Menu_itemID=:menItemID")
    fun deleteByID(menItemID:String)

}