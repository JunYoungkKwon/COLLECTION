package com.eight.collection.data.remote.mylook

import com.eight.collection.data.entities.User
import com.eight.collection.data.remote.calendar.CalendarResponse
import okhttp3.internal.http.hasBody
import retrofit2.Call
import retrofit2.http.*

interface MyLookRetrofitInterface {

    @GET("app/user/check-nickname")
    fun getMainMyLook(@Query("nickname") nickname : Int): Call<MyLookResponse>


    @GET("app/calendar/monthly")
    fun getMonth(): Call<CalendarResponse>

}