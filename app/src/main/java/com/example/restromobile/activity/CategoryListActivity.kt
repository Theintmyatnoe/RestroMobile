package com.example.restromobile.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.restromobile.MainActivity
import com.example.restromobile.R

class CategoryListActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        supportActionBar?.title="Categories List"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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
