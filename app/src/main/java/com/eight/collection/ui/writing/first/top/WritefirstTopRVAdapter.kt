package com.eight.collection.ui.writing.first.top

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWritefirstTopBinding
import android.graphics.Color
import android.util.Log
import android.widget.RadioButton
import com.eight.collection.R
import com.eight.collection.ui.writing.first.WritefirstActivity
import com.eight.collection.utils.*

class WritefirstTopRVAdapter(private val topList: ArrayList<TopFixedItem>) : RecyclerView.Adapter<WritefirstTopRVAdapter.ViewHolder>(){
    private var selectCheck : ArrayList<Int> = arrayListOf()
    private var clickListener: TopClickListener? = null
    var colorText : String? = getColor()
    var selectedId : Int = getSelectedId()
    private var selectId : Int = -1
    private var beforeselectedId : RadioButton? = null

    //버튼 Select 초기화
    init {
        for(i in topList){
            if(i.name == "-"){
                selectCheck.add(1)
            }
            else{
                selectCheck.add(0)
            }
        }
    }

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
        holder.bind(topList[position], position)
        holder.setIsRecyclable(false)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemWritefirstTopBinding = ItemWritefirstTopBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemWritefirstTopBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(top: TopFixedItem, position: Int){
            binding.writefirstColorTopTextButton.apply {
                //버튼에 Text 대입
                text = top.name

                // select 여부 확인 및 상태 변경
                isChecked = selectCheck[position] == 1

                setOnClickListener {
                    when (topList[position].id) {
                        0 -> clickListener?.plusButtonClick()
                        else -> {
                            for (i in selectCheck.indices) {
                                if (i == position) {
                                    selectCheck[i] = 1
                                    selectId = position
                                } else {
                                    selectCheck[i] = 0
                                }
                            }
                        }
                    }

                    //
                    notifyItemChanged(position)
                    if (beforeselectedId != null){
                        beforeselectedId?.isChecked = false
                    }
                    beforeselectedId = this
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