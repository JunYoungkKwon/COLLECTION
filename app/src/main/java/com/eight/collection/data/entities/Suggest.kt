package com.eight.collection.data.entities

import com.eight.collection.data.entities.Cloth
import com.google.gson.annotations.SerializedName

data class Suggest(
    @SerializedName("smallClass") val smallclass: String?,
    @SerializedName("place") val place: String?,
    @SerializedName("weather") val weather: String?,
    @SerializedName("who") val who: String?
    )
