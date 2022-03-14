package com.eight.collection.data.remote.imageUpload

import com.google.gson.annotations.SerializedName

data class ImageUploadResult(@SerializedName("s3 imageUrl") val s3imageurl: String)
data class ImageUploadResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ImageUploadResult?
)