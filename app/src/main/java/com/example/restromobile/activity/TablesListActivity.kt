package com.example.restromobile.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restromobile.MainActivity
import com.example.restromobile.R
import com.example.restromobile.adapter.TableListAdapter
import com.example.restromobile.database.AppDatabase
import com.example.restromobile.database.model.Tables
import com.example.restromobile.delegate.SendIdDelegate
import kotlinx.android.synthetic.main.activity_table.*

class TablesListActivity:AppCompatActivity(), View.OnClickListener, SendIdDelegate {
    override fun sendDeleteID(tableID: String) {
        appDatabase!!.getTableDAO().deleteByTableID(tableID)
        tableList=appDatabase!!.getTableDAO().getAllTables()
        val adapter = TableListAdapter(this, tableList!!,this)
        table_list_recycler.adapter=adapter
    }

    override fun sendId(tableID: String) {
        val intent=Intent(this,AddTablesActivity::class.java)
        intent.putExtra("TableID",tableID)
        startActivity(intent)
        finish()
    }

    private var tableList:List<Tables>?= arrayListOf()
    private var appDatabase:AppDatabase?=null

    override fun onClick(v: View?) {
        val intent=Intent(this,AddTablesActivity::class.java)
        startActivity(intent)
        finish()
    }

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table)
        supportActionBar?.title="Table List"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        appDatabase= AppDatabase.getDatabase(this)
        tableList=appDatabase!!.getTableDAO().getAllTables()

        val linearLayoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        table_list_recycler.layoutManager = linearLayoutManager

        val adapter = TableListAdapter(this, tableList!!,this)
        table_list_recycler.adapter=adapter


        fb_add_table.setOnClickListener(this)
    }
    private fun goToBack(){
        val intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        goToBack()
    }

    override fun onSupportNavigateUp(): Boolean {
        goToBack()
        return true
    }
}