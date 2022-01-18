package com.eight.collection.ui.main.week

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWeekDiaryClothColorBinding


class EtcRVAdapter(private  val Etclist: ArrayList<Etc>) : RecyclerView.Adapter<EtcRVAdapter.ViewHolder>() {

    //test
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
            binding.itemDiaryColorIv.setImageResource(etc.color!!)

        }
    }


}


