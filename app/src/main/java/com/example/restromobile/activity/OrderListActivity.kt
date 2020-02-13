package com.example.restromobile.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restromobile.MainActivity
import com.example.restromobile.R
import com.example.restromobile.adapter.FoodOrderMenuListAdapter
import com.example.restromobile.adapter.InvoiceListForOrderAdapter
import com.example.restromobile.database.AppDatabase
import com.example.restromobile.database.model.InvoiceTable
import kotlinx.android.synthetic.main.activity_orders.*
import java.text.SimpleDateFormat
import java.util.*

class OrderListActivity: AppCompatActivity() {
    private var appDatabase:AppDatabase?=null
    private var invoiceList:List<InvoiceTable>?= arrayListOf()


    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)
        supportActionBar?.title="Order List"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        appDatabase= AppDatabase.getDatabase(this)

        val date = Date()
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val currentdate = formatter.format(date)

        val linearLayoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        activity_order_list.layoutManager = linearLayoutManager
        invoiceList= appDatabase!!.getInvoiceDAO().getAllByDate(currentdate)

        if (invoiceList!!.isNotEmpty()){
            val adapter = InvoiceListForOrderAdapter(this, invoiceList!!)
            activity_order_list.adapter=adapter
        }

        btn_go_invoice.setOnClickListener{
            val intent= Intent(this, InvoiceListActivity::class.java)
            startActivity(intent)
            finish()
        }

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