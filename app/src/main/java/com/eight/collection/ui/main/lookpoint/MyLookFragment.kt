package com.eight.collection.ui.main.lookpoint

import android.graphics.Path
import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.eight.collection.R
import com.eight.collection.databinding.FragmentMyLookBinding
import com.eight.collection.databinding.FragmentMonthBinding
import com.eight.collection.databinding.FragmentMatchBinding
import com.eight.collection.ui.BaseFragment
import com.eight.collection.ui.main.MainActivity
import com.eight.collection.ui.main.week.Diary
import com.eight.collection.ui.main.week.DiaryRVAdapter
import com.eight.collection.ui.main.week.Etc
import com.google.gson.Gson
import java.util.ArrayList

class MyLookFragment(): BaseFragment<FragmentMyLookBinding>(FragmentMyLookBinding::inflate) {

    override fun initAfterBinding() {

        var itemList = ArrayList<String>()
        var myLookDatas = mutableList()

        val myLookRVAdapter = MyLookRVAdapter(myLookDatas)
        binding.myLookScrollRecyclerview.adapter = myLookRVAdapter

        myLookRVAdapter.setMyitemClickListener(object : MyLookRVAdapter.MyitemClickListener{
            override fun onItemClik(myLook: MyLook) {
                startMyLookSecond(myLook)
            }

        })

        itemList.apply {
            add("작성한 OOTD를 룩포인트 별로 확인해보세요!")
            add("PHOTO를 탭하여 전체 페이지를 확인해보세요!")
        }

        val adapter = MyLookVPA(itemList, true)
        binding.myLookBannerVp.setAdapter(adapter)

    }

    private fun mutableList(): MutableList<MyLook> {
        var myLookDatas = mutableListOf(
            MyLook(
                "01", R.drawable.mylook_point_5, mutableListOf(
                    Photo(R.drawable.example1), Photo(R.drawable.example1),
                    Photo(R.drawable.example2), Photo(R.drawable.example1)
                )
            ),

            MyLook(
                "02", R.drawable.mylook_point_4, mutableListOf(
                    Photo(R.drawable.example1), Photo(R.drawable.example2),
                    Photo(R.drawable.example3), Photo(R.drawable.example4)
                )
            ),

            MyLook(
                "03", R.drawable.mylook_point_3, mutableListOf(
                    Photo(R.drawable.example2), Photo(R.drawable.example1),
                    Photo(R.drawable.example3), Photo(R.drawable.example4)
                )
            ),

            MyLook(
                "04", R.drawable.mylook_point_2, mutableListOf(
                    Photo(R.drawable.example4), Photo(R.drawable.example1),
                    Photo(R.drawable.example1), Photo(R.drawable.example1)
                )
            ),

            MyLook(
                "05", R.drawable.mylook_point_1, mutableListOf(
                    Photo(R.drawable.example3), Photo(R.drawable.example1),
                    Photo(R.drawable.example1)
                )
            ),
        )
        return myLookDatas
    }

    private fun startMyLookSecond(myLook: MyLook) {
        arguments = Bundle().apply {
            val gson = Gson()
            val myLookJson = gson.toJson(myLook)
            putString("myLook", myLookJson)
        }
        findNavController().navigate(R.id.MyLookSecondFragment, arguments)
    }
    override fun onResume() {
        super.onResume()
        binding.myLookBannerVp.resumeAutoScroll()
    }

    override fun onPause() {
        binding.myLookBannerVp.pauseAutoScroll()
        super.onPause()
    }

}