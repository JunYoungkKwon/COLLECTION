package com.eight.collection.ui.main.week

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWeekDiaryClothColorBinding


class ToprRVAdapter(private  val Toplist: ArrayList<Top>) : RecyclerView.Adapter<ToprRVAdapter.ViewHolder>() {

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
            binding.itemDiaryColorIv.setImageResource(top.color!!)

        }
    }


}


