package com.eight.collection.data.remote.calendar

import com.eight.collection.data.entities.User
import okhttp3.internal.http.hasBody
import retrofit2.Call
import retrofit2.http.*

interface CalendarRetrofitInterface {

    @GET("app/calendar/monthly")
    fun getMonth(): Call<CalendarResponse>

}