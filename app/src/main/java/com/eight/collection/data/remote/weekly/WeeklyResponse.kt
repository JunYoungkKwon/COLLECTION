package com.eight.collection.data.remote.weekly

import com.eight.collection.data.entities.Calendar
import com.eight.collection.ui.main.week.Diary
import com.google.gson.annotations.SerializedName

data class WeeklyResult(@SerializedName("weekly") val weekly: ArrayList<Diary>)
data class WeeklyResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: WeeklyResult?
)