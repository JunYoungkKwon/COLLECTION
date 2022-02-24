package com.eight.collection.ui.writing.second.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWritesecondPlaceBinding
import com.eight.collection.databinding.ItemWritesecondWeatherBinding

class WritesecondWeatherRVAdapter(private val weatherList: ArrayList<WritesecondWeather>) : RecyclerView.Adapter<WritesecondWeatherRVAdapter.ViewHolder>(){
    private var clickListener: WeatherClickListener? = null
    private var count : Int = 0
    private var selectId : Int = -1
    private var beforeId : Int = -1

    interface WeatherClickListener {
        fun plusButtonClick()
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
                // select 여부 확인 및 상태 변경
                setOnClickListener{
                    when(weatherList[position].id){
                        0 -> {
                            clickListener?.plusButtonClick()
                            isChecked = false
                        }
                        else -> {
                            // 0개 선택
                            if(count == 0) {
                                weatherList[position].focus = true
                                selectId = position
                                beforeId = position
                                count = count + 1
                            }


                            // 1개 선택
                            else if (count == 1) {
                                if (selectId == position) {
                                    weatherList[position].focus = false
                                    count = count - 1
                                }
                                else {
                                    weatherList[position].focus = true
                                    selectId = position
                                    count = count + 1
                                }

                            }

                            //2개 선택
                            else {
                                if(selectId == position) {
                                    weatherList[selectId].focus = false
                                    selectId = beforeId
                                    count = count - 1
                                }
                                else if(beforeId == position) {
                                    weatherList[beforeId].focus = false
                                    beforeId = selectId
                                    count = count - 1
                                }
                            }
                        }
                    }
                    notifyDataSetChanged()
                }
            }
            binding.writesecondWeatherDeleteButton.apply {
                if(weatherList[position].id < 9) {
                    visibility = View.GONE
                }
                else {
                    visibility = View.VISIBLE
                    setOnClickListener{
                        when (weatherList[position].id){
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
    fun addItem(who: WritesecondWeather){
        weatherList.add(who)
        notifyDataSetChanged()
    }

    // 데이터 삭제 메소드
    fun removeItem(position: Int){
        weatherList.removeAt(position)
        notifyDataSetChanged()
    }
}