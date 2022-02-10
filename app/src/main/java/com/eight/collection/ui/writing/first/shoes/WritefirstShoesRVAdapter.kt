package com.eight.collection.ui.writing.first.shoes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWritefirstBottomBinding
import com.eight.collection.databinding.ItemWritefirstShoesBinding
import com.eight.collection.databinding.ItemWritefirstTopBinding
import com.eight.collection.ui.writing.first.WritefirstActivity

class WritefirstShoesRVAdapter(private val shoesList: ArrayList<WritefirstShoes>) : RecyclerView.Adapter<WritefirstShoesRVAdapter.ViewHolder>(){
    private var selectCheck : ArrayList<Int> = arrayListOf()
    private var clickListener: ShoesClickListener? = null

    init {
        for(i in shoesList){
            if(i.title == "-"){
                selectCheck.add(1)
            }
            else{
                selectCheck.add(0)
            }
        }
    }


    interface ShoesClickListener {
        fun plusButtonClick()
    }


    fun setShoesClickListener(shoesClickListener: ShoesClickListener) {
        this.clickListener = shoesClickListener
    }

    interface ColorClickListner {
        fun colorTextPost()
    }



    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemWritefirstShoesBinding = ItemWritefirstShoesBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(shoesList[position], position)
    }

    override fun getItemCount(): Int = shoesList.size


    inner class ViewHolder(val binding: ItemWritefirstShoesBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(shoes: WritefirstShoes, position: Int){
            binding.writefirstColorShoesTextButton.apply {
                text = shoes.title
                // select 여부 확인 및 상태 변경
                isChecked = selectCheck[bindingAdapterPosition] == 1
                setOnClickListener{
                    when(shoesList[position].id){
                        0 -> clickListener?.plusButtonClick()
                        else -> {
                            for (k in selectCheck.indices) {
                                if (k == bindingAdapterPosition) {
                                    selectCheck[k] = 1
                                }
                                else {
                                    selectCheck[k] = 0
                                }
                            }
                        }
                    }

                    notifyDataSetChanged()
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
}