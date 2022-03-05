package com.eight.collection.data.remote.modi

import com.eight.collection.data.entities.Write.*
import com.google.gson.annotations.SerializedName

data class ModiResult(@SerializedName("selected") val selected: selected?)
data class ModiResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ModiResult?
)