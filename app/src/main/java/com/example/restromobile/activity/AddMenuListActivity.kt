package com.example.restromobile.activity

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.restromobile.R
import com.example.restromobile.database.AppDatabase
import com.example.restromobile.database.model.MenusItem
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.DexterError
import com.karumi.dexter.listener.PermissionRequestErrorListener
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_add_category.*
import kotlinx.android.synthetic.main.activity_add_menu_item.*
import kotlinx.android.synthetic.main.activity_add_table.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class AddMenuListActivity: AppCompatActivity(), View.OnClickListener {


    private var strcategoryList:ArrayList<String>?= arrayListOf()
    private var menusList:List<MenusItem>?= arrayListOf()
    private var strSelectedCategory:String?=""
    private var getMenusIDfromIntent:String?=null
    private var appDatabase:AppDatabase?=null
    private val GALLERY = 1
    private val CAMERA = 2
    private var pictureFilePath:String?=""
    private var myImgUri:String?=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_menu_item)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title ="Menus"
        requestMultiplePermissions()

        appDatabase= AppDatabase.getDatabase(this)

        getMenusIDfromIntent=intent.getStringExtra("MenuID")
        if (getMenusIDfromIntent!=null){
            menusList=appDatabase!!.getMenuItemDAO().getAllMenuItemByID(getMenusIDfromIntent!!)
            if (menusList!!.isNotEmpty()){
                for (menus in menusList!!){
                    if (menus.CategoryName.isNotEmpty() && menus.CategoryName!=""){
                        strSelectedCategory=menus.CategoryName
                    }
                    if (menus.Menu_ItemName.isNotEmpty() && menus.Menu_ItemName!=""){
                        edt_menu_name.setText(menus.Menu_ItemName)
                    }
                    if (menus.Price.isNotEmpty() && menus.Price!=""){
                        edt_price.setText(menus.Price)
                    }
                    if (menus.ImageUri.isNotEmpty() && menus.ImageUri!=""){
                        pictureFilePath=menus.ImageUri
                        menu_item_img.setImageURI(Uri.parse(menus.ImageUri))
                    }
                }
            }
        }

        getCategoryListSpinner()

        btn_save_menu.setOnClickListener(this)
        fb_camera.setOnClickListener(this)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY)
        {
            if (data != null)
            {
                val contentURI = data!!.data
                try
                {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    val path = saveImage(bitmap)

                    val imgFile = File(pictureFilePath)
                    myImgUri = Uri.fromFile(imgFile).toString()
                    menu_item_img.setImageURI(Uri.parse(myImgUri))


                }
                catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Failed Upload image!", Toast.LENGTH_SHORT).show()
                }

            }

        }
        else if (requestCode == CAMERA)
        {
            val thumbnail = data!!.extras!!.get("data") as Bitmap
            saveImage(thumbnail)
            val imgFile = File(pictureFilePath)
            myImgUri = Uri.fromFile(imgFile).toString()
            menu_item_img.setImageURI(Uri.parse(myImgUri))
        }
    }

    private fun getCategoryListSpinner(){
        strcategoryList?.add("Seafood")
        strcategoryList?.add("Soup")
        strcategoryList?.add("Salad")
        strcategoryList?.add("Noodle")
        strcategoryList?.add("Drink")
        strcategoryList?.add("Chicken")
        strcategoryList?.add("fish")
        strcategoryList?.add("Rice")
        val spinnerAdapter=
            strcategoryList?.let {
                ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    it
                )
            }
        spinnerAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        category_spinner!!.adapter=spinnerAdapter
        if (strSelectedCategory!=""){
            for (i in 1 until  category_spinner.count  ){
                if (category_spinner.getItemAtPosition(i).toString() == strSelectedCategory){
                    category_spinner.setSelection(i)
                }
            }
        }
    }
    override fun onClick(v: View?) {
        val id= v?.id
        if (id==R.id.btn_save_menu){
            val strCategory=category_spinner.selectedItem.toString().trim()
            val strMenuItem=edt_menu_name.text.toString().trim()
            val strPrice=edt_price.text.toString().trim()
            if (strMenuItem.isEmpty()){
                edt_menu_name.error="Menu Name is required!"
                edt_menu_name.requestFocus()
                return
            }
            else if (strPrice.isEmpty()){
                edt_price.error="Price is required!"
                edt_price.requestFocus()
                return
            }
            else{
                if (getMenusIDfromIntent!=null){
                    val menuItem= MenusItem()
                    menuItem.Menu_itemID= getMenusIDfromIntent!!
                    menuItem.CategoryName=strCategory
                    menuItem.Price=strPrice
                    menuItem.Menu_ItemName=strMenuItem
                    menuItem.ImageUri=pictureFilePath.toString()
                    appDatabase!!.getMenuItemDAO().update(menuItem)
                    Toast.makeText(this,"Save Success!", Toast.LENGTH_SHORT).show()
                    goToBack()
                }
                else{
                    val strMenuItemID=UUID.randomUUID().toString()
                    val menuItem= MenusItem()
                    menuItem.Menu_itemID=strMenuItemID
                    menuItem.CategoryName=strCategory
                    menuItem.Price=strPrice
                    menuItem.Menu_ItemName=strMenuItem
                    menuItem.ImageUri=pictureFilePath.toString()
                    appDatabase!!.getMenuItemDAO().add(menuItem)
                    Toast.makeText(this,"Save Success!", Toast.LENGTH_SHORT).show()
                    goToBack()
                }
            }
        }
        if (id==R.id.fb_camera){
            showPictureDialog()
        }
    }

    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select photo from gallery", "Capture photo from camera")
        pictureDialog.setItems(pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallary()
                1 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    }

    private fun choosePhotoFromGallary() {
        val galleryIntent = Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun takePhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
    }



    private fun saveImage(myBitmap: Bitmap):String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
            (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY)
        // have the object build the directory structure, if needed.
        Log.d("fee",wallpaperDirectory.toString())
        if (!wallpaperDirectory.exists())
        {

            wallpaperDirectory.mkdirs()
        }

        try
        {
            Log.d("heel",wallpaperDirectory.toString())
            val f = File(wallpaperDirectory, ((Calendar.getInstance()
                .timeInMillis).toString() + ".jpg"))
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(this,
                arrayOf(f.getPath()),
                arrayOf("image/jpeg"), null)
            fo.close()
            Log.d("TAG", "File Saved::--->" + f.absolutePath)

            pictureFilePath=f.absolutePath.toString()
            return f.absolutePath
        }
        catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }

    companion object {
        private val IMAGE_DIRECTORY = "/demonuts"
    }

    private fun requestMultiplePermissions() {
        Dexter.withActivity(this)
            .withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    // check if all permissions are granted
                    if (report.areAllPermissionsGranted()) {
                        Toast.makeText(
                            applicationContext,
                            "All permissions are granted by user!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    // check for permanent denial of any permission
                    if (report.isAnyPermissionPermanentlyDenied) {
                        // show alert dialog navigating to Settings
                        //openSettingsDialog();
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<com.karumi.dexter.listener.PermissionRequest>,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }


            }).withErrorListener {
                Toast.makeText(
                    applicationContext,
                    "Some Error! ",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .onSameThread()
            .check()
    }

    private fun goToBack(){
        val intent= Intent(this, MenuListActivity::class.java)
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
