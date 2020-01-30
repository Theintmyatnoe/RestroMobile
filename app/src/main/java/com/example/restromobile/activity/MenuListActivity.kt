package com.example.restromobile.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restromobile.MainActivity
import com.example.restromobile.R
import com.example.restromobile.adapter.MenuListAdapter
import com.example.restromobile.database.AppDatabase
import com.example.restromobile.database.model.MenusItem
import com.example.restromobile.delegate.SendIdDelegate
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_menu.category_spinner

class MenuListActivity:AppCompatActivity(), View.OnClickListener, SendIdDelegate {
    private var appDatabase:AppDatabase?=null
    private var menuList:List<MenusItem>?= arrayListOf()
    private var strcategoryList:ArrayList<String>?= arrayListOf()
    private var strCategoryName:String?=""

    override fun sendId(tableID: String) {
        val intent=Intent(this,AddMenuListActivity::class.java)
        intent.putExtra("MenuID",tableID)
        startActivity(intent)
        finish()
    }

    override fun sendDeleteID(tableID: String) {
        appDatabase!!.getMenuItemDAO().deleteByID(tableID)
        menuList=appDatabase!!.getMenuItemDAO().getAllMenuItem()
        val adapter = MenuListAdapter(this, menuList!!,this)
        menu_list_recycler.adapter=adapter
        tv_Total.text = "Total:"+ menuList!!.size
    }

    override fun onClick(v: View?) {
        val intent=Intent(this,AddMenuListActivity::class.java)
        startActivity(intent)
        finish()
    }

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        supportActionBar?.title="Menu List"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getCategoryListSpinner()

        appDatabase= AppDatabase.getDatabase(this)

        val linearLayoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        menu_list_recycler.layoutManager = linearLayoutManager

        category_spinner.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                strCategoryName= category_spinner.selectedItem.toString().trim()
                if (strCategoryName!=""){
                    getMenuList(strCategoryName)
                    val adapter = MenuListAdapter(this@MenuListActivity, menuList!!,this@MenuListActivity)
                    menu_list_recycler.adapter=adapter
                    tv_Total.text = "Total:"+ menuList!!.size
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }



        fb_add_menu.setOnClickListener(this)
    }

    private fun getCategoryListSpinner() {
        strcategoryList?.add("All")
        strcategoryList?.add("Seafood")
        strcategoryList?.add("Soup")
        strcategoryList?.add("Salad")
        strcategoryList?.add("Noodle")
        strcategoryList?.add("Drink")
        strcategoryList?.add("Chicken")
        strcategoryList?.add("fish")
        strcategoryList?.add("Rice")
        val spinnerAdapter =
            strcategoryList?.let {
                ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    it
                )
            }
        spinnerAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        category_spinner!!.adapter = spinnerAdapter
        category_spinner.background
            .setColorFilter(Color.parseColor("#e60023"), PorterDuff.Mode.SRC_ATOP)
    }
    private fun getMenuList(strCategoryName: String?) {
        if (strCategoryName=="All"){
            menuList=appDatabase!!.getMenuItemDAO().getAllMenuItem()
        }
        else{
            menuList= strCategoryName?.let { appDatabase!!.getMenuItemDAO().getAllMenuItemByCategory(it) }

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
