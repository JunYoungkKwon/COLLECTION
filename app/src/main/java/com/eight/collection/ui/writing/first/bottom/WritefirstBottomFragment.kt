package com.eight.collection.ui.writing.first.bottom

import android.graphics.Color
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
import com.eight.collection.data.remote.getaddedblock.GetAddedBlockResult
import com.eight.collection.data.remote.getaddedblock.GetAddedBlockService
import com.eight.collection.data.remote.modi.ModiResult
import com.eight.collection.data.remote.modi.ModiService
import com.eight.collection.databinding.FragmentWritefirstBottomBinding
import com.eight.collection.ui.writing.CustomDialogInterface
import com.eight.collection.ui.writing.GetAddedBlockView
import com.eight.collection.ui.writing.ModiView
import com.eight.collection.ui.writing.first.AddedClothes
import com.eight.collection.ui.writing.first.FixedClothes
import com.eight.collection.ui.writing.first.WritefirstActivity
import com.eight.collection.ui.writing.first.top.WritefirstTop
import com.eight.collection.ui.writing.first.top.WritefirstTopRVAdapter
import com.google.android.flexbox.FlexboxLayoutManager

class WritefirstBottomFragment : Fragment(), CustomDialogInterface,
    WritefirstBottomRVAdapter.BottomClickListener, WritefirstActivity.BottomColorClickListener,
    WritefirstActivity.GetBottomDataListener, GetAddedBlockView, ModiView,WritefirstActivity.RefreshBottomDataListener {
    lateinit var binding : FragmentWritefirstBottomBinding
    var bottomList = ArrayList<WritefirstBottom>()
    lateinit var customDialog: WritefirstBottomCustomDialog
    private var addItemId : Int = 13
    var bottomRVAdapter : WritefirstBottomRVAdapter = WritefirstBottomRVAdapter(bottomList)
    var date : String = "2021-01-01"
    var looknameEt : EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWritefirstBottomBinding.inflate(inflater,container,false)

        // 데이터 리스트 생성
        bottomList.apply {
            add(WritefirstBottom("+", 0,0))
            add(WritefirstBottom("데님팬츠", 1,13))
            add(WritefirstBottom("롱스커트", 2,14))
            add(WritefirstBottom("레깅스", 3,15))
            add(WritefirstBottom("미니스커트", 4,16))
            add(WritefirstBottom("미디스커트", 5,17))
            add(WritefirstBottom("반바지", 6,18))
            add(WritefirstBottom("부츠컷팬츠", 7,19))
            add(WritefirstBottom("스키니팬츠", 8,20))
            add(WritefirstBottom("슬랙스", 9,21))
            add(WritefirstBottom("일자팬츠", 10,22))
            add(WritefirstBottom("와이드팬츠", 11,23))
            add(WritefirstBottom("조거팬츠", 12,24))
        }

        getAddedBlock()

        modi()

        Handler(Looper.getMainLooper()).postDelayed({
            bottomRVAdapter = WritefirstBottomRVAdapter(bottomList)
            bottomRVAdapter.setBottomClickListener(this)

            val flexboxLayoutManager = FlexboxLayoutManager(activity)
            binding.writefirstBottomRecyclerview.adapter = bottomRVAdapter
            binding.writefirstBottomRecyclerview.layoutManager = flexboxLayoutManager
        }, 300)


        return binding.root
    }

    override fun onAddButtonClicked(addText: String) {
        bottomList.apply {
            add(WritefirstBottom(addText,addItemId))
            addItemId += 1
        }
        bottomRVAdapter.notifyDataSetChanged()
        looknameEt = (activity as WritefirstActivity).binding.writefirstLookstyleTv
        looknameEt!!.clearFocus()
    }

    override fun onCancelButtonClicked() {
    }

    // RVAdapter에서 plus 버튼 클릭시 이벤트 생성
    override fun plusButtonClick() {
        customDialog = WritefirstBottomCustomDialog(requireContext(), this)
        customDialog.show()
    }

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
            bottomList[bottomRVAdapter.getSelectId()].focus = false
                    bottomRVAdapter.setSelectId(-1)
        }
        bottomRVAdapter.notifyDataSetChanged()
    }

    override fun getFixedData(): ArrayList<FixedClothes> {
        var bottomfixedClothes = arrayListOf<FixedClothes>()
        bottomfixedClothes = bottomRVAdapter.getRVAFixedData()
        return bottomfixedClothes
    }

    override fun getAddedData(): ArrayList<AddedClothes> {
        var bottomaddedClothes = arrayListOf<AddedClothes>()
        bottomaddedClothes = bottomRVAdapter.getRVAAddedData()
        return bottomaddedClothes
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
                    add(WritefirstBottom(i, addItemId))
                    addItemId += 1
                }
            }
            bottomRVAdapter.notifyDataSetChanged()
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
        if(modiresult.selected?.bottom.isNullOrEmpty() == false){
            for(i in bottomList){
                for(j in modiresult.selected?.bottom!!){
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
    }

    override fun onModiFailure(code: Int, message: String) {
    }

    override fun refreshData() {
        for(i in bottomList){
            i.apply{
                i.color = "#00ff0000"
                i.textcolor = "#c3b5ac"
            }
        }
        Log.d("Bottom success","Bottom success")
        bottomRVAdapter.notifyDataSetChanged()
    }

}