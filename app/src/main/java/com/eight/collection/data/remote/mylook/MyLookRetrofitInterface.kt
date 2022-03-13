package com.eight.collection.data.remote.mylook

import com.eight.collection.data.entities.User
import com.eight.collection.data.remote.calendar.CalendarResponse
import okhttp3.internal.http.hasBody
import retrofit2.Call
import retrofit2.http.*

interface MyLookRetrofitInterface {

    @GET("app/mylook/mainpage/{lookpoint}")
    fun getMainMyLook(@Path("lookpoint") lookpoint : Int): Call<MyLookResponse>
}