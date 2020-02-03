package com.example.restromobile.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.restromobile.R
import com.example.restromobile.database.AppDatabase
import com.example.restromobile.database.model.OrderTable

class OrderItemListAdapter (private var context: Context, private var orderItemList: List<OrderTable>) : RecyclerView.Adapter<OrderItemListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemListAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_order_item_list_by_invoice, parent, false)
        return ViewHolder(v,context)
    }

    //this method is binding the data on the list
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: OrderItemListAdapter.ViewHolder, position: Int) {
        holder.bindItems(orderItemList[position])

    }

    override fun getItemCount(): Int {
        return orderItemList.size
    }

    class ViewHolder(
        itemView: View,
        private var context: Context
    ) : RecyclerView.ViewHolder(itemView){

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        @SuppressLint("WrongConstant", "SetTextI18n")
        fun bindItems(orderTable: OrderTable) {
            val tvItemName=itemView.findViewById<TextView>(R.id.tv_item_name)
            val tvItemPrice=itemView.findViewById<TextView>(R.id.tv_item_price)
            val tvItemQty=itemView.findViewById<TextView>(R.id.tv_item_qty)
            val tvStaus=itemView.findViewById<TextView>(R.id.tv_status)

            val itemID=orderTable.itemID
            val appDatabase=AppDatabase.getDatabase(context)
            val itemList=appDatabase.getMenuItemDAO().getAllMenuItemByID(itemID)
            for (items in itemList){
                tvItemName.text=items.Menu_ItemName
            }
            tvStaus.text=orderTable.status
            tvItemPrice.text=orderTable.price
            tvItemQty.text=orderTable.qty
            val invoiceID=orderTable.invoiceID

            tvStaus.setOnClickListener {
                if (tvStaus.text=="waiting"){
                    tvStaus.text="complete"
                    tvStaus.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#2e7d32"))
//                    tvStaus.setBackgroundColor(Color.parseColor(""))
                }
                else{
                    tvStaus.text="waiting"
                    tvStaus.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#e60023"))
                }
            }


        }
    }
}