package com.eight.collection.ui.main.match

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eight.collection.R
import com.eight.collection.databinding.ItemMatchWeatherLastBinding
import com.eight.collection.databinding.ItemMyLookSecondPhotoBinding
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class MatchButtonRVAdapter(private  val buttonlist: ArrayList<Button>) : RecyclerView.Adapter<MatchButtonRVAdapter.ViewHolder>() {

//    private val buttonlist = ArrayList<Button>()

    interface MyitemClickListener{
        fun onItemClick(button: Button, position: Int)
    }


    private  lateinit var mItemClickListener: MyitemClickListener


    fun setMyItemClickListener(itemClickListener: MyitemClickListener){
        mItemClickListener = itemClickListener
    }



    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MatchButtonRVAdapter.ViewHolder {
        val binding: ItemMatchWeatherLastBinding = ItemMatchWeatherLastBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return  ViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addButton(button:  ArrayList<Button>){
        this.buttonlist.clear()
        this.buttonlist.addAll(button)

        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: MatchButtonRVAdapter.ViewHolder, position: Int) {
        holder.bind(buttonlist[position])
        holder.itemView.setOnClickListener{
            mItemClickListener.onItemClick(buttonlist[position], position)

        }
    }


    override fun getItemCount(): Int = buttonlist.size


    inner class ViewHolder(val binding: ItemMatchWeatherLastBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(button: Button){
            binding.matchWeatherLastTextButton.text = button.text
        }
    }



}


