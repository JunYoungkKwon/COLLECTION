package com.eight.collection.data.remote.auth

import com.google.gson.annotations.SerializedName

data class Auth(@SerializedName("nickname") val nickName: String,@SerializedName("userId") val userId: String, @SerializedName("jwt") val jwt: String, @SerializedName("name") val name: String)
data class AuthResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Auth?
)