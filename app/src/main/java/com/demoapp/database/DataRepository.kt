package com.demoapp.database

import com.demoapp.adapter.CategoryViewRM
import com.demoapp.adapter.DataViewRM
import com.demoapp.adapterutils.RecyclerViewType
import com.demoapp.model.CategoryEntity
import com.demoapp.model.DataEntity
import com.demoapp.model.DataResponse
import com.demoapp.retrofit.RetrofitInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val dataSource: DataSource,
    private val mService: RetrofitInterface
) {

    fun getLocalData(): Flow<List<RecyclerViewType>> = runBlocking {

        val categories = dataSource.getAllCategories()
        val data = dataSource.getData()

        return@runBlocking categories.combine(data) { category, dataEntity ->
            category.flatMap {
                listOf(
                    CategoryViewRM(it.name)
                ).plus(
                    dataEntity.filter { dataEntity ->
                        dataEntity.categoryName == it.name
                    }.map { dataEntity ->
                        DataViewRM(
                            dataEntity.name, dataEntity.imageLink
                        )
                    }
                )
            }
        }
    }

    suspend fun getCategoryData() {
        val response = mService.getCategoryWithData("89359ade-c88d-4048-815d-2e1e652728f7")
        withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
                response.body()?.let { insertData(it) }
            }
        }
    }

    private suspend fun insertData(response: DataResponse) {
        if (!response.isNullOrEmpty()) {

            dataSource.insertAllCategories(response.map {
                CategoryEntity(name = it.category)
            })

            dataSource.insertAllSubCategories(
                response.map {
                    it.items.map { item ->
                        DataEntity(
                            name = item.name,
                            description = item.description,
                            price = item.price,
                            imageLink = item.imageLink,
                            productId = item.productId,
                            categoryName = it.category,
                        )
                    }
                }.flatten()
            )
        }
    }

}
