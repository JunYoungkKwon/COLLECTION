package com.eight.collection.ui.finish

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.data.entities.Cloth
import com.eight.collection.databinding.ItemWeekDiaryClothColorBinding


class EtcRVAdapter() : RecyclerView.Adapter<EtcRVAdapter.ViewHolder>() {

    private val etclist = ArrayList<Cloth>()
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): EtcRVAdapter.ViewHolder {
        val binding: ItemWeekDiaryClothColorBinding = ItemWeekDiaryClothColorBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return  ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: EtcRVAdapter.ViewHolder, position: Int) {
        holder.bind(etclist[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addEtc(etclist: ArrayList<Cloth>) {
        this.etclist.clear()
        this.etclist.addAll(etclist)

        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = etclist.size


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


