package com.eight.collection.data.entities.Write

import com.google.gson.annotations.SerializedName
import java.net.URI

data class Fclothes(
    @SerializedName("index") val index: Int,
    @SerializedName("color") val color: String
)
