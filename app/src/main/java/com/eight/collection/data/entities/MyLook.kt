package com.eight.collection.data.entities

import com.google.gson.annotations.SerializedName
import java.util.*

data class MyLook(
    @SerializedName("lookpoint") val lookpoint: Int,
    @SerializedName("ootdIdx") val ootdIdx: Int,
    @SerializedName("thumbnailIdx") val thumbnailIdx: Int,
    @SerializedName("photoIs") val photoIs: Int,
    @SerializedName("date") val date: Date,
    @SerializedName("imageUrl") val imageUrl: String,

)