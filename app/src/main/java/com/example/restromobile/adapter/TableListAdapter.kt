package com.example.restromobile.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.restromobile.R
import com.example.restromobile.database.model.Tables
import com.example.restromobile.delegate.SendIdDelegate

class TableListAdapter(private var context: Context,private var tableList: List<Tables>,private var delegate:SendIdDelegate) : RecyclerView.Adapter<TableListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableListAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_table_list, parent, false)
        return ViewHolder(v,delegate)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: TableListAdapter.ViewHolder, position: Int) {
        holder.bindItems(tableList[position])

    }

    override fun getItemCount(): Int {
        return tableList.size
    }

    class ViewHolder(
        itemView: View,
        delegate: SendIdDelegate
    ) : RecyclerView.ViewHolder(itemView), View.OnLongClickListener {
        override fun onLongClick(v: View?): Boolean {
            tableID?.let { delegate.sendDeleteID(it) }
            return true
        }

        private var delegate=delegate
        private var tableID:String?=""
        fun bindItems(tables: Tables) {
            val tvTableno = itemView.findViewById(R.id.tv_table_no_info) as TextView
            val tvFloor  = itemView.findViewById(R.id.tv_floor_info) as TextView
            tvTableno.text = tables.TableName
            tvFloor.text=tables.Floor
            tableID=tables.TableID
            itemView.setOnClickListener {

                delegate.sendId(tableID!!)
            }
            itemView.setOnLongClickListener(this)

        }
    }
}
