package com.eight.collection.data.remote.addblock

import com.eight.collection.data.entities.Write.Content
import com.eight.collection.data.remote.auth.AuthResponse
import retrofit2.Call
import retrofit2.http.*

interface AddBlockRetrofitInterface {
    @POST("app/ootd/new-block")
    fun addBlock(@Query("Clothes") clothes : Int,
                 @Query("PWW") pww : Int,
                 @Body content : Content
    ): Call<AddBlockResponse>
}