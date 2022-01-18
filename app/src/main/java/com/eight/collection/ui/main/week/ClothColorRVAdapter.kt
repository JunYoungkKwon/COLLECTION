package com.eight.collection.ui.main.week

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWeekDiaryBinding
import com.eight.collection.databinding.ItemWeekDiaryClothColorBinding


class ClothColorRVAdapter(private  val ClothColorlist: ArrayList<ClothColor>) : RecyclerView.Adapter<ClothColorRVAdapter.ViewHolder>() {

    //test
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ClothColorRVAdapter.ViewHolder {
        val binding: ItemWeekDiaryClothColorBinding = ItemWeekDiaryClothColorBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return  ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ClothColorRVAdapter.ViewHolder, position: Int) {
        holder.bind(ClothColorlist[position])
    }


    override fun getItemCount(): Int = ClothColorlist.size


    inner class ViewHolder(val binding: ItemWeekDiaryClothColorBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(clothColor: ClothColor){
            binding.itemDiaryClothTv.text= clothColor.cloth
            binding.itemDiaryColorIv.setImageResource(clothColor.color!!)

        }
    }


}


