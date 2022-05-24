package com.eight.collection.ui.main.match

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.R
import com.eight.collection.data.entities.Write.Block
import com.eight.collection.data.entities.Write.Content
import com.eight.collection.data.remote.match.MatchService
import com.eight.collection.databinding.ItemColorSearchBoxBinding
import com.eight.collection.databinding.ItemMatchWeatherLastBinding
import com.eight.collection.databinding.ItemWeekDiaryMoodBinding
import com.eight.collection.ui.writing.first.top.WritefirstTop
import kotlin.collections.ArrayList
import kotlin.coroutines.coroutineContext


class SearchColorTagRVAdapter(var tagList: ArrayList<LastTag>) : RecyclerView.Adapter<SearchColorTagRVAdapter.ViewHolder>(){

    override fun getItemCount(): Int = tagList.size

    @SuppressLint("Range")
    override fun onBindViewHolder(holder: SearchColorTagRVAdapter.ViewHolder, position: Int) {
        holder.bind(tagList[position], position)
        holder.binding.matchColorSearchRl.setBackgroundColor(Color.parseColor(tagList[position].color))
        when(tagList[position].color) {
            //red
            "#d60f0f" -> {
                holder.binding.matchColorSearchTv.setTextColor(Color.parseColor("#ffffff"))
            }
            //pink
            "#f59a9a" -> {
                holder.binding.matchColorSearchTv.setTextColor(Color.parseColor("#ffffff"))
            }
            //yellow
            "#ffb203" -> {
                holder.binding.matchColorSearchTv.setTextColor(Color.parseColor("#ffffff"))
            }
            //lightyellow
            "#fde6b1" -> {
                holder.binding.matchColorSearchTv.setTextColor(Color.parseColor("#191919"))
            }
            //green
            "#71a238" -> {
                holder.binding.matchColorSearchTv.setTextColor(Color.parseColor("#ffffff"))
            }
            //lightgreen
            "#b7de89" -> {
                holder.binding.matchColorSearchTv.setTextColor(Color.parseColor("#191919"))
            }
            //orange
            "#ea7831" -> {
                holder.binding.matchColorSearchTv.setTextColor(Color.parseColor("#ffffff"))
            }
            //navy
            "#273e88" -> {
                holder.binding.matchColorSearchTv.setTextColor(Color.parseColor("#ffffff"))
            }
            //blue
            "#4168e8" -> {
                holder.binding.matchColorSearchTv.setTextColor(Color.parseColor("#ffffff"))
            }
            //lightblue
            "#a5b9fa" -> {
                holder.binding.matchColorSearchTv.setTextColor(Color.parseColor("#ffffff"))
            }
            //purple
            "#894ac7" -> {
                holder.binding.matchColorSearchTv.setTextColor(Color.parseColor("#ffffff"))
            }
            //lightpurple
            "#dcacff" -> {
                holder.binding.matchColorSearchTv.setTextColor(Color.parseColor("#ffffff"))
            }
            //white
            "#ffffff" -> {
                holder.binding.matchColorSearchTv.setTextColor(Color.parseColor("#191919"))
            }
            //grey
            "#888888" -> {
                holder.binding.matchColorSearchTv.setTextColor(Color.parseColor("#ffffff"))
            }
            //black
            "#191919" -> {
                holder.binding.matchColorSearchTv.setTextColor(Color.parseColor("#ffffff"))
            }
            //lightpeach
            "#e8dcd5" -> {
                holder.binding.matchColorSearchTv.setTextColor(Color.parseColor("#191919"))
            }
            //pinkishgrey
            "#c3b5ac" -> {
                holder.binding.matchColorSearchTv.setTextColor(Color.parseColor("#ffffff"))
            }
            //brown
            "#74461f" -> {
                holder.binding.matchColorSearchTv.setTextColor(Color.parseColor("#ffffff"))
            }
        }
        holder.setIsRecyclable(false)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SearchColorTagRVAdapter.ViewHolder {
        val binding: ItemColorSearchBoxBinding = ItemColorSearchBoxBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return  ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemColorSearchBoxBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(tag: LastTag, position: Int){
            binding.matchColorSearchTv.apply{
                text = tag.text
            }
        }

    }
}


