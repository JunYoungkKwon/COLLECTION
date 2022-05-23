package com.eight.collection.ui.main.match.color.Shoes

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
import com.eight.collection.databinding.FragmentWritefirstShoesBinding
import com.eight.collection.ui.main.match.color.ColorSearchActivity
import com.eight.collection.ui.main.match.color.MatchClothes
import com.eight.collection.ui.writing.GetAddedBlockView
import com.google.android.flexbox.FlexboxLayoutManager

class ColorSearchShoesFragment : Fragment(),
    ColorSearchShoesRVAdapter.ShoesClickListener, ColorSearchActivity.ShoesColorClickListener, GetAddedBlockView, ColorSearchActivity.RefreshShoesDataListener,
    ColorSearchActivity.GetShoesDataListener {
    lateinit var binding : FragmentWritefirstShoesBinding
    var shoesList = ArrayList<ColorSearchShoes>()
    var shoesRVAdapter : ColorSearchShoesRVAdapter = ColorSearchShoesRVAdapter(shoesList)
    private var addItemId : Int = 12

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWritefirstShoesBinding.inflate(inflater,container,false)

        //고정 Shoes 리스트 생성
        shoesList.apply {
            add(ColorSearchShoes("단화", 0,24))
            add(ColorSearchShoes("더비슈즈", 1,25))
            add(ColorSearchShoes("로퍼/플랫", 2,26))
            add(ColorSearchShoes("뮬", 3,27))
            add(ColorSearchShoes("부츠", 4,28))
            add(ColorSearchShoes("스니커즈", 5,29))
            add(ColorSearchShoes("슬리퍼", 6,30))
            add(ColorSearchShoes("샌들", 7,31))
            add(ColorSearchShoes("아쿠아슈즈/트래킹슈즈", 8,32))
            add(ColorSearchShoes("워커", 9,33))
            add(ColorSearchShoes("펌프스", 10,34))
            add(ColorSearchShoes("힐", 11,35))
        }

        //추가 Shoes 리스트 생성
        getAddedBlock()

        // Shoes RVA
        Handler(Looper.getMainLooper()).postDelayed({
                shoesRVAdapter = ColorSearchShoesRVAdapter(shoesList)
                shoesRVAdapter.setShoesClickListener(this)

                val flexboxLayoutManager = FlexboxLayoutManager(activity)
                binding.writefirstShoesRecyclerview.adapter = shoesRVAdapter
                binding.writefirstShoesRecyclerview.layoutManager = flexboxLayoutManager
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
        binding.writefirstShoesRecyclerview.layoutManager?.startSmoothScroll(smoothScroller)
    }

    override fun scrollButtonClickSecond() {
        val smoothScroller: RecyclerView.SmoothScroller by lazy {
            object : LinearSmoothScroller(context) {
                override fun getVerticalSnapPreference() = SNAP_TO_START
            }
        }
        smoothScroller.targetPosition = 20
        binding.writefirstShoesRecyclerview.layoutManager?.startSmoothScroll(smoothScroller)
    }

    override fun scrollButtonClickThird() {
        val smoothScroller: RecyclerView.SmoothScroller by lazy {
            object : LinearSmoothScroller(context) {
                override fun getVerticalSnapPreference() = SNAP_TO_START
            }
        }
        smoothScroller.targetPosition = 27
        binding.writefirstShoesRecyclerview.layoutManager?.startSmoothScroll(smoothScroller)
    }

    override fun scrollButtonClickFourth() {
        val smoothScroller: RecyclerView.SmoothScroller by lazy {
            object : LinearSmoothScroller(context) {
                override fun getVerticalSnapPreference() = SNAP_TO_START
            }
        }
        smoothScroller.targetPosition = 37
        binding.writefirstShoesRecyclerview.layoutManager?.startSmoothScroll(smoothScroller)
    }


    //Color Change 함수
    override fun shoesChangeButtonColor(color: String) {
        if(shoesRVAdapter.getSelectId() == -1){
            return
        }
        shoesList[shoesRVAdapter.getSelectId()].color = color
        if(shoesList[shoesRVAdapter.getSelectId()].focus == true) {
            when (color) {
                //red
                "#d60f0f" -> {
                    shoesList[shoesRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //pink
                "#f59a9a" -> {
                    shoesList[shoesRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //yellow
                "#ffb203" -> {
                    shoesList[shoesRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //lightyellow
                "#fde6b1" -> {
                    shoesList[shoesRVAdapter.getSelectId()].textcolor = "#191919"
                }
                //green
                "#71a238" -> {
                    shoesList[shoesRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //lightgreen
                "#b7de89" -> {
                    shoesList[shoesRVAdapter.getSelectId()].textcolor = "#191919"
                }
                //orange
                "#ea7831" -> {
                    shoesList[shoesRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //navy
                "#273e88" -> {
                    shoesList[shoesRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //blue
                "#4168e8" -> {
                    shoesList[shoesRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //lightblue
                "#a5b9fa" -> {
                    shoesList[shoesRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //purple
                "#894ac7" -> {
                    shoesList[shoesRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //lightpurple
                "#dcacff" -> {
                    shoesList[shoesRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //white
                "#ffffff" -> {
                    shoesList[shoesRVAdapter.getSelectId()].textcolor = "#191919"
                }
                //grey
                "#888888" -> {
                    shoesList[shoesRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //black
                "#191919" -> {
                    shoesList[shoesRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //lightpeach
                "#e8dcd5" -> {
                    shoesList[shoesRVAdapter.getSelectId()].textcolor = "#191919"
                }
                //pinkishgrey
                "#c3b5ac" -> {
                    shoesList[shoesRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
                //brown
                "#74461f" -> {
                    shoesList[shoesRVAdapter.getSelectId()].textcolor = "#ffffff"
                }
            }
            if (shoesList[shoesRVAdapter.getSelectId()].id > 11 && shoesList[shoesRVAdapter.getSelectId()].id < 18) {
                scrollButtonClickFirst()
            }

            else if(shoesList[shoesRVAdapter.getSelectId()].id > 17 && shoesList[shoesRVAdapter.getSelectId()].id < 23){
                scrollButtonClickSecond()
            }

            else if(shoesList[shoesRVAdapter.getSelectId()].id > 22 && shoesList[shoesRVAdapter.getSelectId()].id < 28){
                scrollButtonClickThird()
            }

            else if(shoesList[shoesRVAdapter.getSelectId()].id > 27 && shoesList[shoesRVAdapter.getSelectId()].id < 33){
                scrollButtonClickFourth()
            }

            shoesList[shoesRVAdapter.getSelectId()].focus = false
            shoesRVAdapter.setSelectId(-1)
        }
        shoesRVAdapter.notifyDataSetChanged()
    }

    override fun getData(): ArrayList<MatchClothes> {
        var shoesMatchClothes = arrayListOf<MatchClothes>()
        shoesMatchClothes = shoesRVAdapter.getRVAData()
        return shoesMatchClothes
    }

    private fun getAddedBlock(){
        GetAddedBlockService.getAddedBlock(this)
    }

    override fun onGetAddedBlockLoading() {

    }

    override fun onGetAddedBlockSuccess(getaddedblockresult: GetAddedBlockResult) {
        if(getaddedblockresult.ashoes != null) {
            for (i in getaddedblockresult.ashoes) {
                shoesList.apply {
                    add(ColorSearchShoes(i, addItemId))
                    addItemId += 1
                }
            }
            shoesRVAdapter.notifyDataSetChanged()
        }
    }

    override fun onGetAddedBlockFailure(code: Int, message: String) {}



    override fun refreshData() {
        for(i in shoesList){
            i.apply{
                i.color = "#00ff0000"
                i.textcolor = "#c3b5ac"
            }
        }
        shoesRVAdapter.notifyDataSetChanged()
    }


}