package com.example.restromobile.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.restromobile.R
import com.example.restromobile.database.model.MenusItem
import com.example.restromobile.database.model.Tables
import com.example.restromobile.delegate.SendIdDelegate

class MenuListAdapter(private var context: Context, private var menuList: List<MenusItem>, private var delegate: SendIdDelegate) : RecyclerView.Adapter<MenuListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuListAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_menu_list, parent, false)
        return ViewHolder(v,delegate)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: MenuListAdapter.ViewHolder, position: Int) {
        holder.bindItems(menuList[position])

    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    class ViewHolder(
        itemView: View,
        private var delegate: SendIdDelegate
    ) : RecyclerView.ViewHolder(itemView), View.OnLongClickListener {

        override fun onLongClick(v: View?): Boolean {
            menuItemID?.let { delegate.sendDeleteID(it) }
            return true
        }

        private var menuItemID:String?=""
        fun bindItems(menusItem: MenusItem) {
            val tvMenuName = itemView.findViewById(R.id.menu_name_info) as TextView
            val tvPrice  = itemView.findViewById(R.id.menu_price_info) as TextView
            var imageView=itemView.findViewById(R.id.menu_item_img_info) as ImageView
            var llMenu=itemView.findViewById<LinearLayout>(R.id.ll_menu_item)

            tvMenuName.text = menusItem.Menu_ItemName
            tvPrice.text=menusItem.Price
            imageView.setImageURI(Uri.parse(menusItem.ImageUri))
            menuItemID=menusItem.Menu_itemID

            llMenu.setOnClickListener {

                delegate.sendId(menuItemID!!)
            }
            llMenu.setOnLongClickListener(this)

        }
    }

}
