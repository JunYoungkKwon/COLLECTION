package com.eight.collection.data.remote.getaddedblock

import com.eight.collection.data.entities.Write.*
import com.google.gson.annotations.SerializedName

data class GetAddedBlockResult(@SerializedName("aPlace") val aplace: ArrayList<String>, @SerializedName("aWeather") val aweather : ArrayList<String>, @SerializedName("aWho") val awho : ArrayList<String>, @SerializedName("aTop") val atop : ArrayList<String>, @SerializedName("aBottom") val abottom : ArrayList<String>, @SerializedName("aShoes") val ashoes : ArrayList<String>, @SerializedName("aEtc") val aetc : ArrayList<String>)
data class GetAddedBlockResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: GetAddedBlockResult?
)