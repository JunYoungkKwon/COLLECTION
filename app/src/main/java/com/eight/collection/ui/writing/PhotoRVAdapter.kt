package com.eight.collection.ui.writing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWritefirstPhotoBinding

class PhotoRVAdapter(private val photoList:ArrayList<Photo>) : RecyclerView.Adapter<PhotoRVAdapter.ViewHolder>(){


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PhotoRVAdapter.ViewHolder {
        val binding : ItemWritefirstPhotoBinding = ItemWritefirstPhotoBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoRVAdapter.ViewHolder, position: Int) {
        holder.bind(photoList[position])
    }

    override fun getItemCount(): Int = photoList.size

    inner class ViewHolder(val binding:ItemWritefirstPhotoBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(photo:Photo){
            binding.itemWritefirstPhotoImgIv.setImageResource(photo.coverImg!!)
        }
    }
}