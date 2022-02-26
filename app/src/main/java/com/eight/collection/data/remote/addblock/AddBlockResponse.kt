package com.eight.collection.data.remote.addblock

import com.google.gson.annotations.SerializedName

data class AddBlockResult(@SerializedName("added Clothes") val addedClothes: String?, @SerializedName("added Place") val addedPlace: String?, @SerializedName("added Weather") val addedWeather: String?, @SerializedName("added Who") val addedWho: String?)
data class AddBlockResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: AddBlockResult?
)