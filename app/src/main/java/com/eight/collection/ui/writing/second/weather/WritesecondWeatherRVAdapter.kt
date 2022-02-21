package com.eight.collection.ui.writing.second.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.databinding.ItemWritesecondPlaceBinding
import com.eight.collection.databinding.ItemWritesecondWeatherBinding

class WritesecondWeatherRVAdapter(private val weatherList: ArrayList<WritesecondWeather>) : RecyclerView.Adapter<WritesecondWeatherRVAdapter.ViewHolder>(){
    private var selectCheck : ArrayList<Int> = arrayListOf()
    private var clickListener: WeatherClickListener? = null
    private var count : Int = 0

    init {
        for(i in weatherList){
            if(i.title == "-"){
                selectCheck.add(1)
            }
            else{
                selectCheck.add(0)
            }
        }
    }

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
        holder.bind(weatherList[position],position)
    }

    override fun getItemCount(): Int = weatherList.size


    inner class ViewHolder(val binding: ItemWritesecondWeatherBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(weather: WritesecondWeather, position: Int){
            binding.writesecondWeatherTextButton.apply {
                text = weather.title
                // select 여부 확인 및 상태 변경
                isChecked = selectCheck[bindingAdapterPosition] == 1
                setOnClickListener{
                    when(weatherList[position].id){
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