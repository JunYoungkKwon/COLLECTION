package com.eight.collection.ui.writing.second.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.eight.collection.databinding.FragmentWritesecondWeatherBinding
import com.eight.collection.ui.writing.CustomDialogInterface
import com.eight.collection.ui.writing.second.place.WritesecondPlace
import com.eight.collection.ui.writing.second.place.WritesecondPlaceCustomDialog
import com.eight.collection.ui.writing.second.place.WritesecondPlaceRVAdapter
import com.google.android.flexbox.FlexboxLayoutManager

class WritesecondWeatherFragment : Fragment(), CustomDialogInterface,
    WritesecondWeatherRVAdapter.WeatherClickListener {
    lateinit var binding : FragmentWritesecondWeatherBinding
    private var weatherDatas = ArrayList<WritesecondWeather>()
    lateinit var customDialog: WritesecondWeatherCustomDialog
    private var idcount : Int = 9
    private var addtext : String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWritesecondWeatherBinding.inflate(inflater,container,false)

        // 데이터 리스트 생성
        weatherDatas.apply {
            add(WritesecondWeather("+", 0))
            add(WritesecondWeather("매우추움", 1))
            add(WritesecondWeather("매우더움", 2))
            add(WritesecondWeather("추움", 3))
            add(WritesecondWeather("더움", 4))
            add(WritesecondWeather("적당함", 5))
            add(WritesecondWeather("눈", 6))
            add(WritesecondWeather("비", 7))
            add(WritesecondWeather("우박", 8))
        }

        val weatherRVAdapter = WritesecondWeatherRVAdapter(weatherDatas)
        weatherRVAdapter.setWeatherClickListener(this)

        val flexboxLayoutManager = FlexboxLayoutManager(activity)
        binding.writesecondWeatherRecyclerview.adapter = weatherRVAdapter
        binding.writesecondWeatherRecyclerview.layoutManager = flexboxLayoutManager


        return binding.root
    }

    override fun onAddButtonClicked(addText: String) {
        weatherDatas.apply {
            add(WritesecondWeather(addText,idcount))
            idcount += 1
        }

        val weatherRVAdapter = WritesecondWeatherRVAdapter(weatherDatas)
        weatherRVAdapter.setWeatherClickListener(this)

        val flexboxLayoutManager = FlexboxLayoutManager(activity)
        binding.writesecondWeatherRecyclerview.adapter = weatherRVAdapter
        binding.writesecondWeatherRecyclerview.layoutManager = flexboxLayoutManager
    }

    override fun onCancelButtonClicked() {
        Toast.makeText(requireContext(), "취소", Toast.LENGTH_SHORT).show()
    }

    override fun plusButtonClick() {
        customDialog = WritesecondWeatherCustomDialog(requireContext(), this)
        customDialog.show()
    }

}