package com.eight.collection.ui.writing.second.who

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.data.entities.Write.Block
import com.eight.collection.data.remote.deleteblock.DeleteBlockService
import com.eight.collection.databinding.ItemWritesecondWhoBinding
import com.eight.collection.ui.writing.DeleteBlockView
import com.eight.collection.ui.writing.second.AddedPlace
import com.eight.collection.ui.writing.second.AddedWho
import com.eight.collection.ui.writing.second.FixedPlace
import com.eight.collection.ui.writing.second.FixedWho

class WritesecondWhoRVAdapter(private val whoList: ArrayList<WritesecondWho>) : RecyclerView.Adapter<WritesecondWhoRVAdapter.ViewHolder>(), DeleteBlockView{
    private var clickListener: WhoClickListener? = null
    private var count : Int = 0
    private var selectId : Int = -1
    private var beforeId : Int = -1


    interface WhoClickListener {
        fun plusButtonClick()
        fun scrollButtonClickFirst()
        fun scrollButtonClickSecond()
        fun scrollButtonClickThird()
        fun scrollButtonClickFourth()
    }

    fun setWhoClickListener(whoClickListener: WhoClickListener) {
        this.clickListener = whoClickListener
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemWritesecondWhoBinding = ItemWritesecondWhoBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.writesecondWhoTextButton.isChecked = whoList[position].focus
        holder.bind(whoList[position],position)
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int = whoList.size


    inner class ViewHolder(val binding: ItemWritesecondWhoBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(who: WritesecondWho, position: Int){
            binding.writesecondWhoTextButton.apply {
                if(whoList[position].id < 7) {
                    text = who.name
                }
                else {
                    text = who.name + "    "
                }
                // select ?????? ?????? ??? ?????? ??????
                setOnClickListener {
                    if (whoList[position].id > 11 && whoList[position].id < 18) {
                        clickListener?.scrollButtonClickFirst()
                    }

                    else if(whoList[position].id > 17 && whoList[position].id < 23){
                        clickListener?.scrollButtonClickSecond()
                    }

                    else if(whoList[position].id > 22 && whoList[position].id < 28){
                        clickListener?.scrollButtonClickThird()
                    }

                    else if(whoList[position].id > 27 && whoList[position].id < 33){
                        clickListener?.scrollButtonClickFourth()
                    }
                    when (whoList[position].id) {
                        0 -> {
                            clickListener?.plusButtonClick()
                            isChecked = false
                        }
                        else -> {
                            // ?????? ?????????
                            if (selectId == -1) {
                                whoList[position].focus = true
                                selectId = position
                            }
                            // ???????????? ?????? ?????????
                            else if (selectId == position) {
                                whoList[selectId].focus = false
                                selectId = -1
                            }
                            // ?????????????????? ????????? ?????????
                            else {
                                whoList[selectId].focus = false
                                whoList[position].focus = true
                                selectId = position
                            }
                        }
                    }
                    notifyDataSetChanged()
                }
            }
            binding.writesecondWhoDeleteButton.apply {
                if (whoList[position].id < 7) {
                    visibility = View.GONE
                } else {
                    visibility = View.VISIBLE
                    setOnClickListener{
                        when (whoList[position].id){
                            0 -> {}
                            else -> {
                                removeItem(position)
                                if(position == selectId){
                                    selectId = -1
                                }
                                else if(position < selectId){
                                    selectId = selectId - 1
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // ????????? ?????? ????????? (????????? ??? ??????????????? ??????)
    fun addItem(who: WritesecondWho){
        whoList.add(who)
        notifyDataSetChanged()
    }

    // ????????? ?????? ?????????
    fun removeItem(position: Int){
        deleteBlock(whoList[position].name.toString())
        whoList.removeAt(position)
        notifyDataSetChanged()
    }

    fun getSelectId() : Int{
        return selectId
    }

    fun setSelectId(setId : Int){
        selectId = setId
    }

    private fun getBlock(content : String) : Block {
        val clothes : Int = -1
        val pww : Int = 2
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

    fun getRVAFixedData() : ArrayList<Int> {
        val fixedWho = arrayListOf<Int>()
        for(i in whoList){
            if(i.id < 7){
                if(i.focus == true){
                    fixedWho.apply{
                        add(i.index)
                    }
                }
            }
        }
        return fixedWho
    }

    fun getRVAAddedData() : ArrayList<String> {
        val addedWho = arrayListOf<String>()
        for(i in whoList) {
            if (i.id > 6) {
                if (i.focus == true) {
                    addedWho.apply {
                        add(i.name)
                    }
                }
            }
        }
        return addedWho
    }
}