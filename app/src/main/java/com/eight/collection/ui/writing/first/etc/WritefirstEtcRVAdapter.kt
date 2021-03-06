package com.eight.collection.ui.writing.first.etc

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.data.entities.Write.Block
import com.eight.collection.data.remote.deleteblock.DeleteBlockService
import com.eight.collection.databinding.ItemWritefirstEtcBinding
import com.eight.collection.ui.writing.DeleteBlockView
import com.eight.collection.ui.writing.first.AddedClothes
import com.eight.collection.ui.writing.first.FixedClothes

class WritefirstEtcRVAdapter(private val etcList: ArrayList<WritefirstEtc>) : RecyclerView.Adapter<WritefirstEtcRVAdapter.ViewHolder>(), DeleteBlockView{
    private var clickListener: EtcClickListener? = null
    private var selectId : Int = -1

    interface EtcClickListener {
        fun plusButtonClick()
        fun scrollButtonClickFirst()
        fun scrollButtonClickSecond()
        fun scrollButtonClickThird()
        fun scrollButtonClickFourth()
    }


    fun setEtcClickListener(etcClickListener: EtcClickListener) {
        this.clickListener = etcClickListener
    }

    override fun getItemCount(): Int = etcList.size

    override fun onBindViewHolder(holder: WritefirstEtcRVAdapter.ViewHolder, position: Int) {
        holder.binding.writefirstColorEtcTextButton.isChecked = etcList[position].focus
        holder.binding.writefirstEtcItemLayout.setBackgroundColor(Color.parseColor(etcList[position].color))
        holder.binding.writefirstColorEtcTextButton.setTextColor(Color.parseColor(etcList[position].textcolor))

        holder.bind(etcList[position], position)
        holder.setIsRecyclable(false)
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemWritefirstEtcBinding = ItemWritefirstEtcBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }


    inner class ViewHolder(val binding: ItemWritefirstEtcBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(etc: WritefirstEtc, position: Int){
            binding.writefirstColorEtcTextButton.apply {
                if(etcList[position].id < 13) {
                    text = etc.name
                }
                else {
                    text = etc.name + "    "
                }
                setOnClickListener {
                    if (etcList[position].id > 11 && etcList[position].id < 18) {
                        clickListener?.scrollButtonClickFirst()
                    }

                    else if(etcList[position].id > 17 && etcList[position].id < 23){
                        clickListener?.scrollButtonClickSecond()
                    }

                    else if(etcList[position].id > 22 && etcList[position].id < 28){
                        clickListener?.scrollButtonClickThird()
                    }

                    else if(etcList[position].id > 27 && etcList[position].id < 33){
                        clickListener?.scrollButtonClickFourth()
                    }

                    when (etcList[position].id) {
                        0 -> {
                            clickListener?.plusButtonClick()
                            isChecked = false
                        }
                        else -> {
                            if(selectId == -1) {
                                etcList[position].focus = true
                                selectId = position
                            }
                            else if(selectId == position) {
                                etcList[selectId].focus = false
                                etcList[selectId].color = "#00ff0000"
                                etcList[selectId].textcolor = "#c3b5ac"
                                selectId = -1

                            }
                            else {
                                etcList[selectId].focus = false
                                etcList[position].focus = true
                                selectId = position
                            }
                        }
                    }
                    notifyDataSetChanged()
                }
            }
            binding.writefirstColorEtcDeleteButton.apply {
                if(etcList[position].id < 13) {
                    visibility = View.GONE
                }
                else {
                    visibility = View.VISIBLE
                    setOnClickListener{
                        when (etcList[position].id){
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
    fun addItem(etc: WritefirstEtc){
        etcList.add(etc)
        notifyDataSetChanged()
    }

    // ????????? ?????? ?????????
    fun removeItem(position: Int){
        deleteBlock(etcList[position].name.toString())
        etcList.removeAt(position)
        notifyDataSetChanged()
    }

    fun getSelectId() : Int{
        return selectId
    }

    fun setSelectId(setId : Int){
        selectId = setId
    }

    private fun getBlock(content : String) : Block {
        val clothes : Int = 3
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
        for(i in etcList){
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
        for(i in etcList) {
            if (i.id > 12) {
                if (i.color != "#00ff0000") {
                    addedClothes.apply {
                        add(AddedClothes("Etc", i.name, i.color))
                    }
                }
            }
        }
        return addedClothes
    }

    fun getSelectIs() : Int {
        var selectIs : Int = 0
        for (i in etcList) {
            if (i.focus == true){
                selectIs = 1
            }
        }
        return selectIs
    }
}