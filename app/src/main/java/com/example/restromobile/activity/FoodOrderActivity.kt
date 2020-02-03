package com.example.restromobile.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restromobile.MainActivity
import com.example.restromobile.R
import com.example.restromobile.adapter.FoodOrderMenuGridAdapter
import com.example.restromobile.adapter.FoodOrderMenuListAdapter
import com.example.restromobile.database.AppDatabase
import com.example.restromobile.database.model.InvoiceTable
import com.example.restromobile.database.model.MenusItem
import com.example.restromobile.database.model.OrderTable
import com.example.restromobile.database.obj.ItemListObj
import com.example.restromobile.delegate.SendIdDelegate
import com.example.restromobile.delegate.SendPositionForListDelegate
import com.example.restromobile.delegate.SendRemovePositionDelegate
import kotlinx.android.synthetic.main.activity_food_order.*
import kotlinx.android.synthetic.main.activity_menu.category_spinner
import kotlinx.android.synthetic.main.alert_dialog_for_oder_item_list.view.*
import kotlinx.android.synthetic.main.custom_menu_bar.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FoodOrderActivity: AppCompatActivity(), SendIdDelegate, View.OnClickListener,
    SendPositionForListDelegate, SendRemovePositionDelegate {
    private var strcategoryList:ArrayList<String>?= arrayListOf()
    private var strCategoryName:String?=""
    private var pos:Int=-1

    private var totalPriceForOrder:String?=null
    var total:Int=0

    private var totalCount=0

    private var menuList:List<MenusItem>?= arrayListOf()
    private var orderMenuList:List<MenusItem>?= arrayListOf()
    private var itemList:ArrayList<ItemListObj>?= ArrayList()

    private var appDatabase: AppDatabase?=null
    private var tvtotal: TextView? =null

    private var getRemovePosition:Int=-1
    private var adapter:FoodOrderMenuListAdapter?=null

    private var mAlertDialog:AlertDialog?=null

    private var invoiceNo:String?=null
    private var currentdate:String?=null
    private var tableNo:String?=null
    private var custType:String?=null
    private var invoiceCount:Int=1
    private var sharedPreferences:SharedPreferences?=null


    override fun sendRemovePosition(position: Int,count:Int) {
        getRemovePosition=position
        mAlertDialog!!.dismiss()

        if (count>0){
            tv_notification.visibility=View.VISIBLE
            tv_notification.text = count.toString()
            showDialogForOrderList()
        }
        else{
            tv_notification.visibility=View.GONE
            btn_order.isEnabled=false
            mAlertDialog!!.dismiss()
        }

    }

    override fun sendPosition(position: Int, price: String, qty: String, itemListObj: ItemListObj) {
        pos=position
        val itemListObj1=ItemListObj(itemID = itemListObj.itemID,itemName = itemListObj.itemName,qty = qty,price = price)
        itemList!!.add(position,itemListObj1)
        total=0
        for (items in itemList!!){
            val prize=items.price
            val myPrize=prize.toInt()

            total+=myPrize

        }
        totalPriceForOrder=total.toString()
        tvtotal!!.text=totalPriceForOrder

    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_order)
        getCategoryListSpinner()
        supportActionBar?.hide()
        btn_order.isEnabled=false

        appDatabase= AppDatabase.getDatabase(this)

        sharedPreferences=getSharedPreferences("Invoice", Context.MODE_PRIVATE)
        if (sharedPreferences!!.contains("invoice_no")){
            invoiceCount= sharedPreferences!!.getString("invoice_no",null)!!.toInt()
            invoiceCount+=1
        }

        tableNo=intent.getStringExtra("TableID")
        custType=intent.getStringExtra("CustomerType")

        var date = Date()
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        currentdate = formatter.format(date)


        img_back.setOnClickListener{
            goToBack()
        }

        category_spinner.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val v = category_spinner.selectedView
                //Change the selected item's text color
                (v as TextView).setTextColor(Color.parseColor("#ffffff"))


                strCategoryName= category_spinner.selectedItem.toString().trim()
                if (strCategoryName!=""){
                    getMenuList(strCategoryName)
                    val adapter=
                        this@FoodOrderActivity.let { menuList?.let { it1 ->
                            FoodOrderMenuGridAdapter(
                                it,
                                it1
                            )
                        } }
                    grdImages.adapter=adapter
                    adapter?.sendItemIDForTable(this@FoodOrderActivity)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }
        btn_order.setOnClickListener(this)


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
            .setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_ATOP)
    }

    private fun getMenuList(strCategoryName: String?) {
        if (strCategoryName=="All"){
            menuList=appDatabase!!.getMenuItemDAO().getAllMenuItem()
        }
        else{
            menuList= strCategoryName?.let { appDatabase!!.getMenuItemDAO().getAllMenuItemByCategory(it) }

        }
    }
    override fun onClick(v: View?) {
        showDialogForOrderList()
    }

    override fun sendDeleteID(tableID: String) {

    }

    override fun sendId(tableID: String) {
        if (tableID.isNotEmpty() && tableID!=""){
            orderMenuList= appDatabase!!.getMenuItemDAO().getAllMenuItemByID(tableID)
            for (menusItem in orderMenuList!!){

                val totalPrice=menusItem.Price
                val price:Int=totalPrice.toInt()
//                total+=price

                val itemListObj1=ItemListObj(itemID = menusItem.Menu_itemID,itemName = menusItem.Menu_ItemName,price = menusItem.Price,qty = "1")
                itemList?.add(itemListObj1)
                Log.e("size", itemList?.size.toString())
                if (itemList?.isNotEmpty()!!){
                    tv_notification.visibility=View.VISIBLE
                    tv_notification.text = itemList!!.size.toString()
                    btn_order.isEnabled=true

                }

            }
//            totalPriceForOrder=total.toString()
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
    @SuppressLint("WrongConstant")
    private fun showDialogForOrderList(){
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.alert_dialog_for_oder_item_list, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Your Order List:")
        val orderListRecycler=mDialogView.findViewById<RecyclerView>(R.id.order_item_list_recycler)
        tvtotal=mDialogView.findViewById<TextView>(R.id.total_prize)

        if (itemList?.isNotEmpty()!!){
            totalCount=0
            for (items in itemList!!){

                val prize=items.price
                val myPrize=prize.toInt()

                totalCount+=myPrize

            }
            totalPriceForOrder=totalCount.toString()

            tvtotal!!.text=totalPriceForOrder

            val linearLayoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
            orderListRecycler.layoutManager = linearLayoutManager

            adapter = FoodOrderMenuListAdapter(this, itemList!!,this,this,this)
            orderListRecycler.adapter=adapter
            adapter!!.notifyDataSetChanged()
        }


        mAlertDialog = mBuilder.show()
        mDialogView.btn_add_order.setOnClickListener {
            if (itemList!!.isNotEmpty()){
                val invoice=InvoiceTable()
                var invoiceID=UUID.randomUUID().toString()
                invoice.invoiceID=invoiceID
                invoice.invoiceNo=invoiceCount.toString()
                invoice.date= this.currentdate.toString()
                invoice.totalPrice=totalPriceForOrder.toString()
                invoice.customerType= custType.toString()
                invoice.tableNo= this.tableNo.toString()
                appDatabase!!.getInvoiceDAO().add(invoice)
                var invoiceList= appDatabase!!.getInvoiceDAO().getAll()

                for (items in itemList!!){
                    val orders=OrderTable()
                    val orderID=UUID.randomUUID().toString()
                    orders.orderID=orderID
                    orders.invoiceID=invoiceID
                    orders.itemID=items.itemID
                    orders.qty=items.qty
                    orders.price=items.price
                    orders.status="waiting"
                    appDatabase!!.getOrderDAO().add(orders)

                }
                val orderList=appDatabase!!.getOrderDAO().getAll()
                Log.e("invoice_Size",invoiceList.size.toString())
                Log.e("invoice_Size",orderList.size.toString())
                Toast.makeText(this,"Saved Order",Toast.LENGTH_SHORT).show()
                val editor= sharedPreferences?.edit()
                editor?.putString("invoice_no", invoiceCount.toString())
                editor?.commit()
            }
            val intent= Intent(this, OrderListActivity::class.java)
            startActivity(intent)
            finish()

            mAlertDialog!!.dismiss()

        }
    }



}
