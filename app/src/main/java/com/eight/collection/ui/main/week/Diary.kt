package com.eight.collection.ui.main.week

import com.eight.collection.ui.main.lookpoint.Photo

data class Diary(
//    var ratingPoint : Int? = null,
//    var color : String? = "",
    var coverImg : Int? = null,
    var imgCount: Int? = null,
    var moodList: MutableList<Mood>,
    var topList: MutableList<Top>,
    var bottomList: MutableList<Bottom>,
    var shoesList: MutableList<Shoes>,
    var etcList: MutableList<Etc>
)
