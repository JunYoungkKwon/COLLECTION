package com.eight.collection.ui.finish

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWeekDiaryClothColorBinding
import com.eight.collection.ui.main.week.Diary


class BottomRVAdapter() : RecyclerView.Adapter<BottomRVAdapter.ViewHolder>() {

    private val bottomlist = ArrayList<Cloth>()
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BottomRVAdapter.ViewHolder {
        val binding: ItemWeekDiaryClothColorBinding = ItemWeekDiaryClothColorBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return  ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: BottomRVAdapter.ViewHolder, position: Int) {
        holder.bind(bottomlist[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addBottom(bottomlist: ArrayList<Cloth>) {
        this.bottomlist.clear()
        this.bottomlist.addAll(bottomlist)

        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = bottomlist.size


    inner class ViewHolder(val binding: ItemWeekDiaryClothColorBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(cloth: Cloth){
            binding.itemDiaryClothTv.text= cloth.cloth
            binding.itemDiaryColorView.setBackgroundColor(Color.parseColor(cloth.color!!))

        }
    }


}


