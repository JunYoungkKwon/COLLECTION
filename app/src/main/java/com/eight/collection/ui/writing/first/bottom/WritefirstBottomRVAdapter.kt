package com.eight.collection.ui.writing.first.bottom

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWritefirstBottomBinding
import com.eight.collection.databinding.ItemWritefirstTopBinding
import com.eight.collection.ui.writing.first.WritefirstActivity
import com.eight.collection.ui.writing.first.top.WritefirstTopRVAdapter

class WritefirstBottomRVAdapter(private val bottomList: ArrayList<WritefirstBottom>) : RecyclerView.Adapter<WritefirstBottomRVAdapter.ViewHolder>(){
    private var clickListener: BottomClickListener? = null
    private var selectId : Int = -1

    //Add 버튼 클릭시 데이터 추가
    interface BottomClickListener {
        fun plusButtonClick()
    }

    fun setBottomClickListener(bottomClickListener: BottomClickListener) {
        this.clickListener = bottomClickListener
    }

    //bottomList 수 반환
    override fun getItemCount(): Int = bottomList.size


    override fun onBindViewHolder(holder: WritefirstBottomRVAdapter.ViewHolder, position: Int) {
        holder.binding.writefirstColorBottomTextButton.isChecked = bottomList[position].focus
        holder.binding.writefirstBottomItemLayout.setBackgroundColor(Color.parseColor(bottomList[position].color))
        holder.binding.writefirstColorBottomTextButton.setTextColor(Color.parseColor(bottomList[position].textcolor))

        holder.bind(bottomList[position], position)
        holder.setIsRecyclable(false)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemWritefirstBottomBinding = ItemWritefirstBottomBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }


    inner class ViewHolder(val binding: ItemWritefirstBottomBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(bottom: WritefirstBottom, position: Int){
            binding.writefirstColorBottomTextButton.apply {
                text = bottom.name
                // select 여부 확인 및 상태 변경
                setOnClickListener {
                    when (bottomList[position].id) {
                        0 -> {
                            clickListener?.plusButtonClick()
                            isChecked = false
                        }
                        else -> {
                            if(selectId == -1) {
                                bottomList[position].focus = true
                                selectId = position
                            }
                            else if(selectId == position) {
                                bottomList[selectId].focus = false
                                bottomList[selectId].color = "#00ff0000"
                                bottomList[selectId].textcolor = "#c3b5ac"
                                selectId = -1
                            }
                            else {
                                bottomList[selectId].focus = false
                                bottomList[position].focus = true
                                selectId = position
                            }
                        }
                    }
                    notifyDataSetChanged()
                }
            }
        }
    }


    // 데이터 추가 메소드 (데이터 및 삭제아이콘 추가)
    fun addItem(top: WritefirstBottom){
        bottomList.add(top)
        notifyDataSetChanged()
    }

    // 데이터 삭제 메소드
    fun removeItem(position: Int){
        bottomList.removeAt(position)
        notifyDataSetChanged()
    }

    fun getSelectId() : Int{
        return selectId
    }
}