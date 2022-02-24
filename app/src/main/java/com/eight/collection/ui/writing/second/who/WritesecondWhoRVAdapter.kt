package com.eight.collection.ui.writing.second.who

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWritesecondWhoBinding

class WritesecondWhoRVAdapter(private val whoList: ArrayList<WritesecondWho>) : RecyclerView.Adapter<WritesecondWhoRVAdapter.ViewHolder>(){
    private var clickListener: WhoClickListener? = null
    private var count : Int = 0
    private var selectId : Int = -1
    private var beforeId : Int = -1


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
                // select 여부 확인 및 상태 변경
                setOnClickListener{
                    when(whoList[position].id){
                        0 -> {
                            clickListener?.plusButtonClick()
                            isChecked = false
                        }
                        else -> {
                            // 0개 선택
                            if(count == 0) {
                                whoList[position].focus = true
                                selectId = position
                                beforeId = position
                                count = count + 1
                            }


                            // 1개 선택
                            else if (count == 1) {
                                if (selectId == position) {
                                    whoList[position].focus = false
                                    count = count - 1
                                }
                                else {
                                    whoList[position].focus = true
                                    selectId = position
                                    count = count + 1
                                }

                            }

                            //2개 선택
                            else {
                                if(selectId == position) {
                                    whoList[selectId].focus = false
                                    selectId = beforeId
                                    count = count - 1
                                }
                                else if(beforeId == position) {
                                    whoList[beforeId].focus = false
                                    beforeId = selectId
                                    count = count - 1
                                }
                            }
                        }
                    }
                    notifyDataSetChanged()
                }
            }
            binding.writesecondWhoDeleteButton.apply {
                if(whoList[position].id < 7) {
                    visibility = View.GONE
                }
                else {
                    visibility = View.VISIBLE
                    setOnClickListener{
                        when (whoList[position].id){
                            0 -> {}
                            else -> {
                                removeItem(position)
                            }
                        }
                    }
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