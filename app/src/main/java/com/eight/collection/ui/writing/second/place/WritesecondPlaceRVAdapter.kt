package com.eight.collection.ui.writing.second.place

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWritesecondPlaceBinding

class WritesecondPlaceRVAdapter(private val placeList: ArrayList<WritesecondPlace>) : RecyclerView.Adapter<WritesecondPlaceRVAdapter.ViewHolder>(){
    private var clickListener: PlaceClickListener? = null
    private var count : Int = 0
    private var selectId : Int = -1
    private var beforeId : Int = -1

    interface PlaceClickListener {
        fun plusButtonClick()
    }

    fun setPlaceClickListener(placeClickListener: PlaceClickListener) {
        this.clickListener = placeClickListener
    }


    override fun getItemCount(): Int = placeList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.writesecondPlaceTextButton.isChecked = placeList[position].focus
        holder.bind(placeList[position],position)
        holder.setIsRecyclable(false)
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemWritesecondPlaceBinding = ItemWritesecondPlaceBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }


    inner class ViewHolder(val binding: ItemWritesecondPlaceBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(place: WritesecondPlace, position: Int){
            binding.writesecondPlaceTextButton.apply {
                if(placeList[position].id < 9) {
                    text = place.name
                }
                else {
                    text = place.name + "    "
                }

                setOnClickListener {
                    when (placeList[position].id) {
                        0 -> {
                            clickListener?.plusButtonClick()
                            isChecked = false
                        }
                        else -> {
                            // 처음 선택시
                            if (selectId == -1) {
                                placeList[position].focus = true
                                selectId = position
                            }
                            // 선택한거 다시 클릭시
                            else if (selectId == position) {
                                placeList[selectId].focus = false
                                selectId = -1
                            }
                            // 선택한거말고 다른거 클릭시
                            else {
                                placeList[selectId].focus = false
                                placeList[position].focus = true
                                selectId = position
                            }
                        }
                    }
                    notifyDataSetChanged()
                }
            }
            binding.writesecondPlaceDeleteButton.apply {
                if (placeList[position].id < 9) {
                    visibility = View.GONE
                } else {
                    visibility = View.VISIBLE
                    setOnClickListener{
                        when (placeList[position].id){
                            0 -> {}
                            else -> {
                                removeItem(position)
                                if(position < selectId){
                                    selectId = selectId - 1
                                }
                            }
                        }
                    }
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