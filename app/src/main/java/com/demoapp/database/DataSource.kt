package com.demoapp.database

import com.demoapp.model.CategoryEntity
import com.demoapp.model.DataEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataSource @Inject constructor(
    private val dataDuo: DataDuo,
) {

    suspend fun insertAllCategories(res: List<CategoryEntity>) {
        dataDuo.insertAllCategories(res)
    }

    fun getAllCategories(): Flow<List<CategoryEntity>> = dataDuo.getAllCategories()

    suspend fun insertAllSubCategories(res: List<DataEntity>) {
        dataDuo.insertAllSubCategories(res)
    }

    fun getData(): Flow<List<DataEntity>> = dataDuo.getData()

}
