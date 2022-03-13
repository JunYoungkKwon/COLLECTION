package com.eight.collection.ui.main.mylook

import com.google.gson.annotations.SerializedName
import java.util.*


data class MyLookOOTD(
    @SerializedName("ootdIdx") val ootdIdx: Int,
    @SerializedName("date") val date: Date,
    @SerializedName("photoIs") val photoIs: Int,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("thumbnail") val thumbnailIdx: Int,
)

