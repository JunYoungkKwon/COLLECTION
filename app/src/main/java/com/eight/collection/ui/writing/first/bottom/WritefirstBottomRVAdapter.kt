package com.eight.collection.ui.writing.first.bottom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWritefirstBottomBinding
import com.eight.collection.databinding.ItemWritefirstTopBinding
import com.eight.collection.ui.writing.first.WritefirstActivity

class WritefirstBottomRVAdapter(private val bottomList: ArrayList<WritefirstBottom>) : RecyclerView.Adapter<WritefirstBottomRVAdapter.ViewHolder>(){
    private var selectCheck : ArrayList<Int> = arrayListOf()
    private var clickListener: BottomClickListener? = null

    init {
        for(i in bottomList){
            if(i.title == "-"){
                selectCheck.add(1)
            }
            else{
                selectCheck.add(0)
            }
        }
    }


    interface BottomClickListener {
        fun plusButtonClick()
    }


    fun setBottomClickListener(bottomClickListener: BottomClickListener) {
        this.clickListener = bottomClickListener
    }

    interface ColorClickListner {
        fun colorTextPost()
    }



    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemWritefirstBottomBinding = ItemWritefirstBottomBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bottomList[position], position)
    }

    override fun getItemCount(): Int = bottomList.size


    inner class ViewHolder(val binding: ItemWritefirstBottomBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(bottom: WritefirstBottom, position: Int){
            binding.writefirstColorBottomTextButton.apply {
                text = bottom.title
                // select 여부 확인 및 상태 변경
                isChecked = selectCheck[bindingAdapterPosition] == 1
                setOnClickListener{
                    when(bottomList[position].id){
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
    fun addItem(top: WritefirstBottom){
        bottomList.add(top)
        notifyDataSetChanged()
    }

    // 데이터 삭제 메소드
    fun removeItem(position: Int){
        bottomList.removeAt(position)
        notifyDataSetChanged()
    }
}