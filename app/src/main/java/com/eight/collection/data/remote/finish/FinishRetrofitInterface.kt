package com.eight.collection.data.remote.finish

import com.eight.collection.data.entities.Calendar
import com.eight.collection.data.entities.User
import okhttp3.internal.http.hasBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface FinishRetrofitInterface {
    @GET("app/ootd/complete?date=")
    fun getFinish(@Query("date") date : Date): Call<FinishResponse>

}