package com.eight.collection.ui.writing.second.weather

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.data.entities.Write.Block
import com.eight.collection.data.remote.deleteblock.DeleteBlockService
import com.eight.collection.databinding.ItemWritesecondPlaceBinding
import com.eight.collection.databinding.ItemWritesecondWeatherBinding
import com.eight.collection.ui.writing.DeleteBlockView
import com.eight.collection.ui.writing.second.AddedPlace
import com.eight.collection.ui.writing.second.AddedWeather
import com.eight.collection.ui.writing.second.FixedPlace
import com.eight.collection.ui.writing.second.FixedWeather

class WritesecondWeatherRVAdapter(private val weatherList: ArrayList<WritesecondWeather>) : RecyclerView.Adapter<WritesecondWeatherRVAdapter.ViewHolder>(), DeleteBlockView{
    private var clickListener: WeatherClickListener? = null
    private var count : Int = 0
    private var selectId : Int = -1
    private var beforeId : Int = -1

    interface WeatherClickListener {
        fun plusButtonClick()
        fun scrollButtonClickFirst()
        fun scrollButtonClickSecond()
        fun scrollButtonClickThird()
        fun scrollButtonClickFourth()
    }

    fun setWeatherClickListener(weatherClickListener: WeatherClickListener) {
        this.clickListener = weatherClickListener
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemWritesecondWeatherBinding = ItemWritesecondWeatherBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.writesecondWeatherTextButton.isChecked = weatherList[position].focus
        holder.bind(weatherList[position],position)
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int = weatherList.size


    inner class ViewHolder(val binding: ItemWritesecondWeatherBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(weather: WritesecondWeather, position: Int){
            binding.writesecondWeatherTextButton.apply {
                if(weatherList[position].id < 9) {
                    text = weather.name
                }
                else {
                    text = weather.name + "    "
                }
                // select ?????? ?????? ??? ?????? ??????
                setOnClickListener {
                    if (weatherList[position].id > 11 && weatherList[position].id < 18) {
                        clickListener?.scrollButtonClickFirst()
                    }

                    else if(weatherList[position].id > 17 && weatherList[position].id < 23){
                        clickListener?.scrollButtonClickSecond()
                    }

                    else if(weatherList[position].id > 22 && weatherList[position].id < 28){
                        clickListener?.scrollButtonClickThird()
                    }

                    else if(weatherList[position].id > 27 && weatherList[position].id < 33){
                        clickListener?.scrollButtonClickFourth()
                    }
                    when (weatherList[position].id) {
                        0 -> {
                            clickListener?.plusButtonClick()
                            isChecked = false
                        }
                        else -> {
                            // ?????? ?????????
                            if (selectId == -1) {
                                weatherList[position].focus = true
                                selectId = position
                            }
                            // ???????????? ?????? ?????????
                            else if (selectId == position) {
                                weatherList[selectId].focus = false
                                selectId = -1
                            }
                            // ?????????????????? ????????? ?????????
                            else {
                                weatherList[selectId].focus = false
                                weatherList[position].focus = true
                                selectId = position
                            }
                        }
                    }
                    notifyDataSetChanged()
                }
            }
            binding.writesecondWeatherDeleteButton.apply {
                if (weatherList[position].id < 9) {
                    visibility = View.GONE
                } else {
                    visibility = View.VISIBLE
                    setOnClickListener{
                        when (weatherList[position].id){
                            0 -> {}
                            else -> {
                                removeItem(position)
                                if(position == selectId){
                                    selectId = -1
                                }
                                else if(position < selectId){
                                    selectId = selectId - 1
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // ????????? ?????? ????????? (????????? ??? ??????????????? ??????)
    fun addItem(weather: WritesecondWeather){
        weatherList.add(weather)
        notifyDataSetChanged()
    }

    // ????????? ?????? ?????????
    fun removeItem(position: Int){
        deleteBlock(weatherList[position].name.toString())
        weatherList.removeAt(position)
        notifyDataSetChanged()
    }

    fun getSelectId() : Int{
        return selectId
    }

    fun setSelectId(setId : Int){
        selectId = setId
    }

    private fun getBlock(content : String) : Block {
        val clothes : Int = -1
        val pww : Int = 1
        return Block(clothes,pww,content)
    }

    private fun deleteBlock(content : String) {
        DeleteBlockService.deleteBlock(this, getBlock(content))
    }

    override fun onDeleteBlockLoading() {

    }

    override fun onDeleteBlockSuccess() {
        Log.d("message","Delete Success")
    }

    override fun onDeleteBlockFailure(code: Int, message: String) {
        when(code) {
            4006, 4007 -> {
                Log.d("message",message)
            }
            else -> {
                Log.d("message","SERVER ERROR")
            }
        }
    }

    fun getRVAFixedData() : ArrayList<Int> {
        val fixedWeather = arrayListOf<Int>()
        for(i in weatherList){
            if(i.id < 9){
                if(i.focus == true){
                    fixedWeather.apply{
                        add(i.index)
                    }
                }
            }
        }
        return fixedWeather
    }

    fun getRVAAddedData() : ArrayList<String> {
        val addedWeather = arrayListOf<String>()
        for(i in weatherList) {
            if (i.id > 8) {
                if (i.focus == true) {
                    addedWeather.apply {
                        add(i.name)
                    }
                }
            }
        }
        return addedWeather
    }
}