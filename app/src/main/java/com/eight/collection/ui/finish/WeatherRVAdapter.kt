package com.eight.collection.ui.finish

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWeekDiaryBinding
import com.eight.collection.databinding.ItemWeekDiaryMoodBinding


class WeatherRVAdapter() : RecyclerView.Adapter<WeatherRVAdapter.ViewHolder>() {

    private val weatherList = ArrayList<String>()
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): WeatherRVAdapter.ViewHolder {
        val binding: ItemWeekDiaryMoodBinding = ItemWeekDiaryMoodBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return  ViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addWheeather(weatherList: ArrayList<String>) {
        this.weatherList.clear()
        this.weatherList.addAll(weatherList)

        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: WeatherRVAdapter.ViewHolder, position: Int) {
        holder.bind(weatherList[position])
    }

    //데이터의 크기를 알려줌 => 리사이클러뷰의 마지막이 언제지 파악
    override fun getItemCount(): Int = weatherList.size

    //뷰홀더
    inner class ViewHolder(val binding: ItemWeekDiaryMoodBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(weather: String){
            binding.itemDiaryMoodTv.text= weather
        }

    }


}


