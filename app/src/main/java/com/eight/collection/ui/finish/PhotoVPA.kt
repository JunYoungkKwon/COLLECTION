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
                Glide.with(context).load(R.drawable.finish_default_img).into(binding.finishPhoto)
            }
            else{
                Glide.with(context).load(photo).into(binding.finishPhoto)
            }
        }
    }

//    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
//        (LayoutInflater.from(parent.context).inflate(R.layout.fragment_finish_photo, parent, false)){
//
//
//        val idol = itemView.findViewById<View>(R.id.finish_photo)
//
////        if(photo.thumbnail == 1){
////            Glide.with(context).load(R.drawable.finish_photo).into(binding.itemFinishPhotoImgIv)
////        }
////        else{
////            Glide.with(context).load(photo.imageUrl).into(binding.itemFinishPhotoImgIv)
////        }
//    }


}

//class PhotoVPA(val context: Context) : RecyclerView.Adapter<PhotoVPA.PagerViewHolder>() {
//
//    private val item = ArrayList<String>()
//    //var item = idolList
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder((parent))
//
//    override fun getItemCount(): Int = item.size
//
//    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
//        holder.idol.setBackgroundResource(item[position])
//        holder.itemView.setBackgroundResource(item[position])
//        holder.bindingAdapter.set
//    }
//
//    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
//        (LayoutInflater.from(parent.context).inflate(R.layout.fragment_finish_photo, parent, false)){
//
//        val idol = itemView.findViewById<View>(R.id.finish_photo)
//    }
//}
//
//class PhotoVPA(private val bgColors: ArrayList<String>) : RecyclerView.Adapter<PhotoVPA.PagerViewHolder>() {
//
//    inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        private val pageName: TextView = itemView.findViewById(R.id.pageName)
//
//        fun bind(@ColorRes bgColor: String, position: Int) {
//            pageName.text = "Page ${position+1}"
//            pageName.setBackgroundColor(ContextCompat.getColor(textView.context, bgColor))
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(
//            R.layout.fragment_finish_photo,
//            parent,
//            false
//        )
//        return PagerViewHolder(view)
//    }
//    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
//        holder.bind(bgColors[position], position)
//    }
//
//    override fun getItemCount(): Int = bgColors.size
//}
