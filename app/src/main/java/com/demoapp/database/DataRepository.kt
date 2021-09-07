package com.demoapp.database

import com.demoapp.adapter.CategoryViewRM
import com.demoapp.adapter.DataViewRM
import com.demoapp.adapterutils.RecyclerViewType
import com.demoapp.model.CategoryEntity
import com.demoapp.model.DataEntity
import com.demoapp.model.DataResponse
import com.demoapp.retrofit.RetrofitInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val dataDuo: DataDuo,
    private val mService: RetrofitInterface
) {

    fun getLocalData(): Flow<List<RecyclerViewType>> = runBlocking {

        val categories = dataDuo.getAllCategories()
        val data = dataDuo.getData()

        return@runBlocking categories.combine(data) { category, dataEntity ->
            category.flatMap {
                listOf(
                    CategoryViewRM(it.name)
                ).plus(
                    dataEntity.filter { dataEntity ->
                        dataEntity.category_name == it.name
                    }.map { dataEntity ->
                        DataViewRM(
                            dataEntity.name, dataEntity.image_link
                        )
                    }
                )
            }
        }
    }

    suspend fun getCategoryData(): DataResponse? {
        val response = mService.getCategoryWithData("89359ade-c88d-4048-815d-2e1e652728f7")
        if (response.isSuccessful) {
            return response.body()
        }
        return null
    }

    suspend fun insertData(response: DataResponse) {
        if (!response.isNullOrEmpty()) {

            dataDuo.insertAllCategories(response.map {
                CategoryEntity(name = it.category)
            })

            response.map {
                dataDuo.insertAllSubCategories(
                    it.items.map { item ->
                        DataEntity(
                            name = item.name,
                            description = item.description,
                            price = item.price,
                            image_link = item.image_link,
                            product_id = item.product_id,
                            category_name = it.category,
                        )
                    })
            }
        }
    }

}
