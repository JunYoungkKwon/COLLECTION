package com.eight.collection.ui.main.week

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
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
            if(bottom.color == ""){
                binding.itemDiaryColorCardview.visibility = View.GONE
                binding.itemDiaryColorView.visibility = View.GONE
                binding.itemDiaryClothTv.visibility = View.GONE
                binding.itemDiaryDefaultTv.visibility = View.VISIBLE
            }else{
                binding.itemDiaryColorView.setBackgroundColor(Color.parseColor(bottom.color))
                binding.itemDiaryClothTv.text= bottom.cloth
            }

        }
    }


}


