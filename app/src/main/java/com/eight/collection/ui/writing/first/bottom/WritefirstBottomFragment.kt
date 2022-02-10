package com.eight.collection.ui.writing.first.bottom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.eight.collection.databinding.FragmentWritefirstBottomBinding
import com.eight.collection.databinding.FragmentWritefirstTopBinding
import com.eight.collection.ui.writing.CustomDialogInterface
import com.eight.collection.ui.writing.first.top.WritefirstTop
import com.eight.collection.ui.writing.first.top.WritefirstTopCustomDialog
import com.eight.collection.ui.writing.first.top.WritefirstTopRVAdapter
import com.google.android.flexbox.FlexboxLayoutManager

class WritefirstBottomFragment : Fragment(), CustomDialogInterface,
    WritefirstBottomRVAdapter.BottomClickListener {
    lateinit var binding : FragmentWritefirstBottomBinding
    private var bottomDatas = ArrayList<WritefirstBottom>()
    lateinit var customDialog: WritefirstBottomCustomDialog
    private var idcount : Int = 13
    private var addtext : String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWritefirstBottomBinding.inflate(inflater,container,false)

        // 데이터 리스트 생성
        bottomDatas.apply {
            add(WritefirstBottom("+", 0))
            add(WritefirstBottom("슬랙스", 1))
            add(WritefirstBottom("와이드팬츠", 2))
            add(WritefirstBottom("스키니팬츠", 3))
            add(WritefirstBottom("조거팬츠", 4))
            add(WritefirstBottom("부츠컷팬츠", 5))
            add(WritefirstBottom("미니스커트", 6))
            add(WritefirstBottom("롱스커트", 7))
            add(WritefirstBottom("반바지", 8))
            add(WritefirstBottom("레깅스", 9))
            add(WritefirstBottom("데님팬츠", 10))
            add(WritefirstBottom("미디스커트", 11))
            add(WritefirstBottom("일자팬츠", 12))
        }

        val bottomRVAdapter = WritefirstBottomRVAdapter(bottomDatas)
        bottomRVAdapter.setBottomClickListener(this)

        val flexboxLayoutManager = FlexboxLayoutManager(activity)
        binding.writefirstBottomRecyclerview.adapter = bottomRVAdapter
        binding.writefirstBottomRecyclerview.layoutManager = flexboxLayoutManager

        return binding.root
    }

    override fun onAddButtonClicked(addText: String) {
        bottomDatas.apply {
            add(WritefirstBottom(addText,idcount))
            idcount += 1
        }

        val bottomRVAdapter = WritefirstBottomRVAdapter(bottomDatas)
        bottomRVAdapter.setBottomClickListener(this)

        val flexboxLayoutManager = FlexboxLayoutManager(activity)
        binding.writefirstBottomRecyclerview.adapter = bottomRVAdapter
        binding.writefirstBottomRecyclerview.layoutManager = flexboxLayoutManager
    }

    override fun onCancelButtonClicked() {
        Toast.makeText(requireContext(), "취소", Toast.LENGTH_SHORT).show()
    }

    // RVAdapter에서 plus 버튼 클릭시 이벤트 생성
    override fun plusButtonClick() {
        customDialog = WritefirstBottomCustomDialog(requireContext(), this)
        customDialog.show()
    }

}