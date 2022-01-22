package com.eight.collection.ui.finish

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWeekDiaryBinding
import com.eight.collection.databinding.ItemWeekDiaryMoodBinding


class PlaceRVAdapter(val PlaceList: ArrayList<Place>) : RecyclerView.Adapter<PlaceRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PlaceRVAdapter.ViewHolder {
        val binding: ItemWeekDiaryMoodBinding = ItemWeekDiaryMoodBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return  ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: PlaceRVAdapter.ViewHolder, position: Int) {
        holder.bind(PlaceList[position])
    }

    //데이터의 크기를 알려줌 => 리사이클러뷰의 마지막이 언제지 파악
    override fun getItemCount(): Int = PlaceList.size

    //뷰홀더
    inner class ViewHolder(val binding: ItemWeekDiaryMoodBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(place: Place){
            binding.itemDiaryMoodTv.text= place.place
        }

    }


}


