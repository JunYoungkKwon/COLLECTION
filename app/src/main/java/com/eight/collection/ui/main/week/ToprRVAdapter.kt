package com.eight.collection.ui.main.week

import android.R.color
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.R
import com.eight.collection.databinding.ItemWeekDiaryClothColorBinding
import java.util.*


class ToprRVAdapter(private  val Toplist: MutableList<Top>) : RecyclerView.Adapter<ToprRVAdapter.ViewHolder>() {

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
            if(top.color == ""){
                binding.itemDiaryColorCardview.visibility = View.GONE
                binding.itemDiaryColorView.visibility = View.GONE
                binding.itemDiaryClothTv.visibility = View.GONE
                binding.itemDiaryDefaultTv.visibility = View.VISIBLE
            }else{
                binding.itemDiaryColorView.setBackgroundColor(Color.parseColor(top.color))
                binding.itemDiaryClothTv.text= top.cloth
            }

        }
    }


}


