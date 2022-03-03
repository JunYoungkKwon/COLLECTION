package com.eight.collection.data.remote.getaddedblock

import retrofit2.Call
import retrofit2.http.*

interface GetAddedBlockRetrofitInterface {
    @GET("app/ootd/default-block")
    fun getAddedBlock(): Call<GetAddedBlockResponse>
}