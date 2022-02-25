package com.eight.collection.ui.writing.first.shoes

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWritefirstBottomBinding
import com.eight.collection.databinding.ItemWritefirstShoesBinding
import com.eight.collection.databinding.ItemWritefirstTopBinding
import com.eight.collection.ui.writing.first.WritefirstActivity
import com.eight.collection.ui.writing.first.bottom.WritefirstBottomRVAdapter

class WritefirstShoesRVAdapter(private val shoesList: ArrayList<WritefirstShoes>) : RecyclerView.Adapter<WritefirstShoesRVAdapter.ViewHolder>(){
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
        shoesList.removeAt(position)
        notifyDataSetChanged()
    }

    fun getSelectId() : Int{
        return selectId
    }
}