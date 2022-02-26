package com.eight.collection.data.remote.deleteblock

import com.google.gson.annotations.SerializedName

data class Auth(@SerializedName("nickname") val nickName: String,@SerializedName("userId") val userId: String, @SerializedName("jwt") val jwt: String)
data class DeleteblockResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Auth?
)