package com.eight.collection.ui.main.week

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eight.collection.R
import com.eight.collection.data.entities.Diary
import com.eight.collection.databinding.ItemWeekDiaryBinding
import com.eight.collection.databinding.ItemWeekDiaryMoodBinding

class MoodRVAdapter(val MoodList: MutableList<String>) : RecyclerView.Adapter<MoodRVAdapter.ViewHolder>() {

    var arrayList : MutableList<String> = mutableListOf()
    private  val keywordlist = mutableListOf<String>()
    private var keywordlistcopy = mutableListOf<String>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MoodRVAdapter.ViewHolder {
        val binding: ItemWeekDiaryMoodBinding = ItemWeekDiaryMoodBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return  ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MoodRVAdapter.ViewHolder, position: Int) {
        holder.bind(MoodList[position])
    }

    //데이터의 크기를 알려줌 => 리사이클러뷰의 마지막이 언제지 파악
    override fun getItemCount(): Int = MoodList.size

    @SuppressLint("NotifyDataSetChanged")
    fun addKeyword(keywordlist: MutableList<String>) {
        this.keywordlist.clear()
        this.keywordlist.addAll(keywordlist)

        keywordlistcopy = keywordlist

        notifyDataSetChanged()
        arrayList = keywordlist

        keywordlistcopy = keywordlist
    }

    //뷰홀더
    inner class ViewHolder(val binding: ItemWeekDiaryMoodBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(mood: String){
            binding.itemDiaryMoodTv.text= mood
//            Log.d("test1",keywordlist.toString())
//            Log.d("test2",keywordlistcopy.toString())
//            Log.d("test3",arrayList.toString())
//
//            if(keywordlist.size != 0){
//                if(mood == keywordlist.get(0)){
//                    var strokeColor : GradientDrawable = binding.itemDiaryMoodRl.background as GradientDrawable
//                    strokeColor.setStroke(1,Color.parseColor("#CC000000"))
//                }else{
//                    var strokeColor : GradientDrawable = binding.itemDiaryMoodRl.background as GradientDrawable
//                    strokeColor.setStroke(1,Color.parseColor("#c77a4a"))
//                }
//
//            }



        }
    }
}


