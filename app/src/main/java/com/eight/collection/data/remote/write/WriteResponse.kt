package com.eight.collection.data.remote.write

import com.google.gson.annotations.SerializedName

data class WriteResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String
)