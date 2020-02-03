package com.example.restromobile.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment
import com.example.restromobile.R
import com.example.restromobile.activity.FoodOrderActivity
import com.example.restromobile.adapter.GridViewAdapter
import com.example.restromobile.database.AppDatabase
import com.example.restromobile.database.model.Tables
import com.example.restromobile.delegate.SendIdDelegate

class OutsideFragment : Fragment(), SendIdDelegate {
    override fun sendId(tableID: String) {
        tableList= appDatabase!!.getTableDAO().getAllTablesByTableID(tableID)
        for (tables in tableList!!){
            tableNo=tables.TableName
        }

        val intent= Intent(activity, FoodOrderActivity::class.java)
        intent.putExtra("TableID",tableID)
        intent.putExtra("TableNo",tableNo)
        intent.putExtra("CustomerType","Dine in")
        startActivity(intent)
        activity!!.finish()
    }

    override fun sendDeleteID(tableID: String) {

    }

    private var tableList:List<Tables>?= arrayListOf()
    private var appDatabase: AppDatabase?=null
    private var tableNo:String?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_first_floor, container, false)
        val grdView=view.findViewById<GridView>(R.id.grdImages)
        appDatabase= this.activity?.let { AppDatabase.getDatabase(it) }
        tableList=appDatabase!!.getTableDAO().getAllTablesByFloor("Outside")

        val adapter= activity?.let { tableList?.let { it1 -> GridViewAdapter(it, it1) } }
        grdView.adapter=adapter
        adapter!!.sendItemIDForTable(this)

        return view
    }
}
