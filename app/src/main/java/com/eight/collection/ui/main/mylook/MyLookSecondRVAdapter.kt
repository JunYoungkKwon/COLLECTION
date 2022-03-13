package com.eight.collection.ui.main.mylook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemMyLookSecondPhotoBinding


class MyLookSecondRVAdapter(private val myLookSecondList: ArrayList<Photo>) : RecyclerView.Adapter<MyLookSecondRVAdapter.ViewHolder>() {


    //클릭 인터페이스 정의
    interface MyitemClickListener{
        fun onItemClick(photo: Photo)
    }
    //리스너 객체를 저장할 변수
    private  lateinit var mItemClickListener: MyitemClickListener

    fun setMyItemClickListener(itemClickListener: MyitemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyLookSecondRVAdapter.ViewHolder {
        val binding: ItemMyLookSecondPhotoBinding = ItemMyLookSecondPhotoBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return  ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyLookSecondRVAdapter.ViewHolder, position: Int) {
        holder.bind(myLookSecondList[position])
        holder.itemView.setOnClickListener{  mItemClickListener.onItemClick(myLookSecondList[position]) }
    }


    override fun getItemCount(): Int = myLookSecondList.size


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


        fun bind(photo: Photo){
            binding.itemMyLookSecondPhotoIv.setImageResource(photo.photoImg!!)
            binding.itemMyLookDateTv.text = photo.date
        }
    }



}


