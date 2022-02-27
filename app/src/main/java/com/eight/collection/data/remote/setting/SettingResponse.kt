package com.eight.collection.data.remote.setting

import com.google.gson.annotations.SerializedName

data class SettingResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
)