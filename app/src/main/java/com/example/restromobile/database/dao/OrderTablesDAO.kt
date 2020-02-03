package com.example.restromobile.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.restromobile.database.model.OrderTable

@Dao
interface OrderTablesDAO {
    @Insert
    fun add(orderTable: OrderTable)

    @Query(value = "Select * from OrderTable")
    fun getAll():List<OrderTable>

    @Query(value = "Select * from OrderTable where invoiceID=:invoiceID")
    fun getAllByInvoiceID(invoiceID:String):List<OrderTable>
}