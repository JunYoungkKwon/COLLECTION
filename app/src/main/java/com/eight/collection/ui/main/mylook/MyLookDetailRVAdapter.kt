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
import com.eight.collection.databinding.ItemMyLookSecondPhotoBinding
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


class MyLookDetailRVAdapter(val context: Context) : RecyclerView.Adapter<MyLookDetailRVAdapter.ViewHolder>() {

    private val myLookOOTDList = mutableListOf<MyLookOOTD>()

    interface MyitemClickListener{
        fun onItemClick(myLookOOTD: MyLookOOTD, position: Int)
    }

    interface MyitemLongClickListener{
        fun onItemLongClick(myLookOOTD: MyLookOOTD, position: Int)
    }

    private  lateinit var mItemClickListener: MyitemClickListener

    private  lateinit var mItemLongClickListener: MyitemLongClickListener

    fun setMyItemClickListener(itemClickListener: MyitemClickListener){
        mItemClickListener = itemClickListener
    }

    fun setMyItemLongClickListener(itemClickListener: MyitemLongClickListener){
        mItemLongClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyLookDetailRVAdapter.ViewHolder {
        val binding: ItemMyLookSecondPhotoBinding = ItemMyLookSecondPhotoBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return  ViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addOOTD(myLookResult:  MutableList<MyLookOOTD>){
        this.myLookOOTDList.clear()
        this.myLookOOTDList.addAll(myLookResult)

        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: MyLookDetailRVAdapter.ViewHolder, position: Int) {
        holder.bind(myLookOOTDList[position])
        var isClick: Boolean = true
        holder.itemView.setOnClickListener{
            mItemClickListener.onItemClick(myLookOOTDList[position], position)
            if(isClick == true){
                holder.binding.itemMyLookDateTv.visibility = View.VISIBLE
                holder.binding.itemMyLookDimBackground.visibility = View.VISIBLE
                isClick = false
            }
            else{
                holder.binding.itemMyLookDateTv.visibility = View.GONE
                holder.binding.itemMyLookDimBackground.visibility = View.GONE
                isClick = true
            }
        }
        holder.itemView.setOnLongClickListener {
            mItemLongClickListener.onItemLongClick(myLookOOTDList[position], position)
            false
        }
    }


    override fun getItemCount(): Int = myLookOOTDList.size


    inner class ViewHolder(val binding: ItemMyLookSecondPhotoBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(myLookOOTD: MyLookOOTD){
            if(myLookOOTD.imageUrl.isNullOrEmpty()){
                Glide.with(context).load(R.drawable.mylook_second_default).into(binding.itemMyLookSecondPhotoIv)
            }
            else{
                Glide.with(context).load(myLookOOTD.imageUrl).into(binding.itemMyLookSecondPhotoIv)
            }

            val date: Date = myLookOOTD.date
            val localdate: LocalDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
            val formatters = DateTimeFormatter.ofPattern("yyyy.MM.dd")
            val convertDate: String = localdate.format(formatters)
            binding.itemMyLookDateTv.text = convertDate

        }
    }



}


