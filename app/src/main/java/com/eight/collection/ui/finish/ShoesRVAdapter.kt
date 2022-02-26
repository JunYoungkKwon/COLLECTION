package com.eight.collection.ui.finish

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWeekDiaryClothColorBinding


class ShoesRVAdapter() : RecyclerView.Adapter<ShoesRVAdapter.ViewHolder>() {

    private val shoeslist = ArrayList<Cloth>()
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ShoesRVAdapter.ViewHolder {
        val binding: ItemWeekDiaryClothColorBinding = ItemWeekDiaryClothColorBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return  ViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addShoes(shoeslist: ArrayList<Cloth>) {
        this.shoeslist.clear()
        this.shoeslist.addAll(shoeslist)

        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ShoesRVAdapter.ViewHolder, position: Int) {
        holder.bind(shoeslist[position])
    }


    override fun getItemCount(): Int = shoeslist.size


    inner class ViewHolder(val binding: ItemWeekDiaryClothColorBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(cloth: Cloth){
            binding.itemDiaryClothTv.text= cloth.cloth
            binding.itemDiaryColorView.setBackgroundColor(Color.parseColor(cloth.color!!))

        }
    }


}


