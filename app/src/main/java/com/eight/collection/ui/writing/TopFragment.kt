package com.eight.collection.ui.writing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.eight.collection.databinding.FragmentTopBinding

class TopFragment : Fragment(){
    lateinit var binding : FragmentTopBinding
    private var topDatas = ArrayList<Top>();

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopBinding.inflate(inflater,container,false)

        // 데이터 리스트 생성
        topDatas.apply {
            add(Top("+"))
            add(Top("맨투맨"))
            add(Top("티셔츠"))
            add(Top("블라우스"))
            add(Top("목폴라"))
            add(Top("후드티"))
            add(Top("니트"))
            add(Top("와이셔츠"))
            add(Top("나시"))
            add(Top("패딩"))
            add(Top("무스탕"))
            add(Top("후드집업"))
            add(Top("코트"))
        }

        val topRVAdapter = TopRVAdapter(topDatas)

        binding.writefirstTopRecyclerview.adapter = topRVAdapter

        binding.writefirstTopRecyclerview.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)

        return binding.root
    }



}