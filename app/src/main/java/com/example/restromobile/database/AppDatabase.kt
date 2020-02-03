package com.example.restromobile.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.restromobile.database.dao.*
import com.example.restromobile.database.model.*

@Database(entities = [Tables::class,Categories::class,MenusItem::class,OrderTable::class,InvoiceTable::class],version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getTableDAO():TableDAO
    abstract fun getCategoryDAO():CategoryDAO
    abstract fun getMenuItemDAO():MenuItemDAO
    abstract fun getOrderDAO():OrderTablesDAO
    abstract fun getInvoiceDAO():InvoiceDAO
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "todo_database"
                ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}