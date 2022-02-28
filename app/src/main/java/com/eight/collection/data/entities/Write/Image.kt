package com.eight.collection.data.entities.Write

import com.google.gson.annotations.SerializedName
import java.net.URI
import java.util.*

data class Image(
    @SerializedName("imageUrl") val imageurl: String,
    @SerializedName("thumbnail") val thumbnail: Int
)