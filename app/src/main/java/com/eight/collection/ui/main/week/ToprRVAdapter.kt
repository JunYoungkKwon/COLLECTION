package com.eight.collection.ui.main.week

import android.R.color
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.R
import com.eight.collection.databinding.ItemWeekDiaryClothColorBinding
import com.eight.collection.ui.finish.Cloth
import java.util.*


class ToprRVAdapter(private  val Toplist: MutableList<Cloth>) : RecyclerView.Adapter<ToprRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ToprRVAdapter.ViewHolder {
        val binding: ItemWeekDiaryClothColorBinding = ItemWeekDiaryClothColorBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return  ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ToprRVAdapter.ViewHolder, position: Int) {
        holder.bind(Toplist[position])
    }

    override fun getItemCount(): Int = Toplist.size


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


