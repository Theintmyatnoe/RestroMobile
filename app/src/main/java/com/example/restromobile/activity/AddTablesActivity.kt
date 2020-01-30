package com.example.restromobile.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.restromobile.R
import com.example.restromobile.database.AppDatabase
import com.example.restromobile.database.model.Tables
import kotlinx.android.synthetic.main.activity_add_table.*
import java.util.*
import kotlin.collections.ArrayList

class AddTablesActivity: AppCompatActivity() {
    private var strFloorList:ArrayList<String>?= arrayListOf()
    private var strSelectedFloor:String?=""
    private var appdatabase:AppDatabase?=null
    private var tableIDFromIntent:String?=null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title="Add Table"
        setContentView(R.layout.activity_add_table)

        appdatabase= AppDatabase.getDatabase(this)

        tableIDFromIntent=intent.getStringExtra("TableID")

        if (tableIDFromIntent!=null && tableIDFromIntent!!.isNotEmpty()){
            btn_save_table.text = "Update"
            val tableList=appdatabase!!.getTableDAO().getAllTablesByTableID(tableIDFromIntent!!)
            for (tables in tableList){
                if (tables.TableName.isNotEmpty()){
                    edt_table_no.setText(tables.TableName)
                }
                if (tables.Floor.isNotEmpty()){
                    strSelectedFloor=tables.Floor
                }
            }
        }

        getFloorListSpinner()

        btn_save_table.setOnClickListener {
            val strTableno=edt_table_no.text.toString().trim()
            val strFloor=floor_spinner.selectedItem.toString().trim()
            if (strTableno.isEmpty()){
                edt_table_no.error="Table no is required!"
                edt_table_no.requestFocus()
                return@setOnClickListener
            }
            else{
                if (tableIDFromIntent!=null){
                    val tables=Tables()
                    val strTableID=tableIDFromIntent
                    tables.TableID= strTableID!!
                    tables.TableName=strTableno
                    tables.Floor=strFloor
                    tables.Availiable=true
                    appdatabase!!.getTableDAO().update(tables)
                    Toast.makeText(this,"Update Success!",Toast.LENGTH_SHORT).show()
                    edt_table_no.setText("")
                }
                else{
                    val tables=Tables()
                    val strTableID=UUID.randomUUID().toString()
                    tables.TableID=strTableID
                    tables.TableName=strTableno
                    tables.Floor=strFloor
                    tables.Availiable=true
                    appdatabase!!.getTableDAO().add(tables)
                    Toast.makeText(this,"Save Success!",Toast.LENGTH_SHORT).show()
                    edt_table_no.setText("")
                }
            }
        }
    }
    private fun getFloorListSpinner(){
        strFloorList?.add("First Floor")
        strFloorList?.add("Second Floor")
        strFloorList?.add("Outside")
        val spinnerAdapter=
            strFloorList?.let {
                ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    it
                )
            }
        spinnerAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        floor_spinner!!.adapter=spinnerAdapter
        if (strSelectedFloor!=""){
            for (i in 1 until  floor_spinner.count  ){
                if (floor_spinner.getItemAtPosition(i).toString() == strSelectedFloor){
                    floor_spinner.setSelection(i)
                }
            }
        }
    }

    private fun goToBack(){
        val intent= Intent(this, TablesListActivity::class.java)
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