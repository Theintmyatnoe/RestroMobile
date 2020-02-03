package com.example.restromobile.database.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
class InvoiceTable(
    @PrimaryKey
    @NonNull
    var invoiceID:String="",
    var invoiceNo:String="",
    var date:String="",
    var totalPrice:String="",
    var customerType:String="",
    var tableNo:String="",
    var Active:String="",
    var CreatedBy:String="",
    var CreatedOn:String="",
    var ModifiedBy:String="",
    var ModifiedOn: String=""
) {
}