package com.eight.collection.ui.writing.second

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.eight.collection.databinding.FragmentWritesecondPlaceBinding
import com.google.android.flexbox.FlexboxLayoutManager

class WritesecondPlaceFragment : Fragment(){
    lateinit var binding : FragmentWritesecondPlaceBinding
    private var placeDatas = ArrayList<WritesecondPlace>();

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWritesecondPlaceBinding.inflate(inflater,container,false)

        // 데이터 리스트 생성
        placeDatas.apply {
            add(WritesecondPlace("+", 0))
            add(WritesecondPlace("학교", 1))
            add(WritesecondPlace("회사", 2))
            add(WritesecondPlace("헬스장", 3))
            add(WritesecondPlace("집", 4))
            add(WritesecondPlace("카페", 5))
            add(WritesecondPlace("결혼식장", 6))
            add(WritesecondPlace("핫플레이스", 7))
            add(WritesecondPlace("놀이공원", 8))
        }


        /*binding.writefirstTopRecyclerview.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)*/

        val placeRVAdapter = WritesecondPlaceRVAdapter(placeDatas)
        val flexboxLayoutManager = FlexboxLayoutManager(activity)
        binding.writesecondPlaceRecyclerview.adapter = placeRVAdapter
        binding.writesecondPlaceRecyclerview.layoutManager = flexboxLayoutManager


        return binding.root
    }



}