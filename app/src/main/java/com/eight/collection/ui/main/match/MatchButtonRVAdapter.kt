package com.eight.collection.ui.main.match

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemMatchWeatherLastBinding
import kotlin.collections.ArrayList
import kotlin.coroutines.coroutineContext


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

    @SuppressLint("NotifyDataSetChanged")
    fun addButton(lastTag:  ArrayList<LastTag>){
        this.buttonlist.clear()
        this.buttonlist.addAll(lastTag)

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = buttonlist.size


    override fun onBindViewHolder(holder: MatchButtonRVAdapter.ViewHolder, position: Int) {
        holder.bind(buttonlist[position], position)
        holder.itemView.setOnClickListener{
            mItemClickListener.onItemClick(buttonlist[position], position)
        }
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MatchButtonRVAdapter.ViewHolder {
        val binding: ItemMatchWeatherLastBinding = ItemMatchWeatherLastBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return  ViewHolder(binding)
    }



    inner class ViewHolder(val binding: ItemMatchWeatherLastBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(lastTag: LastTag, position: Int){
            binding.matchDefaultTv.apply {
                if(buttonlist[position].isdefault == true){
                    text = lastTag.text
                } else {
                    text = lastTag.text + "    "
                }
            }
            binding.matchDeleteButton.apply {
                if(buttonlist[position].isdefault == true){
                    visibility = View.GONE
                }
                else{
                    visibility = View.VISIBLE
                }
            }
        }
    }



}


