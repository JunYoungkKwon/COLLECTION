package com.eight.collection.ui.writing

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eight.collection.databinding.ActivityAddToptagBinding
import com.eight.collection.databinding.FragmentWritefirstTopBinding
import com.google.android.flexbox.FlexboxLayoutManager

class WritefirstTopFragment : Fragment(){
    lateinit var binding : FragmentWritefirstTopBinding
    private var topDatas = ArrayList<WritefirstTop>();

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


        /*binding.writefirstTopRecyclerview.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)*/

        val topRVAdapter = WritefirstTopRVAdapter(topDatas)
        val flexboxLayoutManager = FlexboxLayoutManager(activity)
        binding.writefirstTopRecyclerview.adapter = topRVAdapter
        binding.writefirstTopRecyclerview.layoutManager = flexboxLayoutManager


        return binding.root
    }


}