package com.eight.collection.ui.writing.first.etc

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
import com.eight.collection.data.remote.getaddedblock.GetAddedBlockResult
import com.eight.collection.data.remote.getaddedblock.GetAddedBlockService
import com.eight.collection.data.remote.modi.ModiResult
import com.eight.collection.data.remote.modi.ModiService
import com.eight.collection.databinding.FragmentWritefirstBottomBinding
import com.eight.collection.databinding.FragmentWritefirstEtcBinding
import com.eight.collection.ui.writing.CustomDialogInterface
import com.eight.collection.ui.writing.GetAddedBlockView
import com.eight.collection.ui.writing.ModiView
import com.eight.collection.ui.writing.first.AddedClothes
import com.eight.collection.ui.writing.first.FixedClothes
import com.eight.collection.ui.writing.first.WritefirstActivity
import com.eight.collection.ui.writing.first.bottom.WritefirstBottom
import com.eight.collection.ui.writing.first.bottom.WritefirstBottomCustomDialog
import com.eight.collection.ui.writing.first.bottom.WritefirstBottomRVAdapter
import com.eight.collection.ui.writing.first.shoes.WritefirstShoesRVAdapter
import com.google.android.flexbox.FlexboxLayoutManager

class WritefirstEtcFragment :  Fragment(), CustomDialogInterface,
    WritefirstEtcRVAdapter.EtcClickListener, WritefirstActivity.EtcColorClickListener, WritefirstActivity.GetEtcDataListener,
    GetAddedBlockView, ModiView,WritefirstActivity.RefreshEtcDataListener,  WritefirstActivity.EtcSelectIsListener {
    lateinit var binding : FragmentWritefirstEtcBinding
    var etcList = ArrayList<WritefirstEtc>()
    lateinit var customDialog: WritefirstEtcCustomDialog
    private var addItemId : Int = 13
    var etcRVAdapter : WritefirstEtcRVAdapter = WritefirstEtcRVAdapter(etcList)
    var date : String = "2019-01-01"
    var looknameEt : EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWritefirstEtcBinding.inflate(inflater,container,false)

        // ????????? ????????? ??????
        etcList.apply {
            add(WritefirstEtc("+", 0,0))
            add(WritefirstEtc("?????????/?????????", 1,37))
            add(WritefirstEtc("??????", 2,38))
            add(WritefirstEtc("?????????", 3,39))
            add(WritefirstEtc("????????????", 4,40))
            add(WritefirstEtc("??????", 5,41))
            add(WritefirstEtc("??????", 6,42))
            add(WritefirstEtc("??????", 7,43))
            add(WritefirstEtc("????????????", 8,44))
            add(WritefirstEtc("?????????", 9,45))
            add(WritefirstEtc("??????", 10,46))
            add(WritefirstEtc("?????????", 11,47))
            add(WritefirstEtc("????????????", 12,48))
        }

        getAddedBlock()

        Handler(Looper.getMainLooper()).postDelayed({
            modi()
        }, 100)

        Handler(Looper.getMainLooper()).postDelayed({
            etcRVAdapter = WritefirstEtcRVAdapter(etcList)
            etcRVAdapter.setEtcClickListener(this)

            val flexboxLayoutManager = FlexboxLayoutManager(activity)
            binding.writefirstEtcRecyclerview.adapter = etcRVAdapter
            binding.writefirstEtcRecyclerview.layoutManager = flexboxLayoutManager
        }, 200)

        return binding.root
    }

    override fun onAddButtonClicked(addText: String) {
        etcList.apply {
            add(WritefirstEtc(addText,addItemId))
            addItemId += 1
        }

        etcRVAdapter.notifyDataSetChanged()
        looknameEt = (activity as WritefirstActivity).binding.writefirstLookstyleTv
        looknameEt!!.clearFocus()
    }

    override fun onCancelButtonClicked() {
    }

    // RVAdapter?????? plus ?????? ????????? ????????? ??????
    override fun plusButtonClick() {
        customDialog = WritefirstEtcCustomDialog(requireContext(), this)
        customDialog.show()
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

    override fun getFixedData(): ArrayList<FixedClothes> {
        var etcfixedClothes = arrayListOf<FixedClothes>()
        etcfixedClothes = etcRVAdapter.getRVAFixedData()
        return etcfixedClothes
    }

    override fun getAddedData(): ArrayList<AddedClothes> {
        var etcaddedClothes = arrayListOf<AddedClothes>()
        etcaddedClothes = etcRVAdapter.getRVAAddedData()
        return etcaddedClothes
    }

    override fun getIs() : Int {
        var selectIs : Int = 0
        selectIs = etcRVAdapter.getSelectIs()
        return selectIs
    }


    private fun getAddedBlock(){
        GetAddedBlockService.getAddedBlock(this)
    }

    override fun onGetAddedBlockLoading() {
    }

    override fun onGetAddedBlockSuccess(getaddedblockresult: GetAddedBlockResult) {
        if(getaddedblockresult.aetc != null) {
            for(i in getaddedblockresult.aetc){
                etcList.apply {
                    add(WritefirstEtc(i,addItemId))
                    addItemId += 1
                }
            }
            etcRVAdapter.notifyDataSetChanged()
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
        if(modiresult.selected?.etc.isNullOrEmpty() == false){
            for(i in etcList){
                for(j in modiresult.selected?.etc!!){
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
        etcRVAdapter = WritefirstEtcRVAdapter(etcList)
        etcRVAdapter.setEtcClickListener(this)

        val flexboxLayoutManager = FlexboxLayoutManager(activity)
        binding.writefirstEtcRecyclerview.adapter = etcRVAdapter
        binding.writefirstEtcRecyclerview.layoutManager = flexboxLayoutManager
    }

    override fun onModiFailure(code: Int, message: String) {
    }

    override fun refreshData() {
        for(i in etcList){
            i.apply{
                i.color = "#00ff0000"
                i.textcolor = "#c3b5ac"
            }
        }
        Log.d("Etc success","Etc success")
        etcRVAdapter.notifyDataSetChanged()
    }

}