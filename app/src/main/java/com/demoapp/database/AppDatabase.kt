package com.demoapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.demoapp.model.CategoryEntity
import com.demoapp.model.DataEntity

@Database(entities = [CategoryEntity::class, DataEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getDataDao() : DataDuo
    companion object {
        private var dbInstance: AppDatabase? = null
        fun getDatabaseInstance(context: Context): AppDatabase {
            if (dbInstance == null) {
                dbInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_db"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return dbInstance!!
        }
    }

}
