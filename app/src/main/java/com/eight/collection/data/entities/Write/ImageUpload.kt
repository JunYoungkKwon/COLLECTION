package com.eight.collection.data.entities.Write

import com.google.gson.annotations.SerializedName
import java.io.File

data class ImageUpload(
    @SerializedName("imageupload") val imageupload: File
)
