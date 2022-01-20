package com.eight.collection.ui.main.lookpoint

import android.view.View
import com.eight.collection.R
import com.eight.collection.databinding.FragmentLookPointBinding
import com.eight.collection.databinding.FragmentMonthBinding
import com.eight.collection.databinding.FragmentMatchBinding
import com.eight.collection.ui.BaseFragment
import com.eight.collection.ui.main.week.Diary
import com.eight.collection.ui.main.week.DiaryRVAdapter
import com.eight.collection.ui.main.week.Etc
import java.util.ArrayList

class LookPointFragment(): BaseFragment<FragmentLookPointBinding>(FragmentLookPointBinding::inflate) {

     private  var myLookDatas = ArrayList<MyLook>()
    private  var photoDatas = ArrayList<Photo>()
    override fun initAfterBinding() {

        myLookDatas.apply {
            add(MyLook( "01", R.drawable.mylook_point_5))
            add(MyLook( "02", R.drawable.mylook_point_4))
            add(MyLook( "03", R.drawable.mylook_point_3))
            add(MyLook( "04", R.drawable.mylook_point_2))
            add(MyLook( "05", R.drawable.mylook_point_1))
        }

        photoDatas.apply {
            add(Photo( R.drawable.example1))
            add(Photo( R.drawable.example2))
            add(Photo( R.drawable.example3))
            add(Photo( R.drawable.example4))
            add(Photo( R.drawable.example1))
        }
        //더미데이터와 어댑터 연결
        val myLookRVAdapter = MyLookRVAdapter(myLookDatas, photoDatas)

        //리사이클러뷰와 어댑터 연결
        binding.myLookScrollRecyclerview.adapter = myLookRVAdapter

    }
}