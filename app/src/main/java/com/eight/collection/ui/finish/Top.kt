package com.eight.collection.ui.finish

import com.google.gson.annotations.SerializedName

data class Top(
    @SerializedName("smallClass") var cloth : String? = "",
    @SerializedName("color") var color : String? = ""
)
