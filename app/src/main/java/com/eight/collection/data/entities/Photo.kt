package com.eight.collection.data.entities

import com.google.gson.annotations.SerializedName


data class Photo(
    @SerializedName("imageUrl") var imageUrl : String? = "",
    @SerializedName("thumbnail") var thumbnail : Int? = null,
)
