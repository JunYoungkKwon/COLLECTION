package com.eight.collection.ui.main.lookpoint

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemMyLookBinding
import com.eight.collection.databinding.ItemMyLookSecondPhotoBinding
import com.eight.collection.databinding.ItemWeekDiaryBinding


class MyLookSecondRVAdapter(private val myLookSecondList: ArrayList<Photo>) : RecyclerView.Adapter<MyLookSecondRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyLookSecondRVAdapter.ViewHolder {
        val binding: ItemMyLookSecondPhotoBinding = ItemMyLookSecondPhotoBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return  ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyLookSecondRVAdapter.ViewHolder, position: Int) {
        holder.bind(myLookSecondList[position])
    }


    override fun getItemCount(): Int = myLookSecondList.size


    inner class ViewHolder(val binding: ItemMyLookSecondPhotoBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(photo: Photo){
            binding.itemMyLookSecondPhotoIv.setImageResource(photo.photoImg!!)
        }
    }


}


