package com.eight.collection.ui.writing.first.top

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWritefirstTopBinding
import android.widget.RadioButton
import kotlinx.coroutines.selects.select

class WritefirstTopRVAdapter(var topList: ArrayList<WritefirstTop>) : RecyclerView.Adapter<WritefirstTopRVAdapter.ViewHolder>(){
    private var clickListener: TopClickListener? = null
    private var selectId : Int = -1


    //Add 버튼 클릭시 데이터 추가
    interface TopClickListener {
        fun plusButtonClick()
    }

    fun setTopClickListener(topClickListener: TopClickListener) {
        this.clickListener = topClickListener
    }


    //topList 수 반환
    override fun getItemCount(): Int = topList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.writefirstColorTopTextButton.isChecked = topList[position].focus
        holder.binding.writefirstTopItemLayout.setBackgroundColor(Color.parseColor(topList[position].color))
        holder.binding.writefirstColorTopTextButton.setTextColor(Color.parseColor(topList[position].textcolor))

        holder.bind(topList[position], position)
        holder.setIsRecyclable(false)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemWritefirstTopBinding = ItemWritefirstTopBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemWritefirstTopBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(top: WritefirstTop, position: Int){
            binding.writefirstColorTopTextButton.apply {
                //버튼에 Text 대입
                text = top.name
                // select 여부 확인 및 상태 변경
                setOnClickListener {
                    when (topList[position].id) {
                        0 -> {
                            clickListener?.plusButtonClick()
                            isChecked = false
                        }
                        else -> {
                            if(selectId == -1) {
                                topList[position].focus = true
                                selectId = position
                            }
                            else if(selectId == position) {
                                topList[selectId].focus = false
                                topList[selectId].color = "#00ff0000"
                                topList[selectId].textcolor = "#c3b5ac"
                                selectId = -1
                            }
                            else {
                                topList[selectId].focus = false
                                topList[position].focus = true
                                selectId = position
                            }
                        }
                    }
                    notifyDataSetChanged()
                }
            }
        }
    }

    /*// 데이터 추가 메소드 (데이터 및 삭제아이콘 추가)
    fun addItem(top: TopFixedItem){
        topList.add(top)
        notifyDataSetChanged()
    }

    // 데이터 삭제 메소드
    fun removeItem(position: Int){
        topList.removeAt(position)
        notifyDataSetChanged()
    }*/


    fun getSelectId() : Int{
        return selectId
    }

}