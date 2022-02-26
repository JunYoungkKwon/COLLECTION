package com.eight.collection.data.remote.finish

import com.eight.collection.ui.finish.*
import com.eight.collection.ui.main.week.Diary
import com.google.gson.annotations.SerializedName
import java.util.*

data class Finish(@SerializedName("ootdIdx") val ootdIdx: Int,@SerializedName("date") val date: Date, @SerializedName("lookname") val lookname: String, @SerializedName("lookpoint") val lookpoint: Int, @SerializedName("comment") val comment: String, @SerializedName("image") val image: ArrayList<Photo>, @SerializedName("place") val place: ArrayList<String>, @SerializedName("weather") val weather: ArrayList<String>, @SerializedName("who") val who: ArrayList<String>, @SerializedName("Top") val Top: ArrayList<Cloth>, @SerializedName("Bottom") val Bottom: ArrayList<Cloth>, @SerializedName("Shoes") val Shoes: ArrayList<Cloth>, @SerializedName("Etc") val Etc: ArrayList<Cloth>)
data class FinishResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Finish?
)