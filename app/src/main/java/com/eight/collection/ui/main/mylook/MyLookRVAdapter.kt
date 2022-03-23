package com.eight.collection.ui.main.mylook

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eight.collection.R
import com.eight.collection.data.entities.Diary
import com.eight.collection.data.entities.MyLook
import com.eight.collection.data.remote.mylook.MyLookResult
import com.eight.collection.databinding.ItemMyLookBinding
import com.eight.collection.databinding.ItemMyLookPhotoBinding
import com.eight.collection.ui.main.week.ToprRVAdapter


class MyLookRVAdapter(val context: Context) : RecyclerView.Adapter<MyLookRVAdapter.ViewHolder>() {

    private val myLookOOTDList = mutableListOf<MyLookOOTD>()

    interface MyitemClickListener{
        fun onItemClick(myLookOOTD: MyLookOOTD, position: Int, context: Context)
    }

    private  lateinit var mItemClickListener: MyitemClickListener

    fun setMyItemClickListener(itemClickListener: MyitemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyLookRVAdapter.ViewHolder {
        val binding: ItemMyLookPhotoBinding = ItemMyLookPhotoBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return  ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyLookRVAdapter.ViewHolder, position: Int) {
        holder.bind(myLookOOTDList[position])
        holder.itemView.setOnClickListener{
            mItemClickListener.onItemClick(myLookOOTDList[position], position, context) }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun addOOTD(myLookResult:  MutableList<MyLookOOTD>){
        this.myLookOOTDList.clear()
        this.myLookOOTDList.addAll(myLookResult)

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = myLookOOTDList.size


    inner class ViewHolder(val binding: ItemMyLookPhotoBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(myLookOOTD: MyLookOOTD){
            if(myLookOOTD.imageUrl.isNullOrEmpty()){
                Glide.with(context).load(R.drawable.mylook_second_default).into(binding.itemLookPointPhotoImgIv)
            }
            else{
                Glide.with(context).load(myLookOOTD.imageUrl).into(binding.itemLookPointPhotoImgIv)
            }

        }
    }


}


