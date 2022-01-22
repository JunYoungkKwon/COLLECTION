package com.eight.collection.ui.main.week

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWeekDiaryClothColorBinding


class BottomRVAdapter(private  val Bottomlist: MutableList<Bottom>) : RecyclerView.Adapter<BottomRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BottomRVAdapter.ViewHolder {
        val binding: ItemWeekDiaryClothColorBinding = ItemWeekDiaryClothColorBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return  ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: BottomRVAdapter.ViewHolder, position: Int) {
        holder.bind(Bottomlist[position])
    }


    override fun getItemCount(): Int = Bottomlist.size


    inner class ViewHolder(val binding: ItemWeekDiaryClothColorBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(bottom: Bottom){
            binding.itemDiaryClothTv.text= bottom.cloth
            binding.itemDiaryColorView.setBackgroundColor(Color.parseColor(bottom.color!!))

        }
    }


}


