package com.eight.collection.ui.writing.second.weather

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.eight.collection.data.remote.getaddedblock.GetAddedBlockResult
import com.eight.collection.data.remote.getaddedblock.GetAddedBlockService
import com.eight.collection.data.remote.modi.ModiResult
import com.eight.collection.data.remote.modi.ModiService
import com.eight.collection.databinding.FragmentWritesecondWeatherBinding
import com.eight.collection.ui.writing.CustomDialogInterface
import com.eight.collection.ui.writing.GetAddedBlockView
import com.eight.collection.ui.writing.ModiView
import com.eight.collection.ui.writing.second.*
import com.eight.collection.ui.writing.second.place.WritesecondPlace
import com.eight.collection.ui.writing.second.place.WritesecondPlaceCustomDialog
import com.eight.collection.ui.writing.second.place.WritesecondPlaceRVAdapter
import com.eight.collection.ui.writing.second.who.WritesecondWho
import com.google.android.flexbox.FlexboxLayoutManager

class WritesecondWeatherFragment : Fragment(), CustomDialogInterface,
    WritesecondWeatherRVAdapter.WeatherClickListener, WritesecondActivity.GetWeatherDataListener,
    GetAddedBlockView, ModiView, WritesecondActivity.RefreshWeatherDataListener {
    lateinit var binding : FragmentWritesecondWeatherBinding
    private var weatherDatas = ArrayList<WritesecondWeather>()
    lateinit var customDialog: WritesecondWeatherCustomDialog
    private var idcount : Int = 9
    var weatherRVAdapter : WritesecondWeatherRVAdapter = WritesecondWeatherRVAdapter(weatherDatas)
    var date : String = "2021-01-01"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWritesecondWeatherBinding.inflate(inflater,container,false)

        // 데이터 리스트 생성
        weatherDatas.apply {
            add(WritesecondWeather("+", 0,0))
            add(WritesecondWeather("매우추움", 1,1))
            add(WritesecondWeather("매우더움", 2,2))
            add(WritesecondWeather("추움", 3,3))
            add(WritesecondWeather("더움", 4,4))
            add(WritesecondWeather("적당함", 5,5))
            add(WritesecondWeather("눈", 6,6))
            add(WritesecondWeather("비", 7,7))
            add(WritesecondWeather("우박", 8,8))
        }

        getAddedBlock()

        modi()

        Handler(Looper.getMainLooper()).postDelayed({
            weatherRVAdapter = WritesecondWeatherRVAdapter(weatherDatas)
            weatherRVAdapter.setWeatherClickListener(this)

            val flexboxLayoutManager = FlexboxLayoutManager(activity)
            binding.writesecondWeatherRecyclerview.adapter = weatherRVAdapter
            binding.writesecondWeatherRecyclerview.layoutManager = flexboxLayoutManager
        }, 100)

        return binding.root
    }

    override fun onAddButtonClicked(addText: String) {
        weatherDatas.apply {
            add(WritesecondWeather(addText,idcount))
            idcount += 1
        }

        weatherRVAdapter.notifyDataSetChanged()
    }

    override fun onCancelButtonClicked() {
    }

    override fun plusButtonClick() {
        customDialog = WritesecondWeatherCustomDialog(requireContext(), this)
        customDialog.show()
    }

    override fun getFixedData(): ArrayList<Int> {
        var fixedWeather = arrayListOf<Int>()
        fixedWeather = weatherRVAdapter.getRVAFixedData()
        return fixedWeather
    }

    override fun getAddedData(): ArrayList<String> {
        var addedWeather = arrayListOf<String>()
        addedWeather = weatherRVAdapter.getRVAAddedData()
        return addedWeather
    }

    private fun getAddedBlock(){
        GetAddedBlockService.getAddedBlock(this)
    }

    override fun onGetAddedBlockLoading() {
    }

    override fun onGetAddedBlockSuccess(getaddedblockresult: GetAddedBlockResult) {
        if(getaddedblockresult.aweather != null) {
            for (i in getaddedblockresult.aweather) {
                weatherDatas.apply {
                    add(WritesecondWeather(i, idcount))
                    idcount += 1
                }
            }
            weatherRVAdapter.notifyDataSetChanged()
        }
    }

    override fun onGetAddedBlockFailure(code: Int, message: String) {
    }

    private fun modi(){
        date = (activity as WritesecondActivity).modidate.toString()
        ModiService.modi(this, date!!)
    }

    override fun onModiLoading() {
    }

    override fun onModiSuccess(modiresult: ModiResult) {
        if(modiresult.selected?.weather.isNullOrEmpty() == false){
            for(i in weatherDatas){
                for(j in modiresult.selected?.weather!!){
                    if(i.name == j){
                        i.apply{
                            i.focus = true
                        }
                    }
                }
            }
        }
    }

    override fun onModiFailure(code: Int, message: String) {
    }

    override fun refreshData() {
        for(i in weatherDatas){
            i.apply{
                i.focus = false
            }
        }
        weatherRVAdapter.notifyDataSetChanged()
    }

}