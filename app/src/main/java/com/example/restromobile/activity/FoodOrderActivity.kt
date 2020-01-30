package com.example.restromobile.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restromobile.MainActivity
import com.example.restromobile.R
import com.example.restromobile.adapter.FoodOrderMenuListAdapter
import com.example.restromobile.database.AppDatabase
import com.example.restromobile.database.model.MenusItem
import com.example.restromobile.delegate.SendIdDelegate
import kotlinx.android.synthetic.main.activity_menu.*

class FoodOrderActivity: AppCompatActivity(), SendIdDelegate {
    override fun sendId(tableID: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sendDeleteID(tableID: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var strcategoryList:ArrayList<String>?= arrayListOf()
    private var strCategoryName:String?=""

    private var menuList:List<MenusItem>?= arrayListOf()

    private var appDatabase: AppDatabase?=null


    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_order)
        getCategoryListSpinner()
        supportActionBar?.title="Food Order"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        appDatabase= AppDatabase.getDatabase(this)

        val linearLayoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        menu_list_recycler.layoutManager = linearLayoutManager

        category_spinner.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                strCategoryName= category_spinner.selectedItem.toString().trim()
                if (strCategoryName!=""){
                    getMenuList(strCategoryName)
                    val adapter = FoodOrderMenuListAdapter(this@FoodOrderActivity, menuList!!,this@FoodOrderActivity)
                    menu_list_recycler.adapter=adapter
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }

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
