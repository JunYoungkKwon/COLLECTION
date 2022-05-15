package com.eight.collection.ui.main.match

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.data.entities.Write.Block
import com.eight.collection.data.entities.Write.Content
import com.eight.collection.data.remote.match.MatchService
import com.eight.collection.databinding.ItemMatchWeatherLastBinding
import com.eight.collection.databinding.ItemWeekDiaryMoodBinding
import com.eight.collection.ui.writing.first.top.WritefirstTop
import kotlin.collections.ArrayList
import kotlin.coroutines.coroutineContext


class SearchTagRVAdapter(var tagList: ArrayList<LastTag>) : RecyclerView.Adapter<SearchTagRVAdapter.ViewHolder>(){

    override fun getItemCount(): Int = tagList.size

    override fun onBindViewHolder(holder: SearchTagRVAdapter.ViewHolder, position: Int) {
        holder.bind(tagList[position], position)
        holder.setIsRecyclable(false)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SearchTagRVAdapter.ViewHolder {
        val binding: ItemWeekDiaryMoodBinding = ItemWeekDiaryMoodBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return  ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemWeekDiaryMoodBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(tag: LastTag, position: Int){
            binding.itemDiaryMoodTv.apply{
                text = tag.text
            }
        }

    }
}


