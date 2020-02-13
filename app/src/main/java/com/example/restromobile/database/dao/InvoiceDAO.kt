package com.example.restromobile.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.restromobile.database.model.InvoiceTable
@Dao
interface InvoiceDAO {
    @Insert
    fun add(invoiceTable: InvoiceTable)

    @Query(value = "Select * from InvoiceTable order by date desc")
    fun getAll():List<InvoiceTable>

    @Query(value = "Select * from InvoiceTable where date like '%' || :date || '%' order by date desc")
    fun getAllByDate(date:String):List<InvoiceTable>
}