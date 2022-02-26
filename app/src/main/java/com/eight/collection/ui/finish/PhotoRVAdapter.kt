package com.eight.collection.ui.finish

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eight.collection.R
import com.eight.collection.databinding.ItemFinishPhotoBinding
import com.eight.collection.databinding.ItemMyLookBinding
import com.eight.collection.databinding.ItemMyLookPhotoBinding
import com.eight.collection.databinding.ItemWeekDiaryBinding


class PhotoRVAdapter(val context: Context) : RecyclerView.Adapter<PhotoRVAdapter.ViewHolder>() {

    private val photoList = ArrayList<Photo>()
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PhotoRVAdapter.ViewHolder {
        val binding: ItemFinishPhotoBinding = ItemFinishPhotoBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return  ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: PhotoRVAdapter.ViewHolder, position: Int) {
        holder.bind(photoList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addPhoto(photoList: ArrayList<Photo>) {
        this.photoList.clear()
        this.photoList.addAll(photoList)

        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = photoList.size


    inner class ViewHolder(val binding: ItemFinishPhotoBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(photo: Photo){
            if(photo.imageUrl == null){
                Glide.with(context).load(R.drawable.camera_bg_01).into(binding.itemFinishPhotoImgIv)
            }
            else{
                Glide.with(context).load(photo.imageUrl).into(binding.itemFinishPhotoImgIv)
            }
        }
    }


}


