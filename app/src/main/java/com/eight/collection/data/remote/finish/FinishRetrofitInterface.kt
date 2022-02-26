package com.eight.collection.data.remote.finish

import com.eight.collection.data.entities.Calendar
import com.eight.collection.data.entities.User
import okhttp3.internal.http.hasBody
import retrofit2.Call
import retrofit2.http.*
import java.time.LocalDate
import java.util.*

interface FinishRetrofitInterface {
    @GET("app/ootd/complete")
    fun getFinish(@Query("date") date : String): Call<FinishResponse>

}