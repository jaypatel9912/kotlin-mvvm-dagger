package com.demoapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.demoapp.model.CategoryEntity
import com.demoapp.model.DataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DataDuo {

    @Query("SELECT  * FROM category_entity")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    suspend fun insertAllCategories(categoryEntity: List<CategoryEntity>)

    @Query("SELECT  * FROM data_entity")
    fun getData(): Flow<List<DataEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    suspend fun insertAllSubCategories(dataEntity: List<DataEntity>)

}
