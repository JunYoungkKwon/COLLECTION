package com.eight.collection.data.remote.modi

import retrofit2.Call
import retrofit2.http.*

interface ModiRetrofitInterface {
    @GET("app/ootd/modi")
    fun modi(@Query("date") date : String): Call<ModiResponse>
}