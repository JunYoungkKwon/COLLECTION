package com.eight.collection.ui.writing.first

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWritefirstTopBinding

class WritefirstTopRVAdapter(private val topList: ArrayList<WritefirstTop>) : RecyclerView.Adapter<WritefirstTopRVAdapter.ViewHolder>(){
    private var selectCheck : ArrayList<Int> = arrayListOf()
    private var clickListener: TopClickListener? = null

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





    interface TopClickListener {
        fun plusButtonClick()
    }


    fun setTopClickListener(topClickListener: TopClickListener) {
        this.clickListener = topClickListener
    }





    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemWritefirstTopBinding = ItemWritefirstTopBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(topList[position], position)
    }

    override fun getItemCount(): Int = topList.size


    inner class ViewHolder(val binding: ItemWritefirstTopBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(top: WritefirstTop, position: Int){
            /*if(position == 0) {
                clickListener?.plusButtonClick(position)
            }*/
            binding.writefirstColorTopTextButton.apply {
                text = top.title
                // select 여부 확인 및 상태 변경
                isChecked = selectCheck[bindingAdapterPosition] == 1
                setOnClickListener{
                    when(topList[position].id){
                        0 -> clickListener?.plusButtonClick()
                        else -> {
                            for (k in selectCheck.indices) {
                                if (k == bindingAdapterPosition) {
                                    selectCheck[k] = 1
                                }
                                else {
                                    selectCheck[k] = 0
                                }
                            }
                        }
                    }

                    notifyDataSetChanged()
                }
            }
        }
    }






    // 데이터 추가 메소드 (데이터 및 삭제아이콘 추가)
    fun addItem(top: WritefirstTop){
        topList.add(top)
        notifyDataSetChanged()
    }

    // 데이터 삭제 메소드
    fun removeItem(position: Int){
        topList.removeAt(position)
        notifyDataSetChanged()
    }
}