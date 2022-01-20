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

    override fun initAfterBinding() {
        setUpRecyclerView()
//        myLookDatas.apply {
//            add(MyLook( "01", R.drawable.mylook_point_5))
//            add(MyLook( "02", R.drawable.mylook_point_4))
//            add(MyLook( "03", R.drawable.mylook_point_3))
//            add(MyLook( "04", R.drawable.mylook_point_2))
//            add(MyLook( "05", R.drawable.mylook_point_1))
//        }
//
//        photoDatas.apply {
//            add(Photo( R.drawable.example1))
//            add(Photo( R.drawable.example2))
//            add(Photo( R.drawable.example3))
//            add(Photo( R.drawable.example4))
//            add(Photo( R.drawable.example1))
//        }
    }

    private fun setUpRecyclerView() {
        var itemList = mutableListOf(
            MyLook("01", R.drawable.mylook_point_5, mutableListOf(
                Photo(R.drawable.example1), Photo(R.drawable.example1),
                Photo(R.drawable.example2), Photo(R.drawable.example1)
            )
            ),

            MyLook("02", R.drawable.mylook_point_4, mutableListOf(
                Photo(R.drawable.example1), Photo(R.drawable.example2),
                Photo(R.drawable.example3), Photo(R.drawable.example4)
            )
            ),

            MyLook("03",R.drawable.mylook_point_3, mutableListOf(
                Photo(R.drawable.example2), Photo(R.drawable.example1),
                Photo(R.drawable.example3), Photo(R.drawable.example4)
            )
            ),

            MyLook("04",R.drawable.mylook_point_2, mutableListOf(
                Photo(R.drawable.example4), Photo(R.drawable.example1),
                Photo(R.drawable.example1), Photo(R.drawable.example1)
            )
            ),

            MyLook("05",R.drawable.mylook_point_1, mutableListOf(
                Photo(R.drawable.example3), Photo(R.drawable.example1),
                Photo(R.drawable.example1)
            )
            ),
        )

        binding.myLookScrollRecyclerview.adapter = MyLookRVAdapter(itemList)
    }
}