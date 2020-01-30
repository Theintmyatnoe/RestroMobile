package com.example.restromobile.database.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "Tables")
class Tables(
    @PrimaryKey
    @NonNull
    var TableID:String="",
    var TableName:String="",
    var Floor:String="",
    var Availiable: Boolean? =null,
    var Active:String="",
    var CreatedBy:String="",
    var CreatedOn:String="",
    var ModifiedBy:String="",
    var ModifiedOn: String=""

) {
}