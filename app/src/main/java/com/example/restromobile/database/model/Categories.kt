package com.example.restromobile.database.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
class Categories(
    @PrimaryKey
    @NonNull
    var CategoryID:String="",
    var CategoryName:String="",
    var Active:String="",
    var CreatedBy:String="",
    var CreatedOn:String="",
    var ModifiedBy:String="",
    var ModifiedOn: String=""
) {
}