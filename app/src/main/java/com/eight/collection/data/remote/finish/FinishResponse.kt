package com.eight.collection.data.remote.finish

import com.eight.collection.ui.finish.*
import com.eight.collection.ui.main.week.Diary
import com.google.gson.annotations.SerializedName
import java.util.*

data class Finish(@SerializedName("ootdIdx") val ootdIdx: Int,@SerializedName("date") val date: Date, @SerializedName("lookname") val lookname: String, @SerializedName("lookpoint") val lookpoint: Int, @SerializedName("comment") val comment: String, @SerializedName("image") val image: ArrayList<Place>, @SerializedName("place") val place: ArrayList<Photo>, @SerializedName("weather") val weather: ArrayList<Weather>, @SerializedName("who") val who: ArrayList<Who>, @SerializedName("Top") val Top: ArrayList<Top>, @SerializedName("Bottom") val Bottom: ArrayList<Bottom>, @SerializedName("Shoes") val Shoes: ArrayList<Shoes>, @SerializedName("Etc") val Etc: ArrayList<Etc>)
data class FinishResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Finish?
)