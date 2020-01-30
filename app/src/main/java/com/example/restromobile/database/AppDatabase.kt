package com.example.restromobile.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.restromobile.database.dao.CategoryDAO
import com.example.restromobile.database.dao.MenuItemDAO
import com.example.restromobile.database.dao.TableDAO
import com.example.restromobile.database.model.Categories
import com.example.restromobile.database.model.MenusItem
import com.example.restromobile.database.model.Tables

@Database(entities = [Tables::class,Categories::class,MenusItem::class],version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getTableDAO():TableDAO
    abstract fun getCategoryDAO():CategoryDAO
    abstract fun getMenuItemDAO():MenuItemDAO
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