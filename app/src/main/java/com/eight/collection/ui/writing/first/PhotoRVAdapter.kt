package com.eight.collection.ui.writing.first

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eight.collection.R
import com.eight.collection.databinding.ItemWritefirstPhotoBinding

class PhotoRVAdapter(private val items:ArrayList<Uri>, val context : Context) : RecyclerView.Adapter<PhotoRVAdapter.ViewHolder>(){

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        Glide.with(context).load(item)
            .override(500,500)
            .into(holder.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        /*val binding : ItemWritefirstPhotoBinding = ItemWritefirstPhotoBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)*/
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.item_writefirst_photo, parent, false)
        /*return ViewHolder(binding)*/
        return ViewHolder(inflatedView)
    }

    /*inner class ViewHolder(val binding:ItemWritefirstPhotoBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(photo: Photo){
            binding.itemWritefirstPhotoImgIv.setImageResource(photo.coverImg!!)
        }
    }*/

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
        private var view : View = v
        var image = v.findViewById<ImageView>(R.id.item_writefirst_photo_img_iv)
        fun bind(listner: View.OnClickListener){
            view.setOnClickListener(listner)
        }
    }
}