package com.eight.collection.ui.finish

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWeekDiaryClothColorBinding


class TopRVAdapter(private  val Toplist: ArrayList<Top>) : RecyclerView.Adapter<TopRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TopRVAdapter.ViewHolder {
        val binding: ItemWeekDiaryClothColorBinding = ItemWeekDiaryClothColorBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return  ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: TopRVAdapter.ViewHolder, position: Int) {
        holder.bind(Toplist[position])
    }


    override fun getItemCount(): Int = Toplist.size


    inner class ViewHolder(val binding: ItemWeekDiaryClothColorBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(top: Top){
            binding.itemDiaryClothTv.text= top.cloth
            binding.itemDiaryColorView.setBackgroundColor(Color.parseColor(top.color!!))
        }
    }


}


