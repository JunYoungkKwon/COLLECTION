package com.eight.collection.data.remote.addblock

import retrofit2.Call
import retrofit2.http.*

interface AddBlockRetrofitInterface {
    @POST("app/ootd/new-block")
    fun addBlock(@Body content : String, @Query("Clothes") clothes : Int, @Query("PWW") pww : Int): Call<AddBlockResponse>
}