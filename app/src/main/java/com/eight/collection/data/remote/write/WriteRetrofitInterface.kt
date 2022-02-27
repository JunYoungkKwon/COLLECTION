package com.eight.collection.data.remote.write

import com.eight.collection.data.entities.Write.Content
import com.eight.collection.data.entities.Write.Write
import com.eight.collection.data.remote.auth.AuthResponse
import retrofit2.Call
import retrofit2.http.*

interface WriteRetrofitInterface {
    @POST("app/ootd/last-register")
    fun write(@Query("mode") mode : Int,
                 @Body write : Write
    ): Call <WriteResponse>
}