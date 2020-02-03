package com.example.restromobile.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restromobile.R
import com.example.restromobile.database.AppDatabase
import com.example.restromobile.database.model.InvoiceTable
import kotlinx.android.synthetic.main.activity_orders.*

class InvoiceListForOrderAdapter (private var context: Context, private var invoiceList: List<InvoiceTable>) : RecyclerView.Adapter<InvoiceListForOrderAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvoiceListForOrderAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_order_list_by_invoice, parent, false)
        return ViewHolder(v,context)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: InvoiceListForOrderAdapter.ViewHolder, position: Int) {
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
        private var floor:String?=""
        @SuppressLint("WrongConstant", "SetTextI18n")
        fun bindItems(invoiceTable: InvoiceTable) {
            val tvTableNo=itemView.findViewById<TextView>(R.id.tv_table_no)
            val tvInvoiceNo=itemView.findViewById<TextView>(R.id.tv_invoice_no)
            val tvdate=itemView.findViewById<TextView>(R.id.tv_date)
            var tvfloor=itemView.findViewById<TextView>(R.id.tv_floor_info)
            val tvTotal=itemView.findViewById<TextView>(R.id.tv_Total)
            val orderRecycler=itemView.findViewById<RecyclerView>(R.id.order_item_list_recycler)

            appDatabase= AppDatabase.getDatabase(context)
            if (invoiceTable.tableNo.isNotEmpty()){
                val tableID=invoiceTable.tableNo

               val tableList= appDatabase!!.getTableDAO().getAllTablesByTableID(tableID)
                for (tables in tableList){
                    tableNo=tables.TableName
                    floor=tables.Floor
                }
                tvTableNo.text= "Table No :$tableNo"
                tvfloor.text="Floor :$floor"
            }
            if (invoiceTable.tableNo=="null"){
                tvTableNo.text="Table No : -"
                tvfloor.text="Floor : -"
            }
            tvInvoiceNo.text="Invoice No :"+invoiceTable.invoiceNo
            tvdate.text="Date :"+invoiceTable.date
            tvTotal.text="Total :"+invoiceTable.totalPrice
//            floor.text="Floor:"+invoiceTable.

            val invoiceID=invoiceTable.invoiceID

            val orderList=appDatabase!!.getOrderDAO().getAllByInvoiceID(invoiceID)
            if (orderList.isNotEmpty()){
                val linearLayoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
                orderRecycler.layoutManager = linearLayoutManager
                val adapter = OrderItemListAdapter(context, orderList!!)
                orderRecycler.adapter=adapter
            }

//            val tvMenuName = itemView.findViewById(R.id.menu_name_info) as TextView
//            val tvPrice  = itemView.findViewById(R.id.menu_price_info) as TextView
//            var imageView=itemView.findViewById(R.id.menu_item_img_info) as ImageView
//            var llMenu=itemView.findViewById<LinearLayout>(R.id.ll_menu_item)
//
//            tvMenuName.text = menusItem.Menu_ItemName
//            tvPrice.text=menusItem.Price
//            imageView.setImageURI(Uri.parse(menusItem.ImageUri))
//            menuItemID=menusItem.Menu_itemID
//
//            llMenu.setOnClickListener {
//
//                delegate.sendId(menuItemID!!)
//            }
//            llMenu.setOnLongClickListener(this)

        }
    }
}