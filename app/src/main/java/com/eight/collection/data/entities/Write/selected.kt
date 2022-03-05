package com.eight.collection.data.entities.Write

import com.eight.collection.data.entities.Cloth
import com.google.gson.annotations.SerializedName

data class selected(
    @SerializedName("ootdIdx") val ootdidx: Int,
    @SerializedName("date") val date: String,
    @SerializedName("lookname") val lookname: String,
    @SerializedName("lookpoint") val lookpoint: Float,
    @SerializedName("comment") val comment: String?,
    @SerializedName("image") val image: ArrayList<Image>?,
    @SerializedName("place") val place: ArrayList<String>?,
    @SerializedName("weather") val weather: ArrayList<String>?,
    @SerializedName("who") val who: ArrayList<String>?,
    @SerializedName("Top") val top: ArrayList<Cloth>?,
    @SerializedName("Bottom") val bottom: ArrayList<Cloth>?,
    @SerializedName("Shoes") val shoes: ArrayList<Cloth>?,
    @SerializedName("Etc") val etc: ArrayList<Cloth>?
    )
