package com.example.restromobile.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restromobile.MainActivity
import com.example.restromobile.R
import com.example.restromobile.adapter.InvoiceListAdapter
import com.example.restromobile.adapter.InvoiceListForOrderAdapter
import com.example.restromobile.database.AppDatabase
import com.example.restromobile.database.model.InvoiceTable
import kotlinx.android.synthetic.main.activity_invoices.*
import kotlinx.android.synthetic.main.activity_orders.*
import kotlinx.android.synthetic.main.custom_menu_bar.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class InvoiceListActivity: AppCompatActivity() {
    private var appDatabase:AppDatabase?=null
    private var invoiceList:List<InvoiceTable>?= arrayListOf()
//    private var formatter:SimpleDateFormat?=null
    private var calendar:Calendar?=null
    private var myDate:String?=""

    @SuppressLint("WrongConstant", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoices)
        supportActionBar?.title="Invoice List"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        appDatabase= AppDatabase.getDatabase(this)
        calendar = Calendar.getInstance()

        var date = Date()
        var formatter = SimpleDateFormat("yyyy-MM-dd")
        tv_date.text = formatter.format(date)
        myDate=tv_date.text.toString().trim()



        tv_date.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                myDate=tv_date.text.toString().trim()
                val linearLayoutManager = LinearLayoutManager(this@InvoiceListActivity, LinearLayout.VERTICAL, false)
                invoice_list_recycler.layoutManager = linearLayoutManager
                invoiceList= myDate?.let { appDatabase!!.getInvoiceDAO().getAllByDate(it) }



                if (invoiceList!!.isNotEmpty()){
                    invoice_list_recycler.visibility=View.VISIBLE
                    val adapter = InvoiceListAdapter(this@InvoiceListActivity, invoiceList!!)
                     invoice_list_recycler.adapter=adapter
                }
                else{
                    invoice_list_recycler.visibility=View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })


        val linearLayoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        invoice_list_recycler.layoutManager = linearLayoutManager
        invoiceList= myDate?.let { appDatabase!!.getInvoiceDAO().getAllByDate(it) }



        if (invoiceList!!.isNotEmpty()){
            invoice_list_recycler.visibility=View.VISIBLE
            val adapter = InvoiceListAdapter(this, invoiceList!!)
            invoice_list_recycler.adapter=adapter
        }
        else{
            invoice_list_recycler.visibility=View.GONE
        }

        date_back.setOnClickListener {

            calendar!!.add(Calendar.DAY_OF_YEAR, -1)
            val mydate=formatter.format(calendar!!.time)
            tv_date.text=mydate

        }
        date_next.setOnClickListener {
            calendar!!.add(Calendar.DAY_OF_YEAR, +1)
            val mydate=formatter.format(calendar!!.time)
            tv_date.text=mydate
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