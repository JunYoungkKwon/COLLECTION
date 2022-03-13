package com.eight.collection.ui.main.mylook

import com.google.gson.annotations.SerializedName
import java.util.*


data class MyLookMain(
    var number : String? = "",
    var pointImg : Int? = null,
    var innerList: MutableList<MyLookOOTD>,

)

