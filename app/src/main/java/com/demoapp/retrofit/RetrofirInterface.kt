package com.demoapp.retrofit

import com.demoapp.model.DataResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface RetrofitInterface {
    @GET("v3/{value}")
    fun getCategoryWithData(@Path("value") url: String): Call<DataResponse>
}