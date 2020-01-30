package com.example.restromobile.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.restromobile.MainActivity
import com.example.restromobile.R
import com.example.restromobile.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class DineInActivity: AppCompatActivity() {
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dine_in)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title ="Dine In"

        tabLayout= findViewById<TabLayout>(R.id.tab_layout)
        viewPager= findViewById<ViewPager>(R.id.pager)

        tabLayout!!.addTab(tabLayout!!.newTab().setText("1st Floor"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("2nd Floor"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Outside"))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = ViewPagerAdapter(this, supportFragmentManager, tabLayout!!.tabCount)
        viewPager!!.adapter = adapter

        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

    }
    private fun goToMain(){
        val intent=Intent(this@DineInActivity,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        goToMain()
    }

    override fun onSupportNavigateUp(): Boolean {
        goToMain()
        return true
    }
}
