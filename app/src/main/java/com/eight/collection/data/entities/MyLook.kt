package com.eight.collection.data.entities

import com.eight.collection.ui.main.mylook.MyLookOOTD
import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

data class MyLook(
    @SerializedName("lookpoint") val lookpoint: Int,
    @SerializedName("lastOOTDArr") val lastOOTDArr: ArrayList<MyLookOOTD>
)