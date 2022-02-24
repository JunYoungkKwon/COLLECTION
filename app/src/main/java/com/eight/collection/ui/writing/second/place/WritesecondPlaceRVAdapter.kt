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


                setOnClickListener{
                    when(placeList[position].id){
                        0 -> {
                            clickListener?.plusButtonClick()
                            isChecked = false
                        }
                        else -> {
                            // 0개 선택
                            if(count == 0) {
                                placeList[position].focus = true
                                selectId = position
                                beforeId = position
                                count = count + 1
                            }


                            // 1개 선택
                            else if (count == 1) {
                                if (selectId == position) {
                                    placeList[position].focus = false
                                    count = count - 1
                                }
                                else {
                                    placeList[position].focus = true
                                    selectId = position
                                    count = count + 1
                                }

                            }

                            //2개 선택
                            else {
                                if(selectId == position) {
                                    placeList[selectId].focus = false
                                    selectId = beforeId
                                    count = count - 1
                                }
                                else if(beforeId == position) {
                                    placeList[beforeId].focus = false
                                    beforeId = selectId
                                    count = count - 1
                                }
                            }
                        }
                    }
                    notifyDataSetChanged()
                }
            }
            binding.writesecondPlaceDeleteButton.apply {
                if(placeList[position].id < 9) {
                    visibility = View.GONE
                }
                else {
                    visibility = View.VISIBLE
                    setOnClickListener{
                        when (placeList[position].id){
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