package com.eight.collection.ui.writing.second.place

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.data.entities.Write.Block
import com.eight.collection.data.remote.deleteblock.DeleteBlockService
import com.eight.collection.databinding.ItemWritesecondPlaceBinding
import com.eight.collection.ui.writing.DeleteBlockView
import com.eight.collection.ui.writing.first.AddedClothes
import com.eight.collection.ui.writing.first.FixedClothes
import com.eight.collection.ui.writing.second.AddedPlace
import com.eight.collection.ui.writing.second.FixedPlace

class WritesecondPlaceRVAdapter(private val placeList: ArrayList<WritesecondPlace>) : RecyclerView.Adapter<WritesecondPlaceRVAdapter.ViewHolder>(), DeleteBlockView{
    private var clickListener: PlaceClickListener? = null
    private var selectId : Int = -1

    /*fun setselectedId() {
        for(i in placeList){
            if(i.focus == true){
                selectId = i.id
            }
        }
    }*/

    interface PlaceClickListener {
        fun plusButtonClick()
        fun scrollButtonClickFirst()
        fun scrollButtonClickSecond()
        fun scrollButtonClickThird()
        fun scrollButtonClickFourth()
    }

    fun setPlaceClickListener(placeClickListener: PlaceClickListener) {
        this.clickListener = placeClickListener
    }




    override fun getItemCount(): Int = placeList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.writesecondPlaceTextButton.isChecked = placeList[position].focus
        holder.bind(placeList[position],position)
        holder.setIsRecyclable(false)
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemWritesecondPlaceBinding = ItemWritesecondPlaceBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }


    inner class ViewHolder(val binding: ItemWritesecondPlaceBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(place: WritesecondPlace, position: Int){
            binding.writesecondPlaceTextButton.apply {
                if(placeList[position].id < 9) {
                    text = place.name
                }
                else {
                    text = place.name + "    "
                }

                setOnClickListener {
                    if (placeList[position].id > 11 && placeList[position].id < 18) {
                        clickListener?.scrollButtonClickFirst()
                    }

                    else if(placeList[position].id > 17 && placeList[position].id < 23){
                        clickListener?.scrollButtonClickSecond()
                    }

                    else if(placeList[position].id > 22 && placeList[position].id < 28){
                        clickListener?.scrollButtonClickThird()
                    }

                    else if(placeList[position].id > 27 && placeList[position].id < 33){
                        clickListener?.scrollButtonClickFourth()
                    }

                    when (placeList[position].id) {
                        0 -> {
                            clickListener?.plusButtonClick()
                            isChecked = false
                        }
                        else -> {
                            Log.d("Item","${placeList}")
                            Log.d("selectId","${selectId}")

                            // 처음 선택시
                            if (selectId == -1) {
                                placeList[position].focus = true
                                selectId = position
                            }
                            // 선택한거 다시 클릭시
                            else if (selectId == position) {
                                placeList[selectId].focus = false
                                selectId = -1
                            }
                            // 선택한거말고 다른거 클릭시
                            else {
                                placeList[selectId].focus = false
                                placeList[position].focus = true
                                selectId = position
                            }
                        }
                    }

                    Log.d("클릭","클릭")
                    notifyDataSetChanged()
                }
            }
            binding.writesecondPlaceDeleteButton.apply {
                if (placeList[position].id < 9) {
                    visibility = View.GONE
                } else {
                    visibility = View.VISIBLE
                    setOnClickListener{
                        when (placeList[position].id){
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

    // 데이터 추가 메소드 (데이터 및 삭제아이콘 추가)
    fun addItem(place: WritesecondPlace){
        placeList.add(place)
        notifyDataSetChanged()
    }

    // 데이터 삭제 메소드
    fun removeItem(position: Int){
        deleteBlock(placeList[position].name.toString())
        placeList.removeAt(position)
        Log.d("placeList","${placeList}")
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
        val pww : Int = 0
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
        val fixedPlace = arrayListOf<Int>()
        for(i in placeList){
            if(i.id < 9){
                if(i.focus == true){
                    fixedPlace.apply{
                        add(i.index)
                    }
                }
            }
        }
        return fixedPlace
    }

    fun getRVAAddedData() : ArrayList<String> {
        val addedPlace = arrayListOf<String>()
        for(i in placeList) {
            if (i.id > 8) {
                if (i.focus == true) {
                    addedPlace.apply {
                        add(i.name)
                    }
                }
            }
        }
        return addedPlace
    }
}