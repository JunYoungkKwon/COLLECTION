package com.eight.collection.ui.writing.first.top

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.eight.collection.databinding.FragmentWritefirstTopBinding
import com.eight.collection.ui.writing.CustomDialogInterface
import com.eight.collection.utils.getColor
import com.eight.collection.utils.getSelectedId
import com.eight.collection.utils.removeColor
import com.eight.collection.utils.removeSelectedId
import com.google.android.flexbox.FlexboxLayoutManager

class WritefirstTopFragment : Fragment(), CustomDialogInterface,
    WritefirstTopRVAdapter.TopClickListener {
    lateinit var binding : FragmentWritefirstTopBinding
    private var topList = ArrayList<TopFixedItem>()
    lateinit var customDialog: WritefirstTopCustomDialog
    private var addItemId : Int = 13
    var selectedId : Int = getSelectedId()
    var colorText : String? = getColor()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWritefirstTopBinding.inflate(inflater,container,false)

        //고정 Top 리스트 생성
        topList.apply {
            add(TopFixedItem("+", 0))
            add(TopFixedItem("맨투맨", 1))
            add(TopFixedItem("티셔츠", 2))
            add(TopFixedItem("블라우스", 3))
            add(TopFixedItem("목폴라", 4))
            add(TopFixedItem("후드티", 5))
            add(TopFixedItem("니트", 6))
            add(TopFixedItem("와이셔츠", 7))
            add(TopFixedItem("나시", 8))
            add(TopFixedItem("패딩", 9))
            add(TopFixedItem("무스탕", 10))
            add(TopFixedItem("후드집업", 11))
            add(TopFixedItem("코트", 12))
        }

        // Top RVA
        val topRVAdapter = WritefirstTopRVAdapter(topList)
        topRVAdapter.setTopClickListener(this)

        val flexboxLayoutManager = FlexboxLayoutManager(activity)
        binding.writefirstTopRecyclerview.adapter = topRVAdapter
        binding.writefirstTopRecyclerview.layoutManager = flexboxLayoutManager

        //컬러데이터 삽입
        topList.apply {
            topList[selectedId].color = colorText
            removeColor()
        }

        return binding.root
    }



    override fun onAddButtonClicked(addText: String) {
        topList.apply {
            add(TopFixedItem(addText,addItemId))
            addItemId += 1
        }

        val topRVAdapter = WritefirstTopRVAdapter(topList)
        topRVAdapter.setTopClickListener(this)

        binding.writefirstTopRecyclerview.adapter = topRVAdapter
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