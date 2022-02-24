package com.eight.collection.data.entities.Write

import com.eight.collection.data.entities.Write.*
import com.google.gson.annotations.SerializedName
import java.util.*

data class Write(
    @SerializedName("date") val date: Date,
    @SerializedName("lookname") val lookname: String,
    @SerializedName("photoIs") val photois: Int,
    @SerializedName("image") val image: ArrayList<Image>,
    @SerializedName("fClothes") val fclothes: ArrayList<Fclothes>,
    @SerializedName("aClothes") val aclothes: ArrayList<Aclothes>,
    @SerializedName("fPlace") val fplace: ArrayList<Fplace>,
    @SerializedName("aPlace") val aplace: ArrayList<Aplace>,
    @SerializedName("fWeather") val fweather: ArrayList<Fweather>,
    @SerializedName("aWeather") val aweather: ArrayList<Aweather>,
    @SerializedName("fWho") val fwho: ArrayList<Fwho>,
    @SerializedName("aWho") val awho: ArrayList<Awho>,
    @SerializedName("lookpoint") val lookpoint: Int,
    @SerializedName("comment") val comment: String

)
