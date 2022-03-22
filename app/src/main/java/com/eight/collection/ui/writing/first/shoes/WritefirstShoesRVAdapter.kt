package com.eight.collection.ui.writing.first.shoes

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.data.entities.Write.Block
import com.eight.collection.data.remote.deleteblock.DeleteBlockService
import com.eight.collection.databinding.ItemWritefirstShoesBinding
import com.eight.collection.ui.writing.DeleteBlockView
import com.eight.collection.ui.writing.first.AddedClothes
import com.eight.collection.ui.writing.first.FixedClothes

class WritefirstShoesRVAdapter(private val shoesList: ArrayList<WritefirstShoes>) : RecyclerView.Adapter<WritefirstShoesRVAdapter.ViewHolder>(),
    DeleteBlockView {
    private var clickListener: ShoesClickListener? = null
    private var selectId : Int = -1


    interface ShoesClickListener {
        fun plusButtonClick()
    }


    fun setShoesClickListener(shoesClickListener: ShoesClickListener) {
        this.clickListener = shoesClickListener
    }

    override fun getItemCount(): Int = shoesList.size


    override fun onBindViewHolder(holder: WritefirstShoesRVAdapter.ViewHolder, position: Int) {
        holder.binding.writefirstColorShoesTextButton.isChecked = shoesList[position].focus
        holder.binding.writefirstShoesItemLayout.setBackgroundColor(Color.parseColor(shoesList[position].color))
        holder.binding.writefirstColorShoesTextButton.setTextColor(Color.parseColor(shoesList[position].textcolor))

        holder.bind(shoesList[position], position)
        holder.setIsRecyclable(false)
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemWritefirstShoesBinding = ItemWritefirstShoesBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }


    inner class ViewHolder(val binding: ItemWritefirstShoesBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(shoes: WritefirstShoes, position: Int){
            binding.writefirstColorShoesTextButton.apply {
                if(shoesList[position].id < 13) {
                    text = shoes.name
                }
                else {
                    text = shoes.name + "    "
                }
                setOnClickListener {
                    when (shoesList[position].id) {
                        0 -> {
                            clickListener?.plusButtonClick()
                            isChecked = false
                        }
                        else -> {
                            if(selectId == -1) {
                                shoesList[position].focus = true
                                selectId = position
                            }
                            else if(selectId == position) {
                                shoesList[selectId].focus = false
                                shoesList[selectId].color = "#00ff0000"
                                shoesList[selectId].textcolor = "#c3b5ac"
                                selectId = -1
                            }
                            else {
                                shoesList[selectId].focus = false
                                shoesList[position].focus = true
                                selectId = position
                            }
                        }
                    }
                    notifyDataSetChanged()
                }
            }
            binding.writefirstColorShoesDeleteButton.apply {
                if(shoesList[position].id < 13) {
                    visibility = View.GONE
                }
                else {
                    visibility = View.VISIBLE
                    setOnClickListener{
                        when (shoesList[position].id){
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
    fun addItem(shoes: WritefirstShoes){
        shoesList.add(shoes)
        notifyDataSetChanged()
    }

    // 데이터 삭제 메소드
    fun removeItem(position: Int){
        deleteBlock(shoesList[position].name.toString())
        shoesList.removeAt(position)
        notifyDataSetChanged()
    }

    fun getSelectId() : Int{
        return selectId
    }

    private fun getBlock(content : String) : Block {
        val clothes : Int = 2
        val pww : Int = -1
        return Block(clothes,pww,content)
    }

    fun setSelectId(setId : Int){
        selectId = setId
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
        for(i in shoesList){
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
        for(i in shoesList) {
            if (i.id > 12) {
                if (i.color != "#00ff0000") {
                    addedClothes.apply {
                        add(AddedClothes("Shoes", i.name, i.color))
                    }
                }
            }
        }
        return addedClothes
    }

    fun getSelectIs() : Int {
        var selectIs : Int = 0
        for (i in shoesList) {
            if (i.focus == true){
                selectIs = 1
            }
        }
        return selectIs
    }
}