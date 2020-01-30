package com.example.restromobile.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.restromobile.R
import com.example.restromobile.database.model.Categories
import com.example.restromobile.delegate.SendIdDelegate

class CategoryListAdapter(private var context: Context, private var categoriesList: List<Categories>, private var sendIdDelegate: SendIdDelegate) : RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_category_list, parent, false)
        return ViewHolder(v,sendIdDelegate)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: CategoryListAdapter.ViewHolder, position: Int) {
        holder.bindItems(categoriesList[position])

    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    class ViewHolder(
        itemView: View,
        private var delegate: SendIdDelegate
    ) : RecyclerView.ViewHolder(itemView), View.OnLongClickListener {
        override fun onLongClick(v: View?): Boolean {
            categoryID?.let { delegate.sendDeleteID(it) }
            return true
        }

        private var categoryID:String?=""
        fun bindItems(categories: Categories) {
            val tvCategory = itemView.findViewById(R.id.tv_category_name_info) as TextView
            tvCategory.text = categories.CategoryName
            categoryID=categories.CategoryID
            itemView.setOnClickListener {

                delegate.sendId(categoryID!!)
            }
            itemView.setOnLongClickListener(this)

        }
    }
}
