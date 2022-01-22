package com.eight.collection.ui.main.lookpoint

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemLookPointBinding
import com.eight.collection.databinding.ItemLookPointPhotoBinding
import com.eight.collection.databinding.ItemWeekDiaryBinding


class PhotoRVAdapter(val photoList: MutableList<Photo>) : RecyclerView.Adapter<PhotoRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PhotoRVAdapter.ViewHolder {
        val binding: ItemLookPointPhotoBinding = ItemLookPointPhotoBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return  ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: PhotoRVAdapter.ViewHolder, position: Int) {
        holder.bind(photoList[position])
    }


    override fun getItemCount(): Int = photoList.size


    inner class ViewHolder(val binding: ItemLookPointPhotoBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(photo: Photo){
            binding.itemLookPointPhotoImgIv.setImageResource(photo.photoImg!!)
        }
    }


}


