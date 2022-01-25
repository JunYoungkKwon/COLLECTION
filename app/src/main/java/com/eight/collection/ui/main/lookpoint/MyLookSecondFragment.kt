package com.eight.collection.ui.main.lookpoint

import android.view.View
import androidx.navigation.fragment.navArgs
import com.eight.collection.R
import com.eight.collection.databinding.FragmentMyLookBinding
import com.eight.collection.databinding.FragmentMonthBinding
import com.eight.collection.databinding.FragmentMatchBinding
import com.eight.collection.databinding.FragmentMyLookSecondBinding
import com.eight.collection.ui.BaseFragment
import com.eight.collection.ui.main.week.Diary
import com.eight.collection.ui.main.week.DiaryRVAdapter
import com.eight.collection.ui.main.week.Etc
import com.google.gson.Gson
import java.util.ArrayList

class MyLookSecondFragment(): BaseFragment<FragmentMyLookSecondBinding>(FragmentMyLookSecondBinding::inflate) {

    private  var gson: Gson = Gson()
    private  var photoDatas = ArrayList<Photo>();

    override fun initAfterBinding() {

        photoDatas.apply {
            add(Photo(R.drawable.example1))
            add(Photo(R.drawable.example2))
            add(Photo(R.drawable.example3))
            add(Photo(R.drawable.example4))
            add(Photo(R.drawable.example1))
            add(Photo(R.drawable.example2))
            add(Photo(R.drawable.example3))
            add(Photo(R.drawable.example4))
        }
        //더미데이터와 어댑터 연결
        val myLookSecondRVAdapter = MyLookSecondRVAdapter(photoDatas)
        //리사이클러뷰와 어댑터 연결
        binding.myLookSecondScrollRecyclerview.adapter = myLookSecondRVAdapter

        //MyLook에서 넘어온 데이터 받아오기
        val myLookData = arguments?.getString("myLook")
        val myLook = gson.fromJson(myLookData, MyLook::class.java)

        //MyLook에서 넘어온 데이터 반영
        setInit(myLook)

    }
    private fun setInit(myLook: MyLook) {
        binding.myLookSecondPoint5Iv.setImageResource(myLook.pointImg!!)
        binding.myLookSecond01Tv.text = myLook.number

    }


}