package com.example.restromobile.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.restromobile.R
import com.example.restromobile.database.model.MenusItem
import com.example.restromobile.database.model.Tables
import com.example.restromobile.delegate.SendIdDelegate
import java.text.DecimalFormat

class FoodOrderMenuGridAdapter(private var context: Context, private var menulist: List<MenusItem>): BaseAdapter() {
    private var menuItemID:String?=""
    private var sendIdDelegate: SendIdDelegate?=null
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater=context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView=layoutInflater.inflate(R.layout.item_food_order_menu_list,parent,false)
        val tvMenuName = itemView.findViewById(R.id.menu_name_info) as TextView
        val tvPrice  = itemView.findViewById(R.id.menu_price_info) as TextView
        var imageView=itemView.findViewById(R.id.menu_item_img_info) as ImageView
        val btnAddToOrder=itemView.findViewById<Button>(R.id.btn_to_order)

        val dec = DecimalFormat("#,###.##")

        tvMenuName.text = menulist[position].Menu_ItemName
        val price=menulist[position].Price
        tvPrice.text=dec.format(price.toInt())
        imageView.setImageURI(Uri.parse(menulist[position].ImageUri))
//        menuItemID=menulist[position].Menu_itemID

        itemView.setOnClickListener {
            menuItemID= menulist[position].Menu_itemID

            sendIdDelegate?.sendId(menuItemID!!)
        }

        btnAddToOrder.setOnClickListener {
            menuItemID= menulist[position].Menu_itemID

            sendIdDelegate?.sendId(menuItemID!!)
        }

        return itemView
    }

    override fun getItem(position: Int): Any {
        return menulist[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return menulist.size
    }
    fun sendItemIDForTable(sendIdDelegate: SendIdDelegate){
        this.sendIdDelegate=sendIdDelegate
    }

}
