package com.eight.collection.data.entities.Write

import com.eight.collection.data.entities.Write.*
import com.google.gson.annotations.SerializedName
import java.util.*

data class Block(
    @SerializedName("Clothes") val clothes: Int,
    @SerializedName("PWW") val pww: Int,
    @SerializedName("content") val content: String
)
