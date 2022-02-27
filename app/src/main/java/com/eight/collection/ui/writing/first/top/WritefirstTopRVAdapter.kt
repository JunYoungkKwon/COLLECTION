package com.eight.collection.ui.writing.first.top

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWritefirstTopBinding
import com.eight.collection.data.entities.Write.Block
import com.eight.collection.data.remote.deleteblock.DeleteBlockService
import com.eight.collection.ui.writing.DeleteBlockView
import com.eight.collection.ui.writing.first.AddedClothes
import com.eight.collection.ui.writing.first.FixedClothes

class WritefirstTopRVAdapter(var topList: ArrayList<WritefirstTop>) : RecyclerView.Adapter<WritefirstTopRVAdapter.ViewHolder>(), DeleteBlockView {
    private var clickListener: TopClickListener? = null
    private var selectId: Int = -1


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
        val binding: ItemWritefirstTopBinding = ItemWritefirstTopBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemWritefirstTopBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(top: WritefirstTop, position: Int) {
            binding.writefirstColorTopTextButton.apply {
                //버튼에 Text 대입
                if (topList[position].id < 13) {
                    text = top.name
                } else {
                    text = top.name + "    "
                }
                // select 여부 확인 및 상태 변경
                setOnClickListener {
                    when (topList[position].id) {
                        0 -> {
                            clickListener?.plusButtonClick()
                            isChecked = false
                        }
                        else -> {
                            // 처음 선택시
                            if (selectId == -1) {
                                topList[position].focus = true
                                selectId = position
                            }
                            // 선택한거 다시 클릭시
                            else if (selectId == position) {
                                topList[selectId].focus = false
                                topList[selectId].color = "#00ff0000"
                                topList[selectId].textcolor = "#c3b5ac"
                                selectId = -1
                            }
                            // 선택한거말고 다른거 클릭시
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
            binding.writefirstColorTopDeleteButton.apply {
                if (topList[position].id < 13) {
                    visibility = View.GONE
                } else {
                    visibility = View.VISIBLE
                    setOnClickListener{
                        when (topList[position].id){
                            0 -> {}
                            else -> {
                                removeItem(position)
                                if(position == selectId){
                                    selectId = -1
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /*// 데이터 추가 메소드 (데이터 및 삭제아이콘 추가)
    fun addItem(top: TopFixedItem){
        topList.add(top)
        notifyDataSetChanged()
    }*/

    // 데이터 삭제 메소드
    fun removeItem(position: Int){
        deleteBlock(topList[position].name.toString())
        topList.removeAt(position)
        notifyDataSetChanged()
    }


    fun getSelectId() : Int{
        return selectId
    }

    private fun getBlock(content : String) : Block {
        val clothes : Int = 0
        val pww : Int = -1
        return Block(clothes,pww,content)
    }

    private fun deleteBlock(content : String) {
        DeleteBlockService.deleteBlock(this, getBlock(content))
    }

    override fun onDeleteBlockLoading() {

    }

    override fun onDeleteBlockSuccess() {
        Log.d("message","Delete Success")
    }

    override fun onDeleteBlockFailure(code: Int, message: String) {
        when(code) {
            4006, 4007 -> {
                Log.d("message",message)
            }
            else -> {
                Log.d("message","SERVER ERROR")
            }
        }
    }


    fun getRVAFixedData() : ArrayList<FixedClothes> {
        val fixedClothes = arrayListOf<FixedClothes>()
        for(i in topList){
            if(i.id < 13){
                if(i.color != "#00ff0000"){
                    fixedClothes.apply{
                        add(FixedClothes(i.index,i.color))
                    }
                }
            }
        }
        return fixedClothes
    }

    fun getRVAAddedData() : ArrayList<AddedClothes> {
        val addedClothes = arrayListOf<AddedClothes>()
        for(i in topList) {
            if (i.id > 12) {
                if (i.color != "#00ff0000") {
                    addedClothes.apply {
                        add(AddedClothes("Top", i.name, i.color))
                    }
                }
            }
        }
        return addedClothes
    }

}