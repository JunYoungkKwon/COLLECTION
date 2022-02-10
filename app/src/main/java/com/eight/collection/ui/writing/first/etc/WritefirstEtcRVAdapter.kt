package com.eight.collection.ui.writing.first.etc

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWritefirstBottomBinding
import com.eight.collection.databinding.ItemWritefirstEtcBinding
import com.eight.collection.databinding.ItemWritefirstTopBinding
import com.eight.collection.ui.writing.first.WritefirstActivity

class WritefirstEtcRVAdapter(private val etcList: ArrayList<WritefirstEtc>) : RecyclerView.Adapter<WritefirstEtcRVAdapter.ViewHolder>(){
    private var selectCheck : ArrayList<Int> = arrayListOf()
    private var clickListener: EtcClickListener? = null

    init {
        for(i in etcList){
            if(i.title == "-"){
                selectCheck.add(1)
            }
            else{
                selectCheck.add(0)
            }
        }
    }


    interface EtcClickListener {
        fun plusButtonClick()
    }


    fun setEtcClickListener(etcClickListener: EtcClickListener) {
        this.clickListener = etcClickListener
    }

    interface ColorClickListner {
        fun colorTextPost()
    }



    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemWritefirstEtcBinding = ItemWritefirstEtcBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(etcList[position], position)
    }

    override fun getItemCount(): Int = etcList.size


    inner class ViewHolder(val binding: ItemWritefirstEtcBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(etc: WritefirstEtc, position: Int){
            binding.writefirstColorEtcTextButton.apply {
                text = etc.title
                // select 여부 확인 및 상태 변경
                isChecked = selectCheck[bindingAdapterPosition] == 1
                setOnClickListener{
                    when(etcList[position].id){
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
    fun addItem(etc: WritefirstEtc){
        etcList.add(etc)
        notifyDataSetChanged()
    }

    // 데이터 삭제 메소드
    fun removeItem(position: Int){
        etcList.removeAt(position)
        notifyDataSetChanged()
    }
}