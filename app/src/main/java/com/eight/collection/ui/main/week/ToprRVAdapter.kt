package com.eight.collection.ui.main.week

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eight.collection.R
import com.eight.collection.databinding.ItemWeekDiaryClothColorBinding
import java.time.LocalDate
import java.time.ZoneId
import java.util.*


class ToprRVAdapter(private  val Toplist: MutableList<Top>) : RecyclerView.Adapter<ToprRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ToprRVAdapter.ViewHolder {
        val binding: ItemWeekDiaryClothColorBinding = ItemWeekDiaryClothColorBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return  ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ToprRVAdapter.ViewHolder, position: Int) {
        holder.bind(Toplist[position])
    }

    override fun getItemCount(): Int = Toplist.size


    inner class ViewHolder(val binding: ItemWeekDiaryClothColorBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(top: Top){
            binding.itemDiaryClothTv.text= top.cloth
            if(top.color == ""){
                binding.itemDiaryColorView.setBackgroundColor(Color.TRANSPARENT)
                Log.d("Test2","test")
            }else{
                binding.itemDiaryColorView.setBackgroundColor(Color.parseColor(top.color!!))
            }

        }
    }


}


