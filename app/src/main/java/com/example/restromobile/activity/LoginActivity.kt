package com.example.restromobile.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.restromobile.MainActivity
import com.example.restromobile.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity: AppCompatActivity(), View.OnClickListener {

    private var sharedPreferences:SharedPreferences?=null


    override fun onClick(v: View?) {
        val strUserName=edt_userName.text.toString().trim()
        val strPassword=edt_password.text.toString().trim()
        if (strUserName=="admin" && strPassword=="1234"){
            val editor= sharedPreferences?.edit()
            editor?.putString("UserCode","U_ad1234")
            editor?.commit()
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        else{
            Toast.makeText(this,"UserName/Password error!",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        sharedPreferences=getSharedPreferences("LoginUser", Context.MODE_PRIVATE)

        btn_login.setOnClickListener(this)
    }
}