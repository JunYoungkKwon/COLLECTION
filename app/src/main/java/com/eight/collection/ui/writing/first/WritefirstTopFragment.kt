package com.eight.collection.ui.writing.first

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.eight.collection.R
import com.eight.collection.databinding.FragmentWritefirstTopBinding
import com.eight.collection.ui.writing.CustomDialogInterface
import com.google.android.flexbox.FlexboxLayoutManager

class WritefirstTopFragment : Fragment(), CustomDialogInterface,
    WritefirstTopRVAdapter.TopClickListener {
    lateinit var binding : FragmentWritefirstTopBinding
    private var topDatas = ArrayList<WritefirstTop>()
    lateinit var customDialog: WritefirstTopCustomDialog
    private var idcount : Int = 13
    private var addtext : String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWritefirstTopBinding.inflate(inflater,container,false)

        // 데이터 리스트 생성
        topDatas.apply {
            add(WritefirstTop("+", 0))
            add(WritefirstTop("맨투맨", 1))
            add(WritefirstTop("티셔츠", 2))
            add(WritefirstTop("블라우스", 3))
            add(WritefirstTop("목폴라", 4))
            add(WritefirstTop("후드티", 5))
            add(WritefirstTop("니트", 6))
            add(WritefirstTop("와이셔츠", 7))
            add(WritefirstTop("나시", 8))
            add(WritefirstTop("패딩", 9))
            add(WritefirstTop("무스탕", 10))
            add(WritefirstTop("후드집업", 11))
            add(WritefirstTop("코트", 12))
        }

        // Top RVA
        val topRVAdapter = WritefirstTopRVAdapter(topDatas)
        topRVAdapter.setTopClickListener(this)

        val flexboxLayoutManager = FlexboxLayoutManager(activity)
        binding.writefirstTopRecyclerview.adapter = topRVAdapter
        binding.writefirstTopRecyclerview.layoutManager = flexboxLayoutManager

        return binding.root
    }

    override fun onAddButtonClicked(addText: String) {
        topDatas.apply {
            add(WritefirstTop(addText,idcount))
            idcount += 1
        }

        val topRVAdapter = WritefirstTopRVAdapter(topDatas)
        topRVAdapter.setTopClickListener(this)

        val flexboxLayoutManager = FlexboxLayoutManager(activity)
        binding.writefirstTopRecyclerview.adapter = topRVAdapter
        binding.writefirstTopRecyclerview.layoutManager = flexboxLayoutManager
    }

    override fun onCancelButtonClicked() {
        Toast.makeText(requireContext(), "취소", Toast.LENGTH_SHORT).show()
    }

    // RVAdapter에서 plus 버튼 클릭시 이벤트 생성
    override fun plusButtonClick() {
        customDialog = WritefirstTopCustomDialog(requireContext(), this)
        customDialog.show()
    }
}