package com.eight.collection.ui.finish

import com.google.gson.annotations.SerializedName

data class Bottom(
    @SerializedName("smallClass") var cloth : String? = "",
    @SerializedName("color") var color : String? = ""
)
