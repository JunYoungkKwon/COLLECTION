package com.eight.collection.ui.main.match.color.top

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
import com.eight.collection.databinding.FragmentWritefirstTopBinding
import com.eight.collection.ui.main.match.color.ColorSearchActivity
import com.eight.collection.ui.main.match.color.MatchClothes
import com.eight.collection.ui.writing.GetAddedBlockView
import com.google.android.flexbox.FlexboxLayoutManager

class ColorSearchTopFragment : Fragment(),
    ColorSearchTopRVAdapter.TopClickListener, ColorSearchActivity.TopColorClickListener, GetAddedBlockView, ColorSearchActivity.RefreshTopDataListener,
    ColorSearchActivity.GetTopDataListener {
    lateinit var binding : FragmentWritefirstTopBinding
    var topList = ArrayList<ColorSearchTop>()
    var topRVAdapter : ColorSearchTopRVAdapter = ColorSearchTopRVAdapter(topList)
    private var addItemId : Int = 12

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWritefirstTopBinding.inflate(inflater,container,false)

        //고정 Top 리스트 생성
        topList.apply {
            add(ColorSearchTop("가디건",0,0))
            add(ColorSearchTop("나시/민소매",1,1))
            add(ColorSearchTop("니트/스웨터",2,2))
            add(ColorSearchTop("무스탕",3,3))
            add(ColorSearchTop("맨투맨",4,4))
            add(ColorSearchTop("베스트",5,5))
            add(ColorSearchTop("셔츠",6,6))
            add(ColorSearchTop("자켓",7,7))
            add(ColorSearchTop("티셔츠",8,8))
            add(ColorSearchTop("코트",9,9))
            add(ColorSearchTop("패딩", 10,10))
            add(ColorSearchTop("후드",11,11))
        }

        //추가 Top 리스트 생성
        getAddedBlock()

        // Top RVA
        Handler(Looper.getMainLooper()).postDelayed({
                topRVAdapter = ColorSearchTopRVAdapter(topList)
                topRVAdapter.setTopClickListener(this)

                val flexboxLayoutManager = FlexboxLayoutManager(activity)
                binding.writefirstTopRecyclerview.adapter = topRVAdapter
                binding.writefirstTopRecyclerview.layoutManager = flexboxLayoutManager
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
        binding.writefirstTopRecyclerview.layoutManager?.startSmoothScroll(smoothScroller)
    }

    override fun scrollButtonClickSecond() {
        val smoothScroller: RecyclerView.SmoothScroller by lazy {
            object : LinearSmoothScroller(context) {
                override fun getVerticalSnapPreference() = SNAP_TO_START
            }
        }
        smoothScroller.targetPosition = 20
        binding.writefirstTopRecyclerview.layoutManager?.startSmoothScroll(smoothScroller)
    }

    override fun scrollButtonClickThird() {
        val smoothScroller: RecyclerView.SmoothScroller by lazy {
            object : LinearSmoothScroller(context) {
                override fun getVerticalSnapPreference() = SNAP_TO_START
            }
        }
        smoothScroller.targetPosition = 27
        binding.writefirstTopRecyclerview.layoutManager?.startSmoothScroll(smoothScroller)
    }

    override fun scrollButtonClickFourth() {
        val smoothScroller: RecyclerView.SmoothScroller by lazy {
            object : LinearSmoothScroller(context) {
                override fun getVerticalSnapPreference() = SNAP_TO_START
            }
        }
        smoothScroller.targetPosition = 37
        binding.writefirstTopRecyclerview.layoutManager?.startSmoothScroll(smoothScroller)
    }


    //Color Change 함수
    override fun topChangeButtonColor(color: String) {
        if(topRVAdapter.getSelectId() == -1){
            return
        }
        topList[topRVAdapter.getSelectId()].color = color
        if(topList[topRVAdapter.getSelectId()].focus == true) {
            when (color) {
                //red
                "#d60f0f" -> {
                    topList[topRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //pink
                "#f59a9a" -> {
                    topList[topRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //yellow
                "#ffb203" -> {
                    topList[topRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //lightyellow
                "#fde6b1" -> {
                    topList[topRVAdapter.getSelectId()].textcolor = "#191919"
                }
                //green
                "#71a238" -> {
                    topList[topRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //lightgreen
                "#b7de89" -> {
                    topList[topRVAdapter.getSelectId()].textcolor = "#191919"
                }
                //orange
                "#ea7831" -> {
                    topList[topRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //navy
                "#273e88" -> {
                    topList[topRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //blue
                "#4168e8" -> {
                    topList[topRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //lightblue
                "#a5b9fa" -> {
                    topList[topRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //purple
                "#894ac7" -> {
                    topList[topRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //lightpurple
                "#dcacff" -> {
                    topList[topRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //white
                "#ffffff" -> {
                    topList[topRVAdapter.getSelectId()].textcolor = "#191919"
                }
                //grey
                "#888888" -> {
                    topList[topRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //black
                "#191919" -> {
                    topList[topRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //lightpeach
                "#e8dcd5" -> {
                    topList[topRVAdapter.getSelectId()].textcolor = "#191919"
                }
                //pinkishgrey
                "#c3b5ac" -> {
                    topList[topRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //brown
                "#74461f" -> {
                    topList[topRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
            }
            if (topList[topRVAdapter.getSelectId()].id > 10 && topList[topRVAdapter.getSelectId()].id < 17) {
                scrollButtonClickFirst()
            }

            else if(topList[topRVAdapter.getSelectId()].id > 16 && topList[topRVAdapter.getSelectId()].id < 24){
                scrollButtonClickSecond()
            }

            else if(topList[topRVAdapter.getSelectId()].id > 21 && topList[topRVAdapter.getSelectId()].id < 27){
                scrollButtonClickThird()
            }

            else if(topList[topRVAdapter.getSelectId()].id > 26 && topList[topRVAdapter.getSelectId()].id < 32){
                scrollButtonClickFourth()
            }

            topList[topRVAdapter.getSelectId()].focus = false
            topRVAdapter.setSelectId(-1)
        }
        topRVAdapter.notifyDataSetChanged()
    }

    override fun getData(): ArrayList<MatchClothes> {
        var topMatchClothes = arrayListOf<MatchClothes>()
        topMatchClothes = topRVAdapter.getRVAData()
        return topMatchClothes
    }

    private fun getAddedBlock(){
        GetAddedBlockService.getAddedBlock(this)
    }

    override fun onGetAddedBlockLoading() {

    }

    override fun onGetAddedBlockSuccess(getaddedblockresult: GetAddedBlockResult) {
        if(getaddedblockresult.atop != null) {
            for (i in getaddedblockresult.atop) {
                topList.apply {
                    add(ColorSearchTop(i, addItemId))
                    addItemId += 1
                }
            }
            topRVAdapter.notifyDataSetChanged()
        }
    }

    override fun onGetAddedBlockFailure(code: Int, message: String) {}



    override fun refreshData() {
        for(i in topList){
            i.apply{
                i.color = "#00ff0000"
                i.textcolor = "#c3b5ac"
            }
        }
        topRVAdapter.notifyDataSetChanged()
    }


}