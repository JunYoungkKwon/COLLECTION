package com.eight.collection.ui.finish

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWeekDiaryClothColorBinding


class EtcRVAdapter(private  val Etclist: ArrayList<Etc>) : RecyclerView.Adapter<EtcRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): EtcRVAdapter.ViewHolder {
        val binding: ItemWeekDiaryClothColorBinding = ItemWeekDiaryClothColorBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return  ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: EtcRVAdapter.ViewHolder, position: Int) {
        holder.bind(Etclist[position])
    }


    override fun getItemCount(): Int = Etclist.size


    inner class ViewHolder(val binding: ItemWeekDiaryClothColorBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(etc: Etc){
            binding.itemDiaryClothTv.text= etc.cloth
            binding.itemDiaryColorView.setBackgroundColor(Color.parseColor(etc.color!!))

        }
    }


}


