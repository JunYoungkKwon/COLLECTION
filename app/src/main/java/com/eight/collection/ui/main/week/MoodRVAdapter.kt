package com.eight.collection.ui.main.week

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWeekDiaryBinding
import com.eight.collection.databinding.ItemWeekDiaryMoodBinding


class MoodRVAdapter(val MoodList: MutableList<Mood>) : RecyclerView.Adapter<MoodRVAdapter.ViewHolder>() {

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

        fun bind(mood: Mood){
            binding.itemDiaryMoodTv.text= mood.mood
        }

    }


}


