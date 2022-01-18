package com.eight.collection.ui.writing

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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
            add(Top("+", 0))
            add(Top("맨투맨", 1))
            add(Top("티셔츠", 2))
            add(Top("블라우스", 3))
            add(Top("목폴라", 4))
            add(Top("후드티", 5))
            add(Top("니트", 6))
            add(Top("와이셔츠", 7))
            add(Top("나시", 8))
            add(Top("패딩", 9))
            add(Top("무스탕", 10))
            add(Top("후드집업", 11))
            add(Top("코트", 12))
        }



        /*binding.writefirstTopRecyclerview.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)*/

        val topRVAdapter = TopRVAdapter(topDatas)
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(3,LinearLayoutManager.VERTICAL)
        binding.writefirstTopRecyclerview.adapter = topRVAdapter
        binding.writefirstTopRecyclerview.layoutManager = staggeredGridLayoutManager

        return binding.root
    }



}