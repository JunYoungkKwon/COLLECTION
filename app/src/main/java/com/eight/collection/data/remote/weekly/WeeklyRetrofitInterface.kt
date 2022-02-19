package com.eight.collection.data.remote.weekly

import com.eight.collection.data.entities.User
import okhttp3.internal.http.hasBody
import retrofit2.Call
import retrofit2.http.*

interface WeeklyRetrofitInterface {

    @GET("app/calendar/weekly")
    fun getWeek(): Call<WeeklyResponse>

}