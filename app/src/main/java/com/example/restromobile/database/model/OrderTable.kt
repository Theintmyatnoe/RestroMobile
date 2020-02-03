package com.example.restromobile.database.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class OrderTable(
    @PrimaryKey
    @NonNull
    var orderID:String="",
    var invoiceID:String="",
    var itemID:String="",
    var qty:String="",
    var price:String="",
    var status:String="",
    var Active:String="",
    var CreatedBy:String="",
    var CreatedOn:String="",
    var ModifiedBy:String="",
    var ModifiedOn: String=""
) {
}