package com.eight.collection.ui.finish

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWeekDiaryBinding
import com.eight.collection.databinding.ItemWeekDiaryMoodBinding


class WhoRVAdapter() : RecyclerView.Adapter<WhoRVAdapter.ViewHolder>() {

    private val whoList = ArrayList<String>()
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): WhoRVAdapter.ViewHolder {
        val binding: ItemWeekDiaryMoodBinding = ItemWeekDiaryMoodBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return  ViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addWho(whoList: ArrayList<String>) {
        this.whoList.clear()
        this.whoList.addAll(whoList)

        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: WhoRVAdapter.ViewHolder, position: Int) {
        holder.bind(whoList[position])
    }

    //데이터의 크기를 알려줌 => 리사이클러뷰의 마지막이 언제지 파악
    override fun getItemCount(): Int = whoList.size

    //뷰홀더
    inner class ViewHolder(val binding: ItemWeekDiaryMoodBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(who: String){
            binding.itemDiaryMoodTv.text= who
        }

    }


}


