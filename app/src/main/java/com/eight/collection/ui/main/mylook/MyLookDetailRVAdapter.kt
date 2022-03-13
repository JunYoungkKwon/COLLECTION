package com.eight.collection.ui.main.mylook

import android.annotation.SuppressLint
import android.content.Context
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
        fun onItemClick(myLookOOTD: MyLookOOTD)
    }

    private  lateinit var mItemClickListener: MyitemClickListener

    fun setMyItemClickListener(itemClickListener: MyitemClickListener){
        mItemClickListener = itemClickListener
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
        holder.itemView.setOnClickListener{  mItemClickListener.onItemClick(myLookOOTDList[position]) }
    }


    override fun getItemCount(): Int = myLookOOTDList.size


    inner class ViewHolder(val binding: ItemMyLookSecondPhotoBinding): RecyclerView.ViewHolder(binding.root){

        private var isClick: Boolean = true
        init {
            binding.itemMyLookSecondPhotoIv.setOnClickListener {
                if(isClick == true){
                    binding.itemMyLookDateTv.visibility = View.VISIBLE
                    binding.itemMyLookDimBackground.visibility = View.VISIBLE
                    isClick = false
                }
                else{
                    binding.itemMyLookDateTv.visibility = View.GONE
                    binding.itemMyLookDimBackground.visibility = View.GONE
                    isClick = true
                }
            }
        }


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


