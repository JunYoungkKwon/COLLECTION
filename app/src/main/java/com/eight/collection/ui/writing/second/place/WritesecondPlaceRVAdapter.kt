package com.eight.collection.ui.writing.second.place

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWritesecondPlaceBinding

class WritesecondPlaceRVAdapter(private val placeList: ArrayList<WritesecondPlace>) : RecyclerView.Adapter<WritesecondPlaceRVAdapter.ViewHolder>(){
    private var selectCheck : ArrayList<Int> = arrayListOf()
    private var clickListener: PlaceClickListener? = null
    private var count : Int = 0

    init {
        for(i in placeList){
            if(i.title == "-"){
                selectCheck.add(1)
            }
            else{
                selectCheck.add(0)
            }
        }
    }

    interface PlaceClickListener {
        fun plusButtonClick()
    }

    fun setPlaceClickListener(placeClickListener: PlaceClickListener) {
        this.clickListener = placeClickListener
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemWritesecondPlaceBinding = ItemWritesecondPlaceBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(placeList[position],position)
    }

    override fun getItemCount(): Int = placeList.size


    inner class ViewHolder(val binding: ItemWritesecondPlaceBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(place: WritesecondPlace, position: Int){
            binding.writesecondPlaceTextButton.apply {
                text = place.title
                // select 여부 확인 및 상태 변경
                isChecked = selectCheck[bindingAdapterPosition] == 1
                setOnClickListener{
                    when(placeList[position].id){
                        0 -> clickListener?.plusButtonClick()
                        else -> {
                                for (k in selectCheck.indices) {
                                    if (k == bindingAdapterPosition) {
                                        if(count < 2) {
                                            if (selectCheck[k] == 1) {
                                                selectCheck[k] = 0
                                                count = count - 1
                                            } else {
                                                selectCheck[k] = 1
                                                count = count + 1
                                            }
                                        }
                                        else {
                                            if (selectCheck[k] == 1) {
                                                selectCheck[k] = 0
                                                count = count - 1
                                            }
                                        }
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
    fun addItem(place: WritesecondPlace){
        placeList.add(place)
        notifyDataSetChanged()
    }

    // 데이터 삭제 메소드
    fun removeItem(position: Int){
        placeList.removeAt(position)
        notifyDataSetChanged()
    }
}