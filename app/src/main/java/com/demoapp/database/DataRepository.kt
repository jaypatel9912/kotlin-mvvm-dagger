package com.demoapp.database

import android.util.Log
import com.demoapp.model.CategoryEntity
import com.demoapp.model.DataEntity
import com.demoapp.model.DataResponse
import com.demoapp.retrofit.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback

class DataRepository(var dataDuo: DataDuo, val mService: RetrofitInterface) {

    fun getLocalData(result: (List<DataEntity>) -> Unit) {
        var resultList = arrayListOf<DataEntity>()
        val categories = dataDuo.getAllCategories()
        val dataList = dataDuo.getData()

        categories.forEach { category ->
            resultList.add(
                DataEntity(
                    name = "",
                    description = "",
                    price = 0.0,
                    image_link = "",
                    product_id = "",
                    category_name = category.name,
                    is_category = true
                )
            )

            resultList.addAll(dataList.filter {
                it.category_name.equals(
                    category.name,
                    ignoreCase = true
                )
            })
        }

        result.invoke(resultList)
    }

    fun getCategoryData(result: (Boolean) -> Unit) {
        val call: Call<DataResponse> =
            mService.getCategoryWithData("89359ade-c88d-4048-815d-2e1e652728f7")


        call.enqueue(object : Callback<DataResponse> {
            override fun onResponse(
                call: Call<DataResponse>,
                response: retrofit2.Response<DataResponse>
            ) {
                Log.d("", "")
                response.body()?.let { insertCategoryData(it) }
                result.invoke(true)
            }

            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                Log.d("", "")
                result.invoke(false)
            }
        })
    }

    fun insertCategoryData(response: DataResponse) {
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