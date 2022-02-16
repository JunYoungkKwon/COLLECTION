package com.eight.collection.data.remote.calendar

import com.eight.collection.data.entities.Calendar
import com.google.gson.annotations.SerializedName

data class CalendarResult(@SerializedName("monthly") val monthly: ArrayList<Calendar>)
data class CalendarResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: CalendarResult?
)