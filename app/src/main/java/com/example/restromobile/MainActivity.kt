package com.example.restromobile

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.ViewPager
import com.example.restromobile.activity.*
import com.example.restromobile.adapter.SlidingImage_Adapter
import com.google.android.material.navigation.NavigationView
import com.viewpagerindicator.CirclePageIndicator
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        val id=p0.itemId
        if (id==R.id.item_table){
            val intent=Intent(this,TablesListActivity::class.java)
            startActivity(intent)
            finish()
        }
        if (id==R.id.item_menu){
            val intent=Intent(this, MenuListActivity::class.java)
            startActivity(intent)
            finish()
        }
        if (id==R.id.item_add_category){
            val intent=Intent(this, CategoryListActivity::class.java)
            startActivity(intent)
            finish()
        }
        if (id==R.id.item_home){
            val intent=Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        if (id==R.id.item_logout){
            val sharedPreferences=getSharedPreferences("LoginUser", Context.MODE_PRIVATE)
            val editor=sharedPreferences.edit()
            editor.clear()
            editor.commit()
            val intent=Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (mtoogle?.onOptionsItemSelected(item)!!){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private var imageModelArrayList: ArrayList<ImageModel>? = null
    private var mtoogle:ActionBarDrawerToggle?=null

    private val myImageList = intArrayOf(R.drawable.iconfinder_yoghurt_49747, R.drawable.iconfinder_blue_51196, R.drawable.iconfinder_mug_8520, R.drawable.ic_tomato, R.drawable.hambger)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mydrawer= findViewById<DrawerLayout>(R.id.my_drawer)
        mtoogle=ActionBarDrawerToggle(this,mydrawer,R.string.open,R.string.close)
        mydrawer.addDrawerListener(mtoogle!!)
        mtoogle!!.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navigationView=findViewById<NavigationView>(R.id.mynav)
        val haderView=navigationView.getHeaderView(0)
        navigationView.setNavigationItemSelectedListener(this)


        imageModelArrayList = ArrayList()
        imageModelArrayList = populateList()

        init()

        ll_dine_in.setOnClickListener{
            val intent=Intent(this,DineInActivity::class.java)
            startActivity(intent)
            finish()
        }
        ll_order.setOnClickListener{
            val intent=Intent(this,OrderListActivity::class.java)
            startActivity(intent)
            finish()
        }
        ll_invoice.setOnClickListener{
            val intent=Intent(this,InvoiceListActivity::class.java)
            startActivity(intent)
            finish()
        }
        ll_carryout.setOnClickListener{
            val intent=Intent(this,FoodOrderActivity::class.java)
            intent.putExtra("CustomerType","Take Away")
            startActivity(intent)
            finish()
        }

    }

    private fun populateList(): ArrayList<ImageModel> {

        val list = ArrayList<ImageModel>()

        for (i in 0..4) {
            val imageModel = ImageModel()
            imageModel.setImage_drawables(myImageList[i])
            list.add(imageModel)
        }

        return list
    }

    private fun init() {

        mPager = findViewById<ViewPager>(R.id.pager)
        mPager!!.adapter = SlidingImage_Adapter(this@MainActivity, this.imageModelArrayList!!)

        val indicator = findViewById<CirclePageIndicator>(R.id.indicator)

        indicator.setViewPager(mPager)

        val density = resources.displayMetrics.density

        //Set circle indicator radius
        indicator.radius = 4 * density

        NUM_PAGES = imageModelArrayList!!.size

        // Auto start of viewpager
        val handler = Handler()
        val Update = Runnable {
            if (currentPage == NUM_PAGES) {
                currentPage = 0
            }
            mPager!!.setCurrentItem(currentPage++, true)
        }
        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(Update)
            }
        }, 3000, 3000)

        // Pager listener over indicator
        indicator.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageSelected(position: Int) {
                currentPage = position

            }

            override fun onPageScrolled(pos: Int, arg1: Float, arg2: Int) {

            }

            override fun onPageScrollStateChanged(pos: Int) {

            }
        })
    }

    companion object {

        private var mPager: ViewPager? = null
        private var currentPage = 0
        private var NUM_PAGES = 0
    }
}
