package com.eight.collection.data.entities

import com.google.gson.annotations.SerializedName
import java.util.*

data class Diary(
    @SerializedName("ootdIdx") val ootdIdx: Int? = null,
    @SerializedName("date") val date: Date,
    @SerializedName("lookpoint") var point : Int? = null,
    @SerializedName("imageUrl") var coverImg : String? = null,
    @SerializedName("imageCnt") var imgCount: Int? = null,
    @SerializedName("place") var placeList: MutableList<String>,
    @SerializedName("weather") var weatherList: MutableList<String>,
    @SerializedName("who") var whoList: MutableList<String>,
    @SerializedName("Top") var topList: MutableList<Cloth>,
    @SerializedName("Bottom") var bottomList: MutableList<Cloth>,
    @SerializedName("Shoes") var shoesList: MutableList<Cloth>,
    @SerializedName("Etc") var etcList: MutableList<Cloth>,
)
