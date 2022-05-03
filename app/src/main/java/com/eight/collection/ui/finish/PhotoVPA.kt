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

    private val item = ArrayList<String>()

    interface MyitemClickListener{
        fun onBack()
    }

    private  lateinit var mItemClickListener: MyitemClickListener

    fun setMyitemClickListener(itemClickListener: MyitemClickListener){
        mItemClickListener = itemClickListener
    }

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
        holder.bind(item[position])
//        holder.binding.finishPhotoDelete.setOnClickListener { mItemClickListener.onBack()}

    }

    inner class PagerViewHolder(val binding: FragmentFinishPhotoBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(photo: String){
            if(photo.isNullOrEmpty()){
            }
            else{
                Glide.with(context).load(photo).into(binding.finishPhoto)
            }
        }
    }
}

