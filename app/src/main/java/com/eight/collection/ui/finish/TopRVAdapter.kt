package com.eight.collection.ui.finish

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.data.entities.Cloth
import com.eight.collection.databinding.ItemWeekDiaryClothColorBinding


class TopRVAdapter() : RecyclerView.Adapter<TopRVAdapter.ViewHolder>() {

    private val toplist = ArrayList<Cloth>()
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TopRVAdapter.ViewHolder {
        val binding: ItemWeekDiaryClothColorBinding = ItemWeekDiaryClothColorBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return  ViewHolder(binding)
    }


    @SuppressLint("NotifyDataSetChanged")
    fun addTop(toplist: ArrayList<Cloth>) {
        this.toplist.clear()
        this.toplist.addAll(toplist)

        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: TopRVAdapter.ViewHolder, position: Int) {
        holder.bind(toplist[position])
    }




    override fun getItemCount(): Int = toplist.size


    inner class ViewHolder(val binding: ItemWeekDiaryClothColorBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(cloth: Cloth){
            if(cloth.color == ""){
                binding.itemDiaryColorCardview.visibility = View.GONE
                binding.itemDiaryColorView.visibility = View.GONE
                binding.itemDiaryClothTv.visibility = View.GONE
                binding.itemDiaryDefaultTv.visibility = View.VISIBLE
            }else{
                binding.itemDiaryColorView.setBackgroundColor(Color.parseColor(cloth.color))
                binding.itemDiaryClothTv.text= cloth.cloth
            }
        }
    }


}


