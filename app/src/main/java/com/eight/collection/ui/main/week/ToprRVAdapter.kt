package com.eight.collection.ui.main.week

import android.R.color
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.R
import com.eight.collection.data.entities.Cloth
import com.eight.collection.databinding.ItemWeekDiaryClothColorBinding
import java.util.*


class ToprRVAdapter(private  val Toplist: MutableList<Cloth>, val keywordList: MutableList<String>) : RecyclerView.Adapter<ToprRVAdapter.ViewHolder>() {

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
                if(keywordList.size == 1){
                    if(cloth.cloth == keywordList.get(0)){
                        binding.itemDiaryClothTv.setTextColor(Color.parseColor("#c77a4a"))
                    }else{
                        binding.itemDiaryClothTv.setTextColor(Color.parseColor("#FF000000"))
                    }
                }
                if(keywordList.size == 2){
                    if(cloth.cloth == keywordList.get(0) || cloth.cloth == keywordList.get(1)){
                        binding.itemDiaryClothTv.setTextColor(Color.parseColor("#c77a4a"))

                    }else{
                        binding.itemDiaryClothTv.setTextColor(Color.parseColor("#FF000000"))
                    }

                }
            }


        }
    }


}


