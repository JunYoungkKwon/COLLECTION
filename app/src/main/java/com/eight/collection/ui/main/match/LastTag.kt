package com.eight.collection.ui.main.match

import com.google.gson.annotations.SerializedName
import java.util.*


data class LastTag(
    @SerializedName("content") val text: String? = "",
    @SerializedName("color") val color: String? = "",
    @SerializedName("index") val index: Int? = 0,

)

