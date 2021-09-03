package com.demoapp.retrofit

import com.demoapp.model.DataResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface RetrofitInterface {
    @GET("v3/{value}")
    suspend fun getCategoryWithData(@Path("value") url: String): Response<DataResponse>
}
