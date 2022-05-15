package com.eight.collection.data.remote.match

import com.eight.collection.data.entities.Calendar
import com.eight.collection.data.entities.Diary
import com.eight.collection.ui.main.match.LastTag
import com.google.gson.annotations.SerializedName

data class DeleteTagResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String
)