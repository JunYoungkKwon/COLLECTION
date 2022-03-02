package com.eight.collection.data.entities

import com.google.gson.annotations.SerializedName
import java.util.*

data class Calendar(
    @SerializedName("date") val date: Date,
    @SerializedName("lookpoint") var lookpoint: Int,
)

