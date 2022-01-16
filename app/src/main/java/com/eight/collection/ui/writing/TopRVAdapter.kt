package com.eight.collection.ui.writing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWritefirstPhotoBinding
import com.eight.collection.databinding.ItemWritefirstTopBinding

class TopRVAdapter(private val topList: ArrayList<Top>) : RecyclerView.Adapter<TopRVAdapter.ViewHolder>(){
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TopRVAdapter.ViewHolder {
        val binding : ItemWritefirstTopBinding = ItemWritefirstTopBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopRVAdapter.ViewHolder, position: Int) {
        holder.bind(topList[position])
    }

    override fun getItemCount(): Int = topList.size


    inner class ViewHolder(val binding: ItemWritefirstTopBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(top:Top){
            binding.writefirstColorTopTextButton.text = top.title
        }
    }
}