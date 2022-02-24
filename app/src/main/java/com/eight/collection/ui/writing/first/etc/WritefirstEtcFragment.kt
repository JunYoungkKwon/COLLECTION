package com.eight.collection.ui.writing.first.etc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.eight.collection.databinding.FragmentWritefirstBottomBinding
import com.eight.collection.databinding.FragmentWritefirstEtcBinding
import com.eight.collection.ui.writing.CustomDialogInterface
import com.eight.collection.ui.writing.first.WritefirstActivity
import com.eight.collection.ui.writing.first.bottom.WritefirstBottom
import com.eight.collection.ui.writing.first.bottom.WritefirstBottomCustomDialog
import com.eight.collection.ui.writing.first.bottom.WritefirstBottomRVAdapter
import com.eight.collection.ui.writing.first.shoes.WritefirstShoesRVAdapter
import com.google.android.flexbox.FlexboxLayoutManager

class WritefirstEtcFragment :  Fragment(), CustomDialogInterface,
    WritefirstEtcRVAdapter.EtcClickListener, WritefirstActivity.EtcColorClickListner {
    lateinit var binding : FragmentWritefirstEtcBinding
    private var etcList = ArrayList<WritefirstEtc>()
    lateinit var customDialog: WritefirstEtcCustomDialog
    private var addItemId : Int = 13
    var etcRVAdapter : WritefirstEtcRVAdapter = WritefirstEtcRVAdapter(etcList)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWritefirstEtcBinding.inflate(inflater,container,false)

        // 데이터 리스트 생성
        etcList.apply {
            add(WritefirstEtc("+", 0))
            add(WritefirstEtc("머플러/스카프", 37))
            add(WritefirstEtc("모자", 38))
            add(WritefirstEtc("목도리", 39))
            add(WritefirstEtc("메신저백", 40))
            add(WritefirstEtc("백팩", 41))
            add(WritefirstEtc("벨트", 42))
            add(WritefirstEtc("시계", 43))
            add(WritefirstEtc("아이웨어", 44))
            add(WritefirstEtc("에코백", 45))
            add(WritefirstEtc("장갑", 46))
            add(WritefirstEtc("주얼리", 47))
            add(WritefirstEtc("크로스백", 48))
        }

        etcRVAdapter = WritefirstEtcRVAdapter(etcList)
        etcRVAdapter.setEtcClickListener(this)

        val flexboxLayoutManager = FlexboxLayoutManager(activity)
        binding.writefirstEtcRecyclerview.adapter = etcRVAdapter
        binding.writefirstEtcRecyclerview.layoutManager = flexboxLayoutManager

        return binding.root
    }

    override fun onAddButtonClicked(addText: String) {
        etcList.apply {
            add(WritefirstEtc(addText,addItemId))
            addItemId += 1
        }

        etcRVAdapter.notifyDataSetChanged()
    }

    override fun onCancelButtonClicked() {
        Toast.makeText(requireContext(), "취소", Toast.LENGTH_SHORT).show()
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
        etcRVAdapter.notifyDataSetChanged()
    }
}