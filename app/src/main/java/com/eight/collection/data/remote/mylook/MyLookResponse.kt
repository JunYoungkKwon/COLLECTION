package com.eight.collection.data.remote.mylook

import com.eight.collection.data.entities.Calendar
import com.eight.collection.data.entities.MyLook
import com.google.gson.annotations.SerializedName

data class MyLookResult(@SerializedName("lastOOTDArr") val lastOOTDArr: ArrayList<MyLook>, @SerializedName("lookpoint") val lookpoint: Int)
data class MyLookResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: MyLookResult?
)