package com.example.restromobile.database.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class MenusItem(
    @PrimaryKey
    @NonNull
    var Menu_itemID:String="",
    var CategoriID:String="",
    var Menu_ItemName:String="",
    var CategoryName:String="",
    var Price:String="",
    var ImageUri:String="",
    var Active:String="",
    var CreatedBy:String="",
    var CreatedOn:String="",
    var ModifiedBy:String="",
    var ModifiedOn: String=""
) {
}