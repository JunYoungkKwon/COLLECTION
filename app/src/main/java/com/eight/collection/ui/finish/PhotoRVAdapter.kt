package com.eight.collection.ui.finish

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemFinishPhotoBinding
import com.eight.collection.databinding.ItemLookPointBinding
import com.eight.collection.databinding.ItemLookPointPhotoBinding
import com.eight.collection.databinding.ItemWeekDiaryBinding


class PhotoRVAdapter(val photoList: ArrayList<Photo>) : RecyclerView.Adapter<PhotoRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PhotoRVAdapter.ViewHolder {
        val binding: ItemFinishPhotoBinding = ItemFinishPhotoBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return  ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: PhotoRVAdapter.ViewHolder, position: Int) {
        holder.bind(photoList[position])
    }


    override fun getItemCount(): Int = photoList.size


    inner class ViewHolder(val binding: ItemFinishPhotoBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(photo: Photo){
            binding.itemFinishPhotoImgIv.setImageResource(photo.photoImg!!)
        }
    }


}


