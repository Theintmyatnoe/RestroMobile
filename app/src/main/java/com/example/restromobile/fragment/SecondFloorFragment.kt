package com.example.restromobile.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment
import com.example.restromobile.R
import com.example.restromobile.adapter.GridViewAdapter
import com.example.restromobile.database.AppDatabase
import com.example.restromobile.database.model.Tables

class SecondFloorFragment : Fragment() {
    private var tableList:List<Tables>?= arrayListOf()
    private var appDatabase:AppDatabase?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.fragment_second_floor, container, false)
        val gridView=view.findViewById<GridView>(R.id.grdImages)
        appDatabase= this.activity?.let { AppDatabase.getDatabase(it) }
        tableList=appDatabase!!.getTableDAO().getAllTablesByFloor("Second Floor")

        val adapter= activity?.let { tableList?.let { it1 -> GridViewAdapter(it, it1) } }
        gridView.adapter=adapter

        return view
    }
}
