package com.eight.collection.ui.main.match.color.Etc

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.data.remote.getaddedblock.GetAddedBlockResult
import com.eight.collection.data.remote.getaddedblock.GetAddedBlockService
import com.eight.collection.databinding.FragmentWritefirstEtcBinding
import com.eight.collection.ui.main.match.color.ColorSearchActivity
import com.eight.collection.ui.main.match.color.MatchClothes
import com.eight.collection.ui.writing.GetAddedBlockView
import com.google.android.flexbox.FlexboxLayoutManager

class ColorSearchEtcFragment : Fragment(),
    ColorSearchEtcRVAdapter.EtcClickListener, ColorSearchActivity.EtcColorClickListener, GetAddedBlockView, ColorSearchActivity.RefreshEtcDataListener,
    ColorSearchActivity.GetEtcDataListener {
    lateinit var binding : FragmentWritefirstEtcBinding
    var etcList = ArrayList<ColorSearchEtc>()
    var etcRVAdapter : ColorSearchEtcRVAdapter = ColorSearchEtcRVAdapter(etcList)
    private var addItemId : Int = 12

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWritefirstEtcBinding.inflate(inflater,container,false)

        //고정 Etc 리스트 생성
        etcList.apply {
            add(ColorSearchEtc("머플러/스카프", 0,36))
            add(ColorSearchEtc("모자", 1,37))
            add(ColorSearchEtc("목도리", 2,38))
            add(ColorSearchEtc("메신저백", 3,39))
            add(ColorSearchEtc("백팩", 4,40))
            add(ColorSearchEtc("벨트", 5,41))
            add(ColorSearchEtc("시계", 6,42))
            add(ColorSearchEtc("아이웨어", 7,43))
            add(ColorSearchEtc("에코백", 8,44))
            add(ColorSearchEtc("장갑", 9,45))
            add(ColorSearchEtc("주얼리", 10,46))
            add(ColorSearchEtc("크로스백", 11,47))
        }

        //추가 Etc 리스트 생성
        getAddedBlock()

        // Etc RVA
        Handler(Looper.getMainLooper()).postDelayed({
                etcRVAdapter = ColorSearchEtcRVAdapter(etcList)
                etcRVAdapter.setEtcClickListener(this)

                val flexboxLayoutManager = FlexboxLayoutManager(activity)
                binding.writefirstEtcRecyclerview.adapter = etcRVAdapter
                binding.writefirstEtcRecyclerview.layoutManager = flexboxLayoutManager
        }, 300)

        return binding.root
    }

    override fun scrollButtonClickFirst() {
        val smoothScroller: RecyclerView.SmoothScroller by lazy {
            object : LinearSmoothScroller(context) {
                override fun getVerticalSnapPreference() = SNAP_TO_START
            }
        }
        smoothScroller.targetPosition = 13
        binding.writefirstEtcRecyclerview.layoutManager?.startSmoothScroll(smoothScroller)
    }

    override fun scrollButtonClickSecond() {
        val smoothScroller: RecyclerView.SmoothScroller by lazy {
            object : LinearSmoothScroller(context) {
                override fun getVerticalSnapPreference() = SNAP_TO_START
            }
        }
        smoothScroller.targetPosition = 20
        binding.writefirstEtcRecyclerview.layoutManager?.startSmoothScroll(smoothScroller)
    }

    override fun scrollButtonClickThird() {
        val smoothScroller: RecyclerView.SmoothScroller by lazy {
            object : LinearSmoothScroller(context) {
                override fun getVerticalSnapPreference() = SNAP_TO_START
            }
        }
        smoothScroller.targetPosition = 27
        binding.writefirstEtcRecyclerview.layoutManager?.startSmoothScroll(smoothScroller)
    }

    override fun scrollButtonClickFourth() {
        val smoothScroller: RecyclerView.SmoothScroller by lazy {
            object : LinearSmoothScroller(context) {
                override fun getVerticalSnapPreference() = SNAP_TO_START
            }
        }
        smoothScroller.targetPosition = 37
        binding.writefirstEtcRecyclerview.layoutManager?.startSmoothScroll(smoothScroller)
    }


    //Color Change 함수
    override fun etcChangeButtonColor(color: String) {
        if(etcRVAdapter.getSelectId() == -1){
            return
        }
        etcList[etcRVAdapter.getSelectId()].color = color
        if(etcList[etcRVAdapter.getSelectId()].focus == true) {
            when (color) {
                //red
                "#d60f0f" -> {
                    etcList[etcRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //pink
                "#f59a9a" -> {
                    etcList[etcRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //yellow
                "#ffb203" -> {
                    etcList[etcRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //lightyellow
                "#fde6b1" -> {
                    etcList[etcRVAdapter.getSelectId()].textcolor = "#191919"
                }
                //green
                "#71a238" -> {
                    etcList[etcRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //lightgreen
                "#b7de89" -> {
                    etcList[etcRVAdapter.getSelectId()].textcolor = "#191919"
                }
                //orange
                "#ea7831" -> {
                    etcList[etcRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //navy
                "#273e88" -> {
                    etcList[etcRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //blue
                "#4168e8" -> {
                    etcList[etcRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //lightblue
                "#a5b9fa" -> {
                    etcList[etcRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //purple
                "#894ac7" -> {
                    etcList[etcRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //lightpurple
                "#dcacff" -> {
                    etcList[etcRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //white
                "#ffffff" -> {
                    etcList[etcRVAdapter.getSelectId()].textcolor = "#191919"
                }
                //grey
                "#888888" -> {
                    etcList[etcRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //black
                "#191919" -> {
                    etcList[etcRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //lightpeach
                "#e8dcd5" -> {
                    etcList[etcRVAdapter.getSelectId()].textcolor = "#191919"
                }
                //pinkishgrey
                "#c3b5ac" -> {
                    etcList[etcRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //brown
                "#74461f" -> {
                    etcList[etcRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
            }
            if (etcList[etcRVAdapter.getSelectId()].id > 11 && etcList[etcRVAdapter.getSelectId()].id < 18) {
                scrollButtonClickFirst()
            }

            else if(etcList[etcRVAdapter.getSelectId()].id > 17 && etcList[etcRVAdapter.getSelectId()].id < 23){
                scrollButtonClickSecond()
            }

            else if(etcList[etcRVAdapter.getSelectId()].id > 22 && etcList[etcRVAdapter.getSelectId()].id < 28){
                scrollButtonClickThird()
            }

            else if(etcList[etcRVAdapter.getSelectId()].id > 27 && etcList[etcRVAdapter.getSelectId()].id < 33){
                scrollButtonClickFourth()
            }
            etcList[etcRVAdapter.getSelectId()].focus = false
            etcRVAdapter.setSelectId(-1)
        }
        etcRVAdapter.notifyDataSetChanged()
    }

    override fun getData(): ArrayList<MatchClothes> {
        var etcMatchClothes = arrayListOf<MatchClothes>()
        etcMatchClothes = etcRVAdapter.getRVAData()
        return etcMatchClothes
    }

    private fun getAddedBlock(){
        GetAddedBlockService.getAddedBlock(this)
    }

    override fun onGetAddedBlockLoading() {

    }

    override fun onGetAddedBlockSuccess(getaddedblockresult: GetAddedBlockResult) {
        for (i in getaddedblockresult.aetc) {
            etcList.apply {
                add(ColorSearchEtc(i, addItemId))
                addItemId += 1
            }
        }
        etcRVAdapter.notifyDataSetChanged()
    }

    override fun onGetAddedBlockFailure(code: Int, message: String) {}



    override fun refreshData() {
        for(i in etcList){
            i.apply{
                i.color = "#00ff0000"
                i.textcolor = "#c3b5ac"
            }
        }
        etcRVAdapter.notifyDataSetChanged()
    }


}