package com.eight.collection.ui.finish

import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("imageUrl") var imageUrl : String? = "",
    @SerializedName("thumbnail") var thumbnail : Int? = null,
)
