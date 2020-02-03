package com.example.restromobile.delegate

import com.example.restromobile.database.obj.ItemListObj

interface SendPositionForListDelegate {
    fun sendPosition(position: Int, price:String, qty:String, itemListObj: ItemListObj)
}