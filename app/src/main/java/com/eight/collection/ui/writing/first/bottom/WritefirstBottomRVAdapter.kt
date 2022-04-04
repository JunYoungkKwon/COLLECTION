package com.eight.collection.ui.writing.first.bottom

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.data.entities.Write.Block
import com.eight.collection.data.remote.deleteblock.DeleteBlockService
import com.eight.collection.databinding.ItemWritefirstBottomBinding
import com.eight.collection.ui.writing.DeleteBlockView
import com.eight.collection.ui.writing.first.AddedClothes
import com.eight.collection.ui.writing.first.FixedClothes

class WritefirstBottomRVAdapter(private val bottomList: ArrayList<WritefirstBottom>) : RecyclerView.Adapter<WritefirstBottomRVAdapter.ViewHolder>(), DeleteBlockView{
    private var clickListener: BottomClickListener? = null
    private var selectId : Int = -1

    //Add 버튼 클릭시 데이터 추가
    interface BottomClickListener {
        fun plusButtonClick()
        fun scrollButtonClickFirst()
        fun scrollButtonClickSecond()
        fun scrollButtonClickThird()
        fun scrollButtonClickFourth()
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
                if(bottomList[position].id < 13) {
                    text = bottom.name
                }
                else {
                    text = bottom.name + "    "
                }
                // select 여부 확인 및 상태 변경
                setOnClickListener {
                    if (bottomList[position].id > 11 && bottomList[position].id < 18) {
                        clickListener?.scrollButtonClickFirst()
                    }

                    else if(bottomList[position].id > 17 && bottomList[position].id < 23){
                        clickListener?.scrollButtonClickSecond()
                    }

                    else if(bottomList[position].id > 22 && bottomList[position].id < 28){
                        clickListener?.scrollButtonClickThird()
                    }

                    else if(bottomList[position].id > 27 && bottomList[position].id < 33){
                        clickListener?.scrollButtonClickFourth()
                    }

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
            binding.writefirstColorBottomDeleteButton.apply {
                if(bottomList[position].id < 13) {
                    visibility = View.GONE
                }
                else {
                    visibility = View.VISIBLE
                    setOnClickListener{
                        when (bottomList[position].id){
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


    // 데이터 추가 메소드 (데이터 및 삭제아이콘 추가)
    fun addItem(top: WritefirstBottom){
        bottomList.add(top)
    }

    // 데이터 삭제 메소드
    fun removeItem(position: Int){
        deleteBlock(bottomList[position].name.toString())
        bottomList.removeAt(position)
        notifyDataSetChanged()
    }

    fun getSelectId() : Int{
        return selectId
    }

    fun setSelectId(setId : Int){
        selectId = setId
    }

    private fun getBlock(content : String) : Block {
        val clothes : Int = 1
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
        for(i in bottomList){
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
        for(i in bottomList) {
            if (i.id > 12) {
                if (i.color != "#00ff0000") {
                    addedClothes.apply {
                        add(AddedClothes("Bottom", i.name, i.color))
                    }
                }
            }
        }
        return addedClothes
    }

    fun getSelectIs() : Int {
        var selectIs : Int = 0
        for (i in bottomList) {
            if (i.focus == true){
                selectIs = 1
            }
        }
        return selectIs
    }
}