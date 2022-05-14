package com.eight.collection.data.remote.match

import com.eight.collection.data.entities.Suggest
import com.google.gson.annotations.SerializedName

data class SuggestTagResult(@SerializedName("suggestion") val suggestion: ArrayList<Suggest>?)
data class SuggestTagResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: SuggestTagResult?
)