package com.eight.collection.ui.main.match

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemMatchWeatherLastBinding
import kotlin.collections.ArrayList


class MatchButtonRVAdapter() : RecyclerView.Adapter<MatchButtonRVAdapter.ViewHolder>() {
//    private  val buttonlist: ArrayList<LastTag>

    private val buttonlist = ArrayList<LastTag>()

    interface MyitemClickListener{
        fun onItemClick(lastTag: LastTag, position: Int)
    }


    private  lateinit var mItemClickListener: MyitemClickListener


    fun setMyItemClickListener(itemClickListener: MyitemClickListener){
        mItemClickListener = itemClickListener
    }



    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MatchButtonRVAdapter.ViewHolder {
        val binding: ItemMatchWeatherLastBinding = ItemMatchWeatherLastBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return  ViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addButton(lastTag:  ArrayList<LastTag>){
        this.buttonlist.clear()
        this.buttonlist.addAll(lastTag)

        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: MatchButtonRVAdapter.ViewHolder, position: Int) {
        holder.bind(buttonlist[position])
        holder.itemView.setOnClickListener{
            mItemClickListener.onItemClick(buttonlist[position], position)

        }
    }


    override fun getItemCount(): Int = buttonlist.size


    inner class ViewHolder(val binding: ItemMatchWeatherLastBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(lastTag: LastTag){
            binding.matchDefaultTv.text = lastTag.text

        }
    }



}


