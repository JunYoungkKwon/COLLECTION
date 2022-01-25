package com.eight.collection.ui.main.lookpoint

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemMyLookBinding
import com.eight.collection.databinding.ItemWeekDiaryBinding


class MyLookRVAdapter(val myLookList: MutableList<MyLook>) : RecyclerView.Adapter<MyLookRVAdapter.ViewHolder>() {

    //클릭 인터페이스 정의
    interface MyitemClickListener{
        fun onItemClik(myLook: MyLook)
    }
    //리스너 객체를 저장할 변수
    private  lateinit var mItemClickListener: MyitemClickListener

    //리스너 객체를 전달받는 함수
    fun setMyitemClickListener(itemClickListener: MyitemClickListener){
        mItemClickListener = itemClickListener
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyLookRVAdapter.ViewHolder {
        val binding: ItemMyLookBinding = ItemMyLookBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return  ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyLookRVAdapter.ViewHolder, position: Int) {
        holder.bind(myLookList[position])
        holder.itemView.setOnClickListener{ mItemClickListener.onItemClik(myLookList[position])}
    }


    override fun getItemCount(): Int = myLookList.size


    inner class ViewHolder(val binding: ItemMyLookBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(myLook: MyLook){
            binding.itemMyLook01Tv.text= myLook.number.toString()
            binding.itemMyLookPoint5Iv.setImageResource(myLook.pointImg!!)

            binding.itemMyLookImgRecyclerview.adapter = PhotoRVAdapter(myLook.innerList)

        }
    }


}


