package com.eight.collection.data.remote.recieves3url

import retrofit2.Call
import retrofit2.http.*

interface ReceiveS3UrlRetrofitInterface {
    @GET("app/ootd/s3-authentication")
    fun receiveS3Url(): Call<ReceiveS3UrlResponse>
}