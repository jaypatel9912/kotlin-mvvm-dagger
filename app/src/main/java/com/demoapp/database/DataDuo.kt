package com.demoapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.demoapp.model.CategoryEntity
import com.demoapp.model.DataEntity


@Dao
interface DataDuo {
    @Query("SELECT  * FROM category_entity")
    fun getAllCategories(): List<CategoryEntity>

    @Insert
    fun insertCategories(categoryEntity: CategoryEntity)

    @Query("DELETE FROM category_entity")
    fun removeCategoryData()

    @Query("SELECT  * FROM data_entity")
    fun getData(): List<DataEntity>

    @Insert
    fun insertData(dataEntity: DataEntity)

    @Query("DELETE FROM data_entity")
    fun removeData()
}