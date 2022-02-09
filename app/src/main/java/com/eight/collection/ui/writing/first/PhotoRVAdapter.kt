package com.eight.collection.ui.writing.first

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWritefirstPhotoBinding

class PhotoRVAdapter(private val photoList:ArrayList<Photo>) : RecyclerView.Adapter<PhotoRVAdapter.ViewHolder>(){


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemWritefirstPhotoBinding = ItemWritefirstPhotoBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(photoList[position])
    }

    override fun getItemCount(): Int = photoList.size

    inner class ViewHolder(val binding:ItemWritefirstPhotoBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(photo: Photo){
            binding.itemWritefirstPhotoImgIv.setImageResource(photo.coverImg!!)
        }
    }
}