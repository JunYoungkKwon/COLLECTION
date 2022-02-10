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
import com.eight.collection.ui.writing.first.bottom.WritefirstBottom
import com.eight.collection.ui.writing.first.bottom.WritefirstBottomCustomDialog
import com.eight.collection.ui.writing.first.bottom.WritefirstBottomRVAdapter
import com.google.android.flexbox.FlexboxLayoutManager

class WritefirstEtcFragment :  Fragment(), CustomDialogInterface,
    WritefirstEtcRVAdapter.EtcClickListener {
    lateinit var binding : FragmentWritefirstEtcBinding
    private var etcDatas = ArrayList<WritefirstEtc>()
    lateinit var customDialog: WritefirstEtcCustomDialog
    private var idcount : Int = 13
    private var addtext : String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWritefirstEtcBinding.inflate(inflater,container,false)

        // 데이터 리스트 생성
        etcDatas.apply {
            add(WritefirstEtc("+", 0))
            add(WritefirstEtc("모자", 1))
            add(WritefirstEtc("스카프/머플러", 2))
            add(WritefirstEtc("장갑", 3))
            add(WritefirstEtc("목도리", 4))
            add(WritefirstEtc("아이웨어", 5))
            add(WritefirstEtc("시계", 6))
            add(WritefirstEtc("벨트", 7))
            add(WritefirstEtc("에코백", 8))
            add(WritefirstEtc("크로스백", 9))
            add(WritefirstEtc("메신저백", 10))
            add(WritefirstEtc("백팩", 11))
            add(WritefirstEtc("주얼리", 12))
        }

        val etcRVAdapter = WritefirstEtcRVAdapter(etcDatas)
        etcRVAdapter.setEtcClickListener(this)

        val flexboxLayoutManager = FlexboxLayoutManager(activity)
        binding.writefirstEtcRecyclerview.adapter = etcRVAdapter
        binding.writefirstEtcRecyclerview.layoutManager = flexboxLayoutManager

        return binding.root
    }

    override fun onAddButtonClicked(addText: String) {
        etcDatas.apply {
            add(WritefirstEtc(addText,idcount))
            idcount += 1
        }

        val etcRVAdapter = WritefirstEtcRVAdapter(etcDatas)
        etcRVAdapter.setEtcClickListener(this)

        val flexboxLayoutManager = FlexboxLayoutManager(activity)
        binding.writefirstEtcRecyclerview.adapter = etcRVAdapter
        binding.writefirstEtcRecyclerview.layoutManager = flexboxLayoutManager
    }

    override fun onCancelButtonClicked() {
        Toast.makeText(requireContext(), "취소", Toast.LENGTH_SHORT).show()
    }

    // RVAdapter에서 plus 버튼 클릭시 이벤트 생성
    override fun plusButtonClick() {
        customDialog = WritefirstEtcCustomDialog(requireContext(), this)
        customDialog.show()
    }
}