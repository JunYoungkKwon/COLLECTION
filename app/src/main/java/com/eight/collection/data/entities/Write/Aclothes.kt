package com.eight.collection.data.entities.Write

import com.google.gson.annotations.SerializedName

data class Aclothes(
    @SerializedName("bigClass") val bigclass: String,
    @SerializedName("smallClass") val smallclass: String,
    @SerializedName("color") val color: String
)
