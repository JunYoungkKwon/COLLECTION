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
    var date : String = "2021-01-01"
    var looknameEt : EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWritefirstEtcBinding.inflate(inflater,container,false)

        // 데이터 리스트 생성
        etcList.apply {
            add(WritefirstEtc("+", 0,0))
            add(WritefirstEtc("머플러/스카프", 1,37))
            add(WritefirstEtc("모자", 2,38))
            add(WritefirstEtc("목도리", 3,39))
            add(WritefirstEtc("메신저백", 4,40))
            add(WritefirstEtc("백팩", 5,41))
            add(WritefirstEtc("벨트", 6,42))
            add(WritefirstEtc("시계", 7,43))
            add(WritefirstEtc("아이웨어", 8,44))
            add(WritefirstEtc("에코백", 9,45))
            add(WritefirstEtc("장갑", 10,46))
            add(WritefirstEtc("주얼리", 11,47))
            add(WritefirstEtc("크로스백", 12,48))
        }

        getAddedBlock()

        modi()

        Handler(Looper.getMainLooper()).postDelayed({
            etcRVAdapter = WritefirstEtcRVAdapter(etcList)
            etcRVAdapter.setEtcClickListener(this)

            val flexboxLayoutManager = FlexboxLayoutManager(activity)
            binding.writefirstEtcRecyclerview.adapter = etcRVAdapter
            binding.writefirstEtcRecyclerview.layoutManager = flexboxLayoutManager
        }, 100)

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

    // RVAdapter에서 plus 버튼 클릭시 이벤트 생성
    override fun plusButtonClick() {
        customDialog = WritefirstEtcCustomDialog(requireContext(), this)
        customDialog.show()
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