package com.eight.collection.data.remote.recieves3url

import com.google.gson.annotations.SerializedName

data class ReceiveS3UrlResult(@SerializedName("preSignedUrl") val presignedurl: String)
data class ReceiveS3UrlResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ReceiveS3UrlResult?
)