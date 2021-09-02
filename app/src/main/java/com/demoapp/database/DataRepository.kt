package com.demoapp.database

import com.demoapp.model.CategoryEntity
import com.demoapp.model.DataEntity
import com.demoapp.model.DataResponse
import com.demoapp.retrofit.RetrofitInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val dataDuo: DataDuo,
    private val mService: RetrofitInterface
) {

    suspend fun getLocalData(): List<DataEntity> {
        val categories = dataDuo.getAllCategories()
        val dataList = dataDuo.getData()

        return categories.flatMap {
            listOf(
                DataEntity(
                    name = "",
                    description = "",
                    price = 0.0,
                    image_link = "",
                    product_id = "",
                    category_name = it.name,
                    is_category = true
                )
            ).plus(dataList.filter { dataEntity ->
                dataEntity.category_name == it.name
            })
        }
    }

    suspend fun getCategoryData() {
        val response = mService.getCategoryWithData("89359ade-c88d-4048-815d-2e1e652728f7")

        withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
                response.body()?.let { insertCategoryData(it) }
            }
        }
    }

    private suspend fun insertCategoryData(response: DataResponse) {
        if (!response.isNullOrEmpty()) {
            dataDuo.removeCategoryData()
            dataDuo.removeData()

            response.forEach { category ->
                dataDuo.insertCategories(CategoryEntity(name = category.category))

                category.items.forEach {
                    dataDuo.insertData(
                        DataEntity(
                            name = it.name,
                            description = it.description,
                            price = it.price,
                            image_link = it.image_link,
                            product_id = it.product_id,
                            category_name = category.category,
                            is_category = false
                        )
                    )
                }
            }
        }
    }
}