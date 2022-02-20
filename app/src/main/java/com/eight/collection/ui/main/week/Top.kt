package com.eight.collection.ui.main.week

import com.google.gson.annotations.SerializedName

data class Top(
    @SerializedName("smallClass") var cloth : String? = "",
    @SerializedName("color") var color : String? = ""
)
