package com.eight.collection.ui.main.week

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWeekDiaryBinding


class DiaryRVAdapter(private  val Diarylist: ArrayList<Diary>, private val Moodlist: ArrayList<Mood>, private val ClothColorlist: ArrayList<ClothColor>) : RecyclerView.Adapter<DiaryRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): DiaryRVAdapter.ViewHolder {
        val binding: ItemWeekDiaryBinding = ItemWeekDiaryBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return  ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: DiaryRVAdapter.ViewHolder, position: Int) {
        holder.bind(Diarylist[position])
    }


    override fun getItemCount(): Int = Diarylist.size


    inner class ViewHolder(val binding: ItemWeekDiaryBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(diary: Diary){
            binding.itemDiaryImgCountTv.text= diary.imgCount.toString()
            binding.itemDiaryImgIv.setImageResource(diary.coverImg!!)

            binding.weekDiaryMoodRecyclerView.adapter = MoodRVAdapter(Moodlist)
            binding.weekDiaryTopRecyclerView.adapter = ClothColorRVAdapter(ClothColorlist)
        }
    }


}


