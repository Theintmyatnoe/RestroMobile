package com.example.restromobile.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.restromobile.R
import com.example.restromobile.database.model.Tables
import com.example.restromobile.delegate.SendIdDelegate

class GridViewAdapter(private var context: Context, private var tableList: List<Tables>): BaseAdapter() {
    private var strtableID:String?=""
    private var sendIdDelegate:SendIdDelegate?=null
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater=context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView=layoutInflater.inflate(R.layout.grid_item,parent,false)
        val tableName=itemView.findViewById<TextView>(R.id.table_name)
        tableName.text= tableList[position].TableName

        itemView.setOnClickListener{
            strtableID=tableList[position].TableID
            sendIdDelegate!!.sendId(strtableID!!)
        }

        return itemView
    }

    override fun getItem(position: Int): Any {
        return tableList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return tableList.size
    }
    fun sendItemIDForTable(sendIdDelegate: SendIdDelegate){
            this.sendIdDelegate=sendIdDelegate
        }
//    companion object{
//        fun sendItemIDForTable(sendIdDelegate: SendIdDelegate){
//            this.se
//        }
//    }
}