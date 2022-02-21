package com.eight.collection.ui.main.week

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWeekDiaryClothColorBinding


class ShoesRVAdapter(private  val Shoeslist: MutableList<Shoes>) : RecyclerView.Adapter<ShoesRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ShoesRVAdapter.ViewHolder {
        val binding: ItemWeekDiaryClothColorBinding = ItemWeekDiaryClothColorBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return  ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ShoesRVAdapter.ViewHolder, position: Int) {
        holder.bind(Shoeslist[position])
    }


    override fun getItemCount(): Int = Shoeslist.size


    inner class ViewHolder(val binding: ItemWeekDiaryClothColorBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(shoes: Shoes){
            if(shoes.color == ""){
                binding.itemDiaryColorCardview.visibility = View.GONE
                binding.itemDiaryColorView.visibility = View.GONE
                binding.itemDiaryClothTv.visibility = View.GONE
                binding.itemDiaryDefaultTv.visibility = View.VISIBLE
            }else{
                binding.itemDiaryColorView.setBackgroundColor(Color.parseColor(shoes.color))
                binding.itemDiaryClothTv.text= shoes.cloth
            }

        }
    }


}


