package com.example.restromobile.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.restromobile.R
import com.example.restromobile.database.AppDatabase
import com.example.restromobile.database.model.InvoiceTable

class InvoiceListAdapter (private var context: Context, private var invoiceList: List<InvoiceTable>) : RecyclerView.Adapter<InvoiceListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvoiceListAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_invoice_list, parent, false)
        return ViewHolder(v,context)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: InvoiceListAdapter.ViewHolder, position: Int) {
        holder.bindItems(invoiceList[position])

    }

    override fun getItemCount(): Int {
        return invoiceList.size
    }

    class ViewHolder(
        itemView: View,
        private var context: Context
    ) : RecyclerView.ViewHolder(itemView){
        private var appDatabase:AppDatabase?=null
        private var tableNo:String?=""

        fun bindItems(invoiceTable: InvoiceTable) {
            val tvTableNo=itemView.findViewById<TextView>(R.id.table_no)
            val tvInvoiceNo=itemView.findViewById<TextView>(R.id.invoice_no)
            val tvCust=itemView.findViewById<TextView>(R.id.cust_type)
            val tvTotal=itemView.findViewById<TextView>(R.id.total_price)
            appDatabase= AppDatabase.getDatabase(context)

            if (invoiceTable.tableNo.isNotEmpty()){

                val tableID=invoiceTable.tableNo

                val tableList= appDatabase!!.getTableDAO().getAllTablesByTableID(tableID)
                for (tables in tableList){
                    tableNo=tables.TableName
                }
                tvTableNo.text= tableNo
            }
            if (invoiceTable.tableNo=="null"){
                tvTableNo.text="-"
            }
            if (tvInvoiceNo.text.isNotEmpty()){
                tvInvoiceNo.text=invoiceTable.invoiceNo
            }
            else{
                tvInvoiceNo.text="-"
            }
            if (invoiceTable.customerType.isNotEmpty()){
                tvCust.text=invoiceTable.customerType
            }
            else{
                invoiceTable.customerType="-"
            }
            if (invoiceTable.totalPrice.isNotEmpty()){
                tvTotal.text=invoiceTable.totalPrice
            }
            else{
                invoiceTable.totalPrice="-"
            }

        }
    }
}