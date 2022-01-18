package com.eight.collection.ui.writing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWritefirstPhotoBinding
import com.eight.collection.databinding.ItemWritefirstTopBinding

class TopRVAdapter(private val topList: ArrayList<Top>) : RecyclerView.Adapter<TopRVAdapter.ViewHolder>(){

    private var selectCheck : ArrayList<Int> = arrayListOf()


    init {
        for(i in topList){
            if(i.title == "-"){
                selectCheck.add(1)
            }
            else{
                selectCheck.add(0)
            }
        }
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemWritefirstTopBinding = ItemWritefirstTopBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(topList[position])
    }

    override fun getItemCount(): Int = topList.size


    inner class ViewHolder(val binding: ItemWritefirstTopBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(top:Top){
            binding.writefirstColorTopTextButton.apply {
                text = top.title
                isChecked = selectCheck[bindingAdapterPosition] == 1
                setOnClickListener{
                    for (k in selectCheck.indices) {
                        if (k == bindingAdapterPosition) {
                            selectCheck[k] = 1
                        }
                        else {
                            selectCheck[k] = 0
                        }
                    }
                    notifyDataSetChanged()
                }
            }
        }
    }
}