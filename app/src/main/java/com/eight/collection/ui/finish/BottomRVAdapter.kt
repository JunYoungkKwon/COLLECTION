package com.eight.collection.ui.finish

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.data.entities.Cloth
import com.eight.collection.databinding.ItemWeekDiaryClothColorBinding
import com.eight.collection.data.entities.Diary


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


