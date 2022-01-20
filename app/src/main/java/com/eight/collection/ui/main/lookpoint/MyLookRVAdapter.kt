package com.eight.collection.ui.main.lookpoint

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemLookPointBinding
import com.eight.collection.databinding.ItemWeekDiaryBinding


class MyLookRVAdapter(private  val MyLooklist: ArrayList<MyLook>, private  val Photolist: ArrayList<Photo>) : RecyclerView.Adapter<MyLookRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyLookRVAdapter.ViewHolder {
        val binding: ItemLookPointBinding = ItemLookPointBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return  ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyLookRVAdapter.ViewHolder, position: Int) {
        holder.bind(MyLooklist[position])
    }


    override fun getItemCount(): Int = MyLooklist.size


    inner class ViewHolder(val binding: ItemLookPointBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(myLook: MyLook){
            binding.itemMyLook01Tv.text= myLook.number.toString()
            binding.itemMyLookPoint5Iv.setImageResource(myLook.pointImg!!)

            binding.itemMyLookImgRecyclerview.adapter = PhotoRVAdapter(Photolist)

        }
    }


}


