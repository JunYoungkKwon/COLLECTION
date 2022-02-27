package com.eight.collection.ui.writing.first

import android.net.Uri
import java.io.Serializable
import java.net.URI

data class Image(
    var uri : Uri,
    var thumnail : Int
    ) : Serializable
