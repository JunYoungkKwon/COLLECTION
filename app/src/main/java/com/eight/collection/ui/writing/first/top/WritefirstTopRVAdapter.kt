package com.eight.collection.ui.writing.first.top

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWritefirstTopBinding
import android.graphics.Color
import com.eight.collection.utils.*

class WritefirstTopRVAdapter(private val topList: ArrayList<TopFixedItem>) : RecyclerView.Adapter<WritefirstTopRVAdapter.ViewHolder>(){
    private var selectCheck : ArrayList<Int> = arrayListOf()
    private var clickListener: TopClickListener? = null
    var data : String? = getColor()
    var selectedId : Int? = getSelectedId()

    init {
        for(i in topList){
            if(i.name == "-"){
                selectCheck.add(1)
            }
            else{
                selectCheck.add(0)
            }
        }
    }


    interface TopClickListener {
        fun plusButtonClick()
    }


    fun setTopClickListener(topClickListener: TopClickListener) {
        this.clickListener = topClickListener
    }



    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemWritefirstTopBinding = ItemWritefirstTopBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(topList[position], position)
    }

    override fun getItemCount(): Int = topList.size


    inner class ViewHolder(val binding: ItemWritefirstTopBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(top: TopFixedItem, position: Int){
            binding.writefirstColorTopTextButton.apply {
                text = top.name
                // select 여부 확인 및 상태 변경
                isChecked = selectCheck[bindingAdapterPosition] == 1


                setOnClickListener{
                    when(topList[position].id){
                        0 -> clickListener?.plusButtonClick()
                        else -> {
                            for (k in selectCheck.indices) {
                                if (k == bindingAdapterPosition) {
                                    selectCheck[k] = 1
                                    removeSelectedId()
                                    setSelectedId(k)
                                }
                                else {
                                    selectCheck[k] = 0
                                }
                            }
                        }
                    }
                    notifyDataSetChanged()
                }


                if(selectedId==position){
                    when(topList[position].color) {
                        "red" -> {
                            setBackgroundColor(Color.parseColor("#d60f0f"))
                            setTextColor(Color.parseColor("#ffffff"))
                            removeColor()
                        }

                        "pink" -> {
                            setBackgroundColor(Color.parseColor("#f59a9a"))
                            setTextColor(Color.parseColor("#ffffff"))
                            removeColor()
                        }

                        "yellow" -> {
                            setBackgroundColor(Color.parseColor("#ffb203"))
                            setTextColor(Color.parseColor("#ffffff"))
                            removeColor()
                        }

                        "lightyellow" -> {
                            setBackgroundColor(Color.parseColor("#fde6b1"))
                            setTextColor(Color.parseColor("#191919"))
                            removeColor()
                        }

                        "green" -> {
                            setBackgroundColor(Color.parseColor("#71a238"))
                            setTextColor(Color.parseColor("#ffffff"))
                            removeColor()
                        }

                        "lightgreen" -> {
                            setBackgroundColor(Color.parseColor("#b7de89"))
                            setTextColor(Color.parseColor("#191919"))
                            removeColor()
                        }

                        "orange" -> {
                            setBackgroundColor(Color.parseColor("#ea7831"))
                            setTextColor(Color.parseColor("#ffffff"))
                            removeColor()
                        }

                        "navy" -> {
                            setBackgroundColor(Color.parseColor("#273e88"))
                            setTextColor(Color.parseColor("#ffffff"))
                            removeColor()
                        }

                        "blue" -> {
                            setBackgroundColor(Color.parseColor("#4168e8"))
                            setTextColor(Color.parseColor("#ffffff"))
                            removeColor()
                        }

                        "lightblue" -> {
                            setBackgroundColor(Color.parseColor("#a5b9fa"))
                            setTextColor(Color.parseColor("#ffffff"))
                            removeColor()
                        }

                        "purple" -> {
                            setBackgroundColor(Color.parseColor("#894ac7"))
                            setTextColor(Color.parseColor("#ffffff"))
                            removeColor()
                        }

                        "lightpurple" -> {
                            setBackgroundColor(Color.parseColor("#dcacff"))
                            setTextColor(Color.parseColor("#ffffff"))
                            removeColor()
                        }

                        "white" -> {
                            setBackgroundColor(Color.parseColor("#ffffff"))
                            setTextColor(Color.parseColor("#191919"))
                            removeColor()
                        }

                        "grey" -> {
                            setBackgroundColor(Color.parseColor("#888888"))
                            setTextColor(Color.parseColor("#ffffff"))
                            removeColor()
                        }

                        "black" -> {
                            setBackgroundColor(Color.parseColor("#191919"))
                            setTextColor(Color.parseColor("#ffffff"))
                            removeColor()
                        }

                        "lightpeach" -> {
                            setBackgroundColor(Color.parseColor("#e8dcd5"))
                            setTextColor(Color.parseColor("#191919"))
                            removeColor()
                        }

                        "pinkishgrey" -> {
                            setBackgroundColor(Color.parseColor("#c3b5ac"))
                            setTextColor(Color.parseColor("#ffffff"))
                            removeColor()
                        }

                        "brown" -> {
                            setBackgroundColor(Color.parseColor("#74461f"))
                            setTextColor(Color.parseColor("#ffffff"))
                            removeColor()
                        }
                    }
                }
            }
        }
    }




    // 데이터 추가 메소드 (데이터 및 삭제아이콘 추가)
    fun addItem(top: TopFixedItem){
        topList.add(top)
        notifyDataSetChanged()
    }

    // 데이터 삭제 메소드
    fun removeItem(position: Int){
        topList.removeAt(position)
        notifyDataSetChanged()
    }

}