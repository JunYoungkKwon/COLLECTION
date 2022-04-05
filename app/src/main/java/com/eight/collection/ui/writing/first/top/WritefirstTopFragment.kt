package com.eight.collection.ui.writing.first.top

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.data.entities.Cloth
import com.eight.collection.data.remote.getaddedblock.GetAddedBlockResult
import com.eight.collection.data.remote.getaddedblock.GetAddedBlockService
import com.eight.collection.data.remote.modi.ModiResult
import com.eight.collection.data.remote.modi.ModiService
import com.eight.collection.data.remote.recieves3url.ReceiveS3UrlService
import com.eight.collection.databinding.FragmentWritefirstTopBinding
import com.eight.collection.ui.writing.CustomDialogInterface
import com.eight.collection.ui.writing.GetAddedBlockView
import com.eight.collection.ui.writing.ModiView
import com.eight.collection.ui.writing.first.AddedClothes
import com.eight.collection.ui.writing.first.FixedClothes
import com.eight.collection.ui.writing.first.WritefirstActivity
import com.google.android.flexbox.FlexboxLayoutManager

class WritefirstTopFragment : Fragment(), CustomDialogInterface,
    WritefirstTopRVAdapter.TopClickListener, WritefirstActivity.TopColorClickListener,
    WritefirstActivity.GetTopDataListener, GetAddedBlockView, ModiView,WritefirstActivity.RefreshTopDataListener, WritefirstActivity.TopSelectIsListener {
    lateinit var binding : FragmentWritefirstTopBinding
    var topList = ArrayList<WritefirstTop>()
    lateinit var customDialog: WritefirstTopCustomDialog
    private var addItemId : Int = 13
    var topRVAdapter : WritefirstTopRVAdapter = WritefirstTopRVAdapter(topList)
    var date : String = "2019-01-01"
    var looknameEt : EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWritefirstTopBinding.inflate(inflater,container,false)

        //고정 Top 리스트 생성
        topList.apply {
            add(WritefirstTop("+", 0, 0))
            add(WritefirstTop("가디건", 1, 1))
            add(WritefirstTop("나시/민소매", 2, 2))
            add(WritefirstTop("니트/스웨터", 3, 3))
            add(WritefirstTop("무스탕", 4, 4))
            add(WritefirstTop("맨투맨", 5,5))
            add(WritefirstTop("베스트", 6, 6))
            add(WritefirstTop("셔츠", 7,7))
            add(WritefirstTop("자켓", 8,8))
            add(WritefirstTop("티셔츠", 9,9))
            add(WritefirstTop("코트", 10,10))
            add(WritefirstTop("패딩", 11,11))
            add(WritefirstTop("후드", 12,12))
        }

        //추가 Top 리스트 생성
        getAddedBlock()

        Handler(Looper.getMainLooper()).postDelayed({
            modi()
        }, 200)

        // Top RVA
        Handler(Looper.getMainLooper()).postDelayed({
                topRVAdapter = WritefirstTopRVAdapter(topList)
                topRVAdapter.setTopClickListener(this)

                val flexboxLayoutManager = FlexboxLayoutManager(activity)
                binding.writefirstTopRecyclerview.adapter = topRVAdapter
                binding.writefirstTopRecyclerview.layoutManager = flexboxLayoutManager
        }, 300)

        return binding.root
    }


    override fun onAddButtonClicked(addText: String) {
        topList.apply {
            add(WritefirstTop(addText,addItemId))
            addItemId += 1
        }
        topRVAdapter.notifyDataSetChanged()
        looknameEt = (activity as WritefirstActivity).binding.writefirstLookstyleTv
        looknameEt!!.clearFocus()
    }


    override fun onCancelButtonClicked() {

    }


    // RVAdapter에서 plus 버튼 클릭시 이벤트 생성
    override fun plusButtonClick() {
        customDialog = WritefirstTopCustomDialog(requireContext(), this)
        customDialog.show()
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
            if (topList[topRVAdapter.getSelectId()].id > 11 && topList[topRVAdapter.getSelectId()].id < 18) {
                scrollButtonClickFirst()
            }

            else if(topList[topRVAdapter.getSelectId()].id > 17 && topList[topRVAdapter.getSelectId()].id < 23){
                scrollButtonClickSecond()
            }

            else if(topList[topRVAdapter.getSelectId()].id > 22 && topList[topRVAdapter.getSelectId()].id < 28){
                scrollButtonClickThird()
            }

            else if(topList[topRVAdapter.getSelectId()].id > 27 && topList[topRVAdapter.getSelectId()].id < 33){
                scrollButtonClickFourth()
            }

            topList[topRVAdapter.getSelectId()].focus = false
            topRVAdapter.setSelectId(-1)
        }
        topRVAdapter.notifyDataSetChanged()
    }

    override fun getFixedData(): ArrayList<FixedClothes> {
        var topfixedClothes = arrayListOf<FixedClothes>()
        topfixedClothes = topRVAdapter.getRVAFixedData()
        return topfixedClothes
    }

    override fun getAddedData(): ArrayList<AddedClothes> {
        var topaddedClothes = arrayListOf<AddedClothes>()
        topaddedClothes = topRVAdapter.getRVAAddedData()
        return topaddedClothes
    }


    override fun getIs() : Int {
        var selectIs : Int = 0
        selectIs = topRVAdapter.getSelectIs()
        return selectIs
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
                    add(WritefirstTop(i, addItemId))
                    addItemId += 1
                }
            }
            topRVAdapter.notifyDataSetChanged()
        }
    }

    override fun onGetAddedBlockFailure(code: Int, message: String) {

    }

    private fun modi(){
        date = (activity as WritefirstActivity).modidate.toString()
        ModiService.modi(this, date!!)
    }

    override fun onModiLoading() {

    }

    override fun onModiSuccess(modiresult: ModiResult) {
        if(modiresult.selected?.top.isNullOrEmpty() == false){
            for(i in topList){
                for(j in modiresult.selected?.top!!){
                    if(i.name == j.cloth){
                        i.apply{
                            i.color = j.color.toString()
                            when (i.color) {
                                "#d60f0f" -> {
                                    i.textcolor = "#ffffff"
                                }
                                //pink
                                "#f59a9a" -> {
                                    i.textcolor = "#ffffff"
                                }
                                //yellow
                                "#ffb203" -> {
                                    i.textcolor = "#ffffff"
                                }

                                //lightyellow
                                "#fde6b1" -> {
                                    i.textcolor = "#191919"
                                }
                                //green
                                "#71a238" -> {
                                    i.textcolor = "#ffffff"
                                }
                                //lightgreen
                                "#b7de89" -> {
                                    i.textcolor = "#191919"
                                }
                                //orange
                                "#ea7831" -> {
                                    i.textcolor = "#ffffff"
                                }
                                //navy
                                "#273e88" -> {
                                    i.textcolor = "#ffffff"
                                }
                                //blue
                                "#4168e8" -> {
                                    i.textcolor = "#ffffff"
                                }
                                //lightblue
                                "#a5b9fa" -> {
                                    i.textcolor = "#ffffff"
                                }
                                //purple
                                "#894ac7" -> {
                                    i.textcolor = "#ffffff"
                                }
                                //lightpurple
                                "#dcacff" -> {
                                    i.textcolor = "#ffffff"
                                }
                                //white
                                "#ffffff" -> {
                                    i.textcolor = "#191919"
                                }
                                //grey
                                "#888888" -> {
                                    i.textcolor = "#ffffff"
                                }
                                //black
                                "#191919" -> {
                                    i.textcolor = "#ffffff"
                                }
                                //lightpeach
                                "#e8dcd5" -> {
                                    i.textcolor = "#191919"
                                }
                                //pinkishgrey
                                "#c3b5ac" -> {
                                    i.textcolor = "#ffffff"
                                }
                                //brown
                                "#74461f" -> {
                                    i.textcolor = "#ffffff"
                                }
                            }
                        }
                    }
                }
            }
        }
        topRVAdapter = WritefirstTopRVAdapter(topList)
        topRVAdapter.setTopClickListener(this)

        val flexboxLayoutManager = FlexboxLayoutManager(activity)
        binding.writefirstTopRecyclerview.adapter = topRVAdapter
        binding.writefirstTopRecyclerview.layoutManager = flexboxLayoutManager

    }

    override fun onModiFailure(code: Int, message: String) {
    }

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