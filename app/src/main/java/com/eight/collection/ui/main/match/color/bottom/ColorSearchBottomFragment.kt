package com.eight.collection.ui.main.match.color.bottom

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
import com.eight.collection.databinding.FragmentWritefirstBottomBinding
import com.eight.collection.ui.main.match.color.ColorSearchActivity
import com.eight.collection.ui.main.match.color.MatchClothes
import com.eight.collection.ui.writing.GetAddedBlockView
import com.google.android.flexbox.FlexboxLayoutManager

class ColorSearchBottomFragment : Fragment(),
    ColorSearchBottomRVAdapter.BottomClickListener, ColorSearchActivity.BottomColorClickListener, GetAddedBlockView, ColorSearchActivity.RefreshBottomDataListener,
    ColorSearchActivity.GetBottomDataListener {
    lateinit var binding : FragmentWritefirstBottomBinding
    var bottomList = ArrayList<ColorSearchBottom>()
    var bottomRVAdapter : ColorSearchBottomRVAdapter = ColorSearchBottomRVAdapter(bottomList)
    private var addItemId : Int = 12

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWritefirstBottomBinding.inflate(inflater,container,false)

        //고정 Bottom 리스트 생성
        bottomList.apply {
            add(ColorSearchBottom("데님팬츠", 0,12))
            add(ColorSearchBottom("롱스커트", 1,13))
            add(ColorSearchBottom("레깅스", 2,14))
            add(ColorSearchBottom("미니스커트", 3,15))
            add(ColorSearchBottom("미디스커트", 4,16))
            add(ColorSearchBottom("반바지", 5,17))
            add(ColorSearchBottom("부츠컷팬츠", 6,18))
            add(ColorSearchBottom("스키니팬츠", 7,19))
            add(ColorSearchBottom("슬랙스", 8,20))
            add(ColorSearchBottom("일자팬츠", 9,21))
            add(ColorSearchBottom("와이드팬츠", 10,22))
            add(ColorSearchBottom("조거팬츠", 11,23))
        }

        //추가 Top 리스트 생성
        getAddedBlock()

        // Top RVA
        Handler(Looper.getMainLooper()).postDelayed({
                bottomRVAdapter = ColorSearchBottomRVAdapter(bottomList)
                bottomRVAdapter.setBottomClickListener(this)

                val flexboxLayoutManager = FlexboxLayoutManager(activity)
                binding.writefirstBottomRecyclerview.adapter = bottomRVAdapter
                binding.writefirstBottomRecyclerview.layoutManager = flexboxLayoutManager
        }, 300)

        return binding.root
    }

    override fun scrollButtonClickFirst() {
        val smoothScroller: RecyclerView.SmoothScroller by lazy {
            object : LinearSmoothScroller(context) {
                override fun getVerticalSnapPreference() = SNAP_TO_START
            }
        }
        smoothScroller.targetPosition = 12
        binding.writefirstBottomRecyclerview.layoutManager?.startSmoothScroll(smoothScroller)
    }

    override fun scrollButtonClickSecond() {
        val smoothScroller: RecyclerView.SmoothScroller by lazy {
            object : LinearSmoothScroller(context) {
                override fun getVerticalSnapPreference() = SNAP_TO_START
            }
        }
        smoothScroller.targetPosition = 19
        binding.writefirstBottomRecyclerview.layoutManager?.startSmoothScroll(smoothScroller)
    }

    override fun scrollButtonClickThird() {
        val smoothScroller: RecyclerView.SmoothScroller by lazy {
            object : LinearSmoothScroller(context) {
                override fun getVerticalSnapPreference() = SNAP_TO_START
            }
        }
        smoothScroller.targetPosition = 26
        binding.writefirstBottomRecyclerview.layoutManager?.startSmoothScroll(smoothScroller)
    }

    override fun scrollButtonClickFourth() {
        val smoothScroller: RecyclerView.SmoothScroller by lazy {
            object : LinearSmoothScroller(context) {
                override fun getVerticalSnapPreference() = SNAP_TO_START
            }
        }
        smoothScroller.targetPosition = 36
        binding.writefirstBottomRecyclerview.layoutManager?.startSmoothScroll(smoothScroller)
    }


    //Color Change 함수
    override fun bottomChangeButtonColor(color: String) {
        if(bottomRVAdapter.getSelectId() == -1){
            return
        }
        bottomList[bottomRVAdapter.getSelectId()].color = color
        if(bottomList[bottomRVAdapter.getSelectId()].focus == true) {
            when (color) {
                //red
                "#d60f0f" -> {
                    bottomList[bottomRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //pink
                "#f59a9a" -> {
                    bottomList[bottomRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //yellow
                "#ffb203" -> {
                    bottomList[bottomRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //lightyellow
                "#fde6b1" -> {
                    bottomList[bottomRVAdapter.getSelectId()].textcolor = "#191919"
                }
                //green
                "#71a238" -> {
                    bottomList[bottomRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //lightgreen
                "#b7de89" -> {
                    bottomList[bottomRVAdapter.getSelectId()].textcolor = "#191919"
                }
                //orange
                "#ea7831" -> {
                    bottomList[bottomRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //navy
                "#273e88" -> {
                    bottomList[bottomRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //blue
                "#4168e8" -> {
                    bottomList[bottomRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //lightblue
                "#a5b9fa" -> {
                    bottomList[bottomRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //purple
                "#894ac7" -> {
                    bottomList[bottomRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //lightpurple
                "#dcacff" -> {
                    bottomList[bottomRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //white
                "#ffffff" -> {
                    bottomList[bottomRVAdapter.getSelectId()].textcolor = "#191919"
                }
                //grey
                "#888888" -> {
                    bottomList[bottomRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //black
                "#191919" -> {
                    bottomList[bottomRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //lightpeach
                "#e8dcd5" -> {
                    bottomList[bottomRVAdapter.getSelectId()].textcolor = "#191919"
                }
                //pinkishgrey
                "#c3b5ac" -> {
                    bottomList[bottomRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //brown
                "#74461f" -> {
                    bottomList[bottomRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
            }
            if (bottomList[bottomRVAdapter.getSelectId()].id > 11 && bottomList[bottomRVAdapter.getSelectId()].id < 18) {
                scrollButtonClickFirst()
            }

            else if(bottomList[bottomRVAdapter.getSelectId()].id > 17 && bottomList[bottomRVAdapter.getSelectId()].id < 23){
                scrollButtonClickSecond()
            }

            else if(bottomList[bottomRVAdapter.getSelectId()].id > 22 && bottomList[bottomRVAdapter.getSelectId()].id < 28){
                scrollButtonClickThird()
            }

            else if(bottomList[bottomRVAdapter.getSelectId()].id > 27 && bottomList[bottomRVAdapter.getSelectId()].id < 33){
                scrollButtonClickFourth()
            }

            bottomList[bottomRVAdapter.getSelectId()].focus = false
            bottomRVAdapter.setSelectId(-1)
        }
        bottomRVAdapter.notifyDataSetChanged()
    }

    override fun getData(): ArrayList<MatchClothes> {
        var bottomMatchClothes = arrayListOf<MatchClothes>()
        bottomMatchClothes = bottomRVAdapter.getRVAData()
        return bottomMatchClothes
    }

    private fun getAddedBlock(){
        GetAddedBlockService.getAddedBlock(this)
    }

    override fun onGetAddedBlockLoading() {

    }

    override fun onGetAddedBlockSuccess(getaddedblockresult: GetAddedBlockResult) {
        if(getaddedblockresult.abottom != null) {
            for (i in getaddedblockresult.abottom) {
                bottomList.apply {
                    add(ColorSearchBottom(i, addItemId))
                    addItemId += 1
                }
            }
            bottomRVAdapter.notifyDataSetChanged()
        }
    }

    override fun onGetAddedBlockFailure(code: Int, message: String) {}



    override fun refreshData() {
        for(i in bottomList){
            i.apply{
                i.color = "#00ff0000"
                i.textcolor = "#c3b5ac"
            }
        }
        bottomRVAdapter.notifyDataSetChanged()
    }


}