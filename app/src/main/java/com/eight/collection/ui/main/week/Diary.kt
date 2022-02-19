package com.eight.collection.ui.main.week

import com.eight.collection.ui.main.lookpoint.Photo
import com.google.gson.annotations.SerializedName
import java.util.*

data class Diary(

    @SerializedName("ootdIdx") val ootdIdx: Int? = null,
    @SerializedName("date") val date: Date,
    @SerializedName("lookpoint") var point : Int? = null,
    //@SerializedName("lookpoint") var date : String? = "",
    @SerializedName("imageUrl") var coverImg : Int? = null,
    @SerializedName("lookpoint") var imgCount: Int? = null,
    @SerializedName("place") var placeList: MutableList<Mood>,
    @SerializedName("weather") var weatherList: MutableList<Mood>,
    @SerializedName("who") var whoList: MutableList<Mood>,
    @SerializedName("Top") var topList: MutableList<Top>,
    @SerializedName("Bottom") var bottomList: MutableList<Bottom>,
    @SerializedName("Shoes") var shoesList: MutableList<Shoes>,
    @SerializedName("Etc") var etcList: MutableList<Etc>
)
