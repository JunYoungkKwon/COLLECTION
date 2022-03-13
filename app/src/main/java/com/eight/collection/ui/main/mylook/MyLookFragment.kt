package com.eight.collection.ui.main.mylook

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.eight.collection.R
import com.eight.collection.data.entities.MyLook
import com.eight.collection.data.remote.mylook.MyLookResult
import com.eight.collection.data.remote.mylook.MyLookService
import com.eight.collection.databinding.FragmentMyLookBinding
import com.eight.collection.ui.BaseFragment
import com.eight.collection.ui.main.week.DiaryRVAdapter
import com.google.gson.Gson
import java.util.ArrayList

class MyLookFragment(): BaseFragment<FragmentMyLookBinding>(FragmentMyLookBinding::inflate), MyLookView, MyLook2View, MyLook3View , MyLook4View, MyLook5View   {

    private  lateinit var myLookRVAdapter: MyLookRVAdapter

    override fun initAfterBinding() {

        var itemList = ArrayList<String>()
//        var myLookDatas = mutableList()
//
//        myLookRVAdapter = MyLookRVAdapter(myLookDatas)
//        binding.myLookScrollRecyclerview.adapter = myLookRVAdapter
//
//        myLookRVAdapter.setMyitemClickListener(object : MyLookRVAdapter.MyitemClickListener{
//            override fun onItemClik(myLookMain: MyLookMain) {
//                startMyLookSecond(myLookMain)
//            }
//
//        })

        itemList.apply {
            add("작성한 OOTD를 룩포인트 별로 확인해보세요!")
            add("PHOTO를 탭하여 전체 페이지를 확인해보세요!")
        }

        val adapter = MyLookVPA(itemList, true)
        binding.myLookBannerVp.setAdapter(adapter)

    }

//    private fun mutableList(): MutableList<MyLookMain> {
//        var myLookDatas = mutableListOf(
//            MyLookMain(
//                "01", R.drawable.mylook_point_5, mutableListOf(
//                )
//            ),
//
//            MyLookMain(
//                "02", R.drawable.mylook_point_4, mutableListOf(
//                )
//            ),
//
//            MyLookMain(
//                "03", R.drawable.mylook_point_3, mutableListOf()
//            ),
//
//            MyLookMain(
//                "04", R.drawable.mylook_point_2, mutableListOf()
//            ),
//
//            MyLookMain(
//                "05", R.drawable.mylook_point_1, mutableListOf()
//            ),
//        )
//        return myLookDatas
//    }

    private fun startMyLookSecond(myLookOOTD: MyLookMain) {
        arguments = Bundle().apply {
            val gson = Gson()
            val myLookJson = gson.toJson(myLookOOTD)
            putString("myLook", myLookJson)
        }
        findNavController().navigate(R.id.MyLookSecondFragment, arguments)
    }
    override fun onResume() {
        super.onResume()
        binding.myLookBannerVp.resumeAutoScroll()
        getPhoto()
    }

    override fun onPause() {
        binding.myLookBannerVp.pauseAutoScroll()
        super.onPause()
    }

    private fun getPhoto() {
        MyLookService.getMainMyLook(this, 5)
        MyLookService.getMain2MyLook(this, 4)
        MyLookService.getMain3MyLook(this, 3)
        MyLookService.getMain4MyLook(this, 2)
        MyLookService.getMain5MyLook(this, 1)
    }

    override fun onMyLookLoading() {}

    override fun onMyLookSuccess(myLookResult: MyLookResult) {
        myLookRVAdapter = MyLookRVAdapter(requireContext())
        binding.itemMyLookImgRecyclerview.adapter = myLookRVAdapter
        myLookRVAdapter.addOOTD(myLookResult.lastOOTDArr)
    }

    override fun onMyLookFailure(code: Int, message: String) {
        when(code) {

            2000, 2001, 2002 -> {
                Log.d("MyLook/JWT/Error", "error")
            }

            3031, 3032 -> {
                Log.d("MyLook/LookPoint/Error", "error")
            }

            5000, 6000 -> {
                Log.d("MyLook/Server/Error", "error")
            }
        }
    }

    override fun onMyLook2Loading() {}

    override fun onMyLook2Success(myLookResult: MyLookResult) {
        myLookRVAdapter = MyLookRVAdapter(requireContext())
        binding.itemMyLookImg2Recyclerview.adapter = myLookRVAdapter
        myLookRVAdapter.addOOTD(myLookResult.lastOOTDArr)
    }

    override fun onMyLook2Failure(code: Int, message: String) {}
    override fun onMyLook3Loading() {}

    override fun onMyLook3Success(myLookResult: MyLookResult) {
        myLookRVAdapter = MyLookRVAdapter(requireContext())
        binding.itemMyLookImg3Recyclerview.adapter = myLookRVAdapter
        myLookRVAdapter.addOOTD(myLookResult.lastOOTDArr)
    }

    override fun onMyLook3Failure(code: Int, message: String) {}

    override fun onMyLook4Loading() {}

    override fun onMyLook4Success(myLookResult: MyLookResult) {
        myLookRVAdapter = MyLookRVAdapter(requireContext())
        binding.itemMyLookImg4Recyclerview.adapter = myLookRVAdapter
        myLookRVAdapter.addOOTD(myLookResult.lastOOTDArr)
    }

    override fun onMyLook4Failure(code: Int, message: String) {}

    override fun onMyLook5Loading() {}

    override fun onMyLook5Success(myLookResult: MyLookResult) {
        myLookRVAdapter = MyLookRVAdapter(requireContext())
        binding.itemMyLookImg5Recyclerview.adapter = myLookRVAdapter
        myLookRVAdapter.addOOTD(myLookResult.lastOOTDArr)
    }

    override fun onMyLook5Failure(code: Int, message: String) {}

}