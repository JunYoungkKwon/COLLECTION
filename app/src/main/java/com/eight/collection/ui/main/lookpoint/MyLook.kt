package com.eight.collection.ui.main.lookpoint

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class MyLook(
    var number : String? = "",
    var pointImg : Int? = null,
    var innerList: MutableList<Photo>
)

