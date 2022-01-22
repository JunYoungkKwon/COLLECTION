package com.eight.collection.ui.main.lookpoint

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemLookPointBinding
import com.eight.collection.databinding.ItemWeekDiaryBinding


class MyLookRVAdapter(val myLookList: MutableList<MyLook>) : RecyclerView.Adapter<MyLookRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyLookRVAdapter.ViewHolder {
        val binding: ItemLookPointBinding = ItemLookPointBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return  ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyLookRVAdapter.ViewHolder, position: Int) {
        holder.bind(myLookList[position])
    }


    override fun getItemCount(): Int = myLookList.size


    inner class ViewHolder(val binding: ItemLookPointBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(myLook: MyLook){
            binding.itemMyLook01Tv.text= myLook.number.toString()
            binding.itemMyLookPoint5Iv.setImageResource(myLook.pointImg!!)

            binding.itemMyLookImgRecyclerview.adapter = PhotoRVAdapter(myLook.innerList)

        }
    }


}


