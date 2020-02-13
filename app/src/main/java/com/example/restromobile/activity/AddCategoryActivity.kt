package com.example.restromobile.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.restromobile.R
import com.example.restromobile.database.AppDatabase
import com.example.restromobile.database.model.Categories
import kotlinx.android.synthetic.main.activity_add_category.*
import java.util.*

class AddCategoryActivity: AppCompatActivity(), View.OnClickListener {

    private var appDatabase:AppDatabase?=null

    override fun onClick(v: View?) {
        val strCategory=edt_category.text.toString().trim()
        if (strCategory==""){
            edt_category.error="Category is required!"
            edt_category.requestFocus()
            return
        }
        else{
            val category=Categories()
            val strCategoryID=UUID.randomUUID().toString()
            category.CategoryID=strCategoryID
            category.CategoryName=strCategory
            appDatabase!!.getCategoryDAO().add(category)
            Toast.makeText(this,"Save Success!", Toast.LENGTH_SHORT).show()
            edt_category.setText("")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)
        supportActionBar?.title="Categories"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        appDatabase= AppDatabase.getDatabase(this)

        btn_save_category.setOnClickListener(this)
    }

    private fun goToBack(){
        val intent= Intent(this, CategoryListActivity::class.java)
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
