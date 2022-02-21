package com.eight.collection.ui.writing.first.bottom

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWritefirstBottomBinding
import com.eight.collection.databinding.ItemWritefirstTopBinding
import com.eight.collection.ui.writing.first.WritefirstActivity

class WritefirstBottomRVAdapter(private val bottomList: ArrayList<WritefirstBottom>) : RecyclerView.Adapter<WritefirstBottomRVAdapter.ViewHolder>(){
    private var selectCheck : ArrayList<Int> = arrayListOf()
    private var clickListener: BottomClickListener? = null
    private var selectId : Int = -1
    private var count : Int = 0
    private var beforeselectedId : RadioButton? = null

    init {
        for(i in bottomList){
            if(i.name == "-"){
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

    override fun getItemCount(): Int = bottomList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bottomList[position], position)
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
                        0 -> clickListener?.plusButtonClick()
                        else -> {
                            for (i in selectCheck.indices) {
                                if (i == bindingAdapterPosition) {
                                    if(count < 1){
                                        isChecked = true
                                        selectCheck[i] = 1
                                        selectId = bindingAdapterPosition
                                        count = count + 1
                                    }
                                    else {
                                        isChecked = false
                                        selectCheck[i] = 0
                                        selectId = -1
                                        count = count - 1
                                    }
                                }
                            }
                        }
                    }
                    notifyItemChanged(position)
                    if (beforeselectedId != null){
                        beforeselectedId?.isChecked = false
                    }
                    beforeselectedId = this
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