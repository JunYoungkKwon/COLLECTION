package com.eight.collection.data.remote.match

import com.eight.collection.data.entities.User
import com.eight.collection.data.entities.Write.Content
import com.eight.collection.data.remote.calendar.CalendarResponse
import com.eight.collection.data.remote.deleteblock.DeleteBlockResponse
import okhttp3.internal.http.hasBody
import retrofit2.Call
import retrofit2.http.*

interface MatchRetrofitInterface {
    @GET("app/search/{PWWC}")
    fun getMatch(@Path("PWWC") PWWC : Int,
                 @Query("keyword1") keyword1 : String,
                 @Query("keyword2") keyword2 : String,
                 @Query("color1") color1 : String,
                 @Query("color2") color2 : String,
                 @Query("startAt") startAt : String,
                 @Query("endAt") endAt : String): Call<MatchResponse>

    @GET("app/search/mainpage/{PWWC}")
    fun getLastTag(@Path("PWWC") PWWC : Int): Call<LastTagResponse>


    @PATCH("/app/search/deletion/{PWWC}")
    fun deleteBlock(@Path("PWWC") PWWC : Int,
                    @Query("type") type : Int,
                    @Body content : Content?
    ): Call<DeleteTagResponse>

    @GET("app/search/suggestion/{PWWC}")
    fun suggestTag(@Path("PWWC") PWWC : Int,
                   @Query("keyword1") keyword1 : String) : Call<SuggestTagResponse>

}