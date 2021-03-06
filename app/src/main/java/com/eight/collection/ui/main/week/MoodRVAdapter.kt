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

class MoodRVAdapter(val MoodList: MutableList<String>, val keywordList: MutableList<String>) : RecyclerView.Adapter<MoodRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MoodRVAdapter.ViewHolder {
        val binding: ItemWeekDiaryMoodBinding = ItemWeekDiaryMoodBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return  ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MoodRVAdapter.ViewHolder, position: Int) {
        holder.bind(MoodList[position])
    }

    //데이터의 크기를 알려줌 => 리사이클러뷰의 마지막이 언제지 파악
    override fun getItemCount(): Int = MoodList.size



    //뷰홀더
    inner class ViewHolder(val binding: ItemWeekDiaryMoodBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(mood: String){
            binding.itemDiaryMoodTv.text= mood
            Log.d("test1",MoodList.toString())
            Log.d("test2",keywordList.toString())

            if(keywordList.size != 0){
                if(keywordList.size == 1){
                    if(mood == keywordList.get(0)){
                        var strokeColor : GradientDrawable = binding.itemDiaryMoodRl.background as GradientDrawable
                        strokeColor.setStroke(3,Color.parseColor("#c77a4a"))
                    }else{
                        var strokeColor : GradientDrawable = binding.itemDiaryMoodRl.background as GradientDrawable
                        strokeColor.setStroke(5,Color.parseColor("#e8dcd5"))
                    }
                }
                if(keywordList.size == 2){
                    if(mood == keywordList.get(0) || mood == keywordList.get(1)){
                        var strokeColor : GradientDrawable = binding.itemDiaryMoodRl.background as GradientDrawable
                        strokeColor.setStroke(3,Color.parseColor("#c77a4a"))
                    }else{
                        var strokeColor : GradientDrawable = binding.itemDiaryMoodRl.background as GradientDrawable
                        strokeColor.setStroke(5,Color.parseColor("#e8dcd5"))
                    }

                }

            }
        }
    }
}


