package com.eight.collection.ui.writing.second.who

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWritesecondWhoBinding

class WritesecondWhoRVAdapter(private val whoList: ArrayList<WritesecondWho>) : RecyclerView.Adapter<WritesecondWhoRVAdapter.ViewHolder>(){
    private var selectCheck : ArrayList<Int> = arrayListOf()
    private var clickListener: WhoClickListener? = null

    init {
        for(i in whoList){
            if(i.title == "-"){
                selectCheck.add(1)
            }
            else{
                selectCheck.add(0)
            }
        }
    }

    interface WhoClickListener {
        fun plusButtonClick()
    }

    fun setWhoClickListener(whoClickListener: WhoClickListener) {
        this.clickListener = whoClickListener
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemWritesecondWhoBinding = ItemWritesecondWhoBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(whoList[position],position)
    }

    override fun getItemCount(): Int = whoList.size


    inner class ViewHolder(val binding: ItemWritesecondWhoBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(who: WritesecondWho, position: Int){
            binding.writesecondWhoTextButton.apply {
                text = who.title
                // select 여부 확인 및 상태 변경
                isChecked = selectCheck[bindingAdapterPosition] == 1
                setOnClickListener{
                    when(whoList[position].id){
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
    fun addItem(who: WritesecondWho){
        whoList.add(who)
        notifyDataSetChanged()
    }

    // 데이터 삭제 메소드
    fun removeItem(position: Int){
        whoList.removeAt(position)
        notifyDataSetChanged()
    }
}