package com.eight.collection.data.remote.match

import com.eight.collection.data.entities.User
import com.eight.collection.data.remote.calendar.CalendarResponse
import okhttp3.internal.http.hasBody
import retrofit2.Call
import retrofit2.http.*

interface MatchRetrofitInterface {

    @GET("app/search/{PWWC}")
    fun getMatch(@Path("PWWC") pwwc : Int,
                 @Query("keyword1") keyword1 : String,
                 @Query("keyword2") keyword2 : String,
                 @Query("color1") color1 : String,
                 @Query("color2") color2 : String,
                 @Query("startAt") startAt : String,
                 @Query("endAt") endAt : String): Call<MatchResponse>

}