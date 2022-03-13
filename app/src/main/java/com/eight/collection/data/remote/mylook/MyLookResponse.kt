package com.eight.collection.data.remote.mylook

import com.eight.collection.data.entities.Calendar
import com.eight.collection.data.entities.MyLook
import com.eight.collection.ui.main.mylook.MyLookOOTD
import com.google.gson.annotations.SerializedName

data class MyLookResult(@SerializedName("lastOOTDDetailArr") val lastOOTDDetailArr: MutableList<MyLookOOTD>, @SerializedName("lastOOTDArr") val lastOOTDArr: MutableList<MyLookOOTD>, @SerializedName("lookpoint") val lookpoint: String)
data class MyLookResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: MyLookResult?
)