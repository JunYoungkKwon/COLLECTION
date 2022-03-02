package com.eight.collection.data.entities.Write

import com.eight.collection.data.entities.Write.*
import com.eight.collection.ui.writing.first.AddedClothes
import com.eight.collection.ui.writing.first.FixedClothes
import com.eight.collection.ui.writing.first.Image
import com.eight.collection.ui.writing.second.*
import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

data class Write(
    @SerializedName("mode") val mode: Int,
    @SerializedName("date") val date: String?,
    @SerializedName("lookname") val lookname: String?,
    @SerializedName("photoIs") val photois: Int,
    @SerializedName("image") val image: ArrayList<Image>?,
    @SerializedName("fClothes") val fclothes: ArrayList<FixedClothes>?,
    @SerializedName("aClothes") val aclothes: ArrayList<AddedClothes>?,
    @SerializedName("fPlace") val fplace: ArrayList<Int>,
    @SerializedName("aPlace") val aplace: ArrayList<String>,
    @SerializedName("fWeather") val fweather: ArrayList<Int>,
    @SerializedName("aWeather") val aweather: ArrayList<String>,
    @SerializedName("fWho") val fwho: ArrayList<Int>,
    @SerializedName("aWho") val awho: ArrayList<String>,
    @SerializedName("lookpoint") val lookpoint: Float,
    @SerializedName("comment") val comment: String
)
