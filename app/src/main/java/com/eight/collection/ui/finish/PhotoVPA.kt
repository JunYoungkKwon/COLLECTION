package com.eight.collection.ui.finish

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eight.collection.R
import com.eight.collection.databinding.FragmentFinishPhotoBinding

class PhotoVPA(val context: Context) : RecyclerView.Adapter<PhotoVPA.PagerViewHolder>() {
//    var item = idolList

    private val item = ArrayList<String>()

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder((parent))

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PhotoVPA.PagerViewHolder {
        val binding: FragmentFinishPhotoBinding = FragmentFinishPhotoBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return  PagerViewHolder(binding)
    }

    override fun getItemCount(): Int = item.size

    @SuppressLint("NotifyDataSetChanged")
    fun addPhoto(item: ArrayList<String>) {
        this.item.clear()
        this.item.addAll(item)

        notifyDataSetChanged()
    }



    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
//        holder.idol.setBackgroundResource(position)
        holder.bind(item[position])

    }

    inner class PagerViewHolder(val binding: FragmentFinishPhotoBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(photo: String){
            if(photo.isNullOrEmpty()){
                Glide.with(context).load(R.drawable.finish_default2_nobg).into(binding.finishPhoto)
            }
            else{
                Glide.with(context).load(photo).into(binding.finishPhoto)
            }
        }
    }
}

