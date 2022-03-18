package com.eight.collection.data.remote.match

import com.eight.collection.data.entities.Calendar
import com.eight.collection.data.entities.Diary
import com.google.gson.annotations.SerializedName

data class MatchResult(@SerializedName("match") val match: MutableList<Diary>)
data class MatchResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: MatchResult?
)