package com.example.restromobile.adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.restromobile.R
import com.example.restromobile.database.AppDatabase
import com.example.restromobile.database.model.MenusItem
import com.example.restromobile.database.obj.ItemListObj
import com.example.restromobile.delegate.SendIdDelegate
import com.example.restromobile.delegate.SendPositionForListDelegate
import com.example.restromobile.delegate.SendRemovePositionDelegate

class FoodOrderMenuListAdapter (private var context: Context,
                                private var itemListObj: ArrayList<ItemListObj>,
                                private var delegate: SendIdDelegate,
                                private var sendPositionForListDelegate: SendPositionForListDelegate,
                                private var sendRemovePositionDelegate:SendRemovePositionDelegate)
    : RecyclerView.Adapter<FoodOrderMenuListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodOrderMenuListAdapter.ViewHolder {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.item_order_list,parent,false)
//        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_food_list, parent, false)
        return ViewHolder(v,delegate,itemListObj,sendPositionForListDelegate,context,sendRemovePositionDelegate)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: FoodOrderMenuListAdapter.ViewHolder, position: Int) {
        holder.bindItems(itemListObj[position])

    }

    override fun getItemCount(): Int {
        return itemListObj.size
    }
//


    class ViewHolder(
        itemView: View,
        private var delegate: SendIdDelegate,
        private var itemListObj1: ArrayList<ItemListObj>,
        private var sendPositionForListDelegate: SendPositionForListDelegate,
        private var context: Context,
        private var sendRemovePositionDelegate: SendRemovePositionDelegate
    ) : RecyclerView.ViewHolder(itemView), View.OnLongClickListener {

        override fun onLongClick(v: View?): Boolean {
//            menuItemID?.let { delegate.sendDeleteID(it) }
            return true
        }

        private var prizeInt:Int=0
        private var totalPrize:Int=0
        private var total:Int=0
        private var qtyInt:Int?=0
        private var sendPositionDelegate:SendPositionForListDelegate?=null
        private var sendRemovePosition:SendRemovePositionDelegate?=null
        private var itemListObj=itemListObj1
        private var appDatabase:AppDatabase?=null
        var totalQty:Int?=0
        private var menusitemID:String?=null
        private var menuItemList:List<MenusItem>?= arrayListOf()
        var mprize:Int=0

        fun bindItems(itemListObj: ItemListObj) {
            val tvItemName=itemView.findViewById<TextView>(R.id.item_order_name_info)
            val tvItemQty=itemView.findViewById<TextView>(R.id.item_order_qty_info)
            val tvItemPrice=itemView.findViewById<TextView>(R.id.item_order_price_info)
            val imgRemove=itemView.findViewById<ImageView>(R.id.img_remove)
            val imgAdd=itemView.findViewById<ImageView>(R.id.img_add_qty)
            val imgMinus=itemView.findViewById<ImageView>(R.id.img_remove_qty)
            tvItemName.text=itemListObj.itemName
            tvItemQty.text=itemListObj.qty
            tvItemPrice.text=itemListObj.price
            menusitemID=itemListObj.itemID

            appDatabase= AppDatabase.getDatabase(context)
            menuItemList=appDatabase!!.getMenuItemDAO().getAllMenuItemByID(menusitemID!!)

            this.sendPositionDelegate=sendPositionForListDelegate
            this.sendRemovePosition=sendRemovePositionDelegate

            for (menusItems in menuItemList!!){
                mprize=menusItems.Price.toInt()
            }


            var qty=tvItemQty.text.toString()
            qtyInt=qty.toInt()

            var prize=tvItemPrice.text.toString()
            prizeInt=prize.toInt()


//            qty=tvItemQty.text.toString()
//            qtyInt=qty.toInt()
            if (qtyInt==1){
                imgMinus.isEnabled=false
            }
            if (qtyInt!! >1){
                imgMinus.isEnabled=true
            }


            for (items in itemListObj1){
                var prize=itemListObj1[adapterPosition].price
                var intPrize=prize.toInt()
                total+=intPrize
            }

            tvItemQty.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {
                    qty=tvItemQty.text.toString()
                    qtyInt=qty.toInt()
                    if (qtyInt==1){
                        imgMinus.isEnabled=false
                    }
                    if (qtyInt!! >1){
                        imgMinus.isEnabled=true
                    }
                }

                override fun beforeTextChanged(s: CharSequence, start: Int,
                                               count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence, start: Int,
                                           before: Int, count: Int) {
                }
            })

            tvItemPrice.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {
                    prize=tvItemPrice.text.toString()
                    prizeInt=prize.toInt()
                    total += prizeInt
                    qty=tvItemQty.text.toString()
                    qtyInt=qty.toInt()
                    if (qtyInt==1){
                        imgMinus.isEnabled=false
                    }
                    if (qtyInt!! >1){
                        imgMinus.isEnabled=true
                    }

                }

                override fun beforeTextChanged(s: CharSequence, start: Int,
                                               count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence, start: Int,
                                           before: Int, count: Int) {
                }
            })



            imgAdd.setOnClickListener{
               totalQty= qtyInt!! +1
                totalPrize=prizeInt+mprize

                Log.e("total",totalPrize.toString())

                tvItemQty.text = totalQty.toString()
                tvItemPrice.text=totalPrize.toString()

                val position=adapterPosition
                val itemObj= itemListObj1[adapterPosition]
                itemListObj1.removeAt(adapterPosition)


                if (sendPositionDelegate!=null){
                    sendPositionDelegate!!.sendPosition(position = position,qty = totalQty.toString(),price = totalPrize.toString(),itemListObj = itemObj)

                }


            }
            imgMinus.setOnClickListener{
                totalPrize=prizeInt-mprize
                totalQty= qtyInt!! -1

                Log.e("total",totalPrize.toString())

                tvItemQty.text = totalQty.toString()

                tvItemPrice.text=totalPrize.toString()

                itemListObj1.removeAt(adapterPosition)

                val position=adapterPosition
                val itemObj= itemListObj1[adapterPosition]

                if (sendPositionDelegate!=null){
                    sendPositionDelegate!!.sendPosition(position = position,qty = totalQty.toString(),price = totalPrize.toString(),itemListObj = itemObj)


                }
            }
            imgRemove.setOnClickListener{
                itemListObj1.removeAt(adapterPosition)

                if (sendRemovePosition!=null){
                    sendRemovePosition!!.sendRemovePosition(position = adapterPosition,count = itemListObj1.size)
                }
            }
        }


    }

}