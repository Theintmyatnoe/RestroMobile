package com.example.restromobile.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.restromobile.MainActivity
import com.example.restromobile.R
import kotlinx.android.synthetic.main.splash_activity.*

class SplashActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.splash_activity)

        val sharedPreferences=getSharedPreferences("LoginUser", Context.MODE_PRIVATE)

        Handler().postDelayed({
            if (sharedPreferences.contains("UserCode") && sharedPreferences.contains("UserCode")!=null){
                val intent= Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                val intent= Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        },5000)
        val animation= AnimationUtils.loadAnimation(this,R.anim.myanimation)
        splash_img.startAnimation(animation)

        val textAnim= AnimationUtils.loadAnimation(this,R.anim.blind_anim)
        splash_text.startAnimation(textAnim)
    }
}