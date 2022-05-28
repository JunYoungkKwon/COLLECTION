package com.eight.collection.ui.main.match

import com.google.gson.annotations.SerializedName
import java.util.*


data class LastTag(
    @SerializedName("content") var text: String? = "",
    @SerializedName("color") var color: String? = "",
    @SerializedName("index") var index: Int? = 0,
    @SerializedName("isdefault") var isdefault: Boolean? = false,
    @SerializedName("focus") var focus : Boolean = false
)

