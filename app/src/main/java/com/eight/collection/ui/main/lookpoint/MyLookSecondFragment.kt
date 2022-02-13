package com.eight.collection.ui.main.lookpoint

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.eight.collection.R
import com.eight.collection.databinding.*
import com.eight.collection.ui.BaseFragment
import com.eight.collection.ui.finish.FinishActivity
import com.eight.collection.ui.login.LoginFirstActivity
import com.eight.collection.ui.main.week.Diary
import com.eight.collection.ui.main.week.DiaryRVAdapter
import com.eight.collection.ui.main.week.Etc
import com.eight.collection.ui.writing.first.WritefirstActivity
import com.google.gson.Gson
import java.util.ArrayList

class

MyLookSecondFragment(): BaseFragment<FragmentMyLookSecondBinding>(FragmentMyLookSecondBinding::inflate) {

    lateinit var binding1: ItemMyLookSecondPhotoBinding

    private var isClick: Boolean = true
    private  var gson: Gson = Gson()
    private  var photoDatas = ArrayList<Photo>()
    private var itemList = ArrayList<String>()

    override fun initAfterBinding() {
        binding1 = ItemMyLookSecondPhotoBinding.inflate(layoutInflater)

        itemList.apply {
            add("사진을 탭하여 작성날짜를 확인해보세요!")
            add("더블탭으로 상세페이지를 확인해보세요!")
        }

        photoDatas.apply {
            add(Photo(R.drawable.example5_2021_12_25, "2021.12.25"))
            add(Photo(R.drawable.example5_2022_01_05, "2022.01.05"))
            add(Photo(R.drawable.example5_2022_01_07, "2022.01.07"))
            add(Photo(R.drawable.example5_2022_01_13, "2022.01.13"))
            add(Photo(R.drawable.example5_2022_01_14, "2022.01.14"))
            add(Photo(R.drawable.example5_2022_01_15, "2022.01.15"))
            add(Photo(R.drawable.example5_2022_01_20, "2022.01.20"))
            add(Photo(R.drawable.example5_2022_01_25, "2022.01.25"))
            add(Photo(R.drawable.example5_2022_01_28, "2022.01.28"))
            add(Photo(R.drawable.example5_2022_02_01, "2022.02.01"))
            add(Photo(R.drawable.example5_2022_02_10, "2022.02.10"))
            add(Photo(R.drawable.example_0213_1, "2022.02.12"))
        }

        val adapter = MyLookVPA(itemList, true)
        binding.myLookSecondBannerVp.setAdapter(adapter)

        //더미데이터와 어댑터 연결
        val myLookSecondRVAdapter = MyLookSecondRVAdapter(photoDatas)
        //리사이클러뷰와 어댑터 연결
        binding.myLookSecondScrollRecyclerview.adapter = myLookSecondRVAdapter

        myLookSecondRVAdapter.setMyItemClickListener(object : MyLookSecondRVAdapter.MyitemClickListener{

            override fun onItemClick(photo: Photo) {
                if(isClick == true){
                    binding1.itemMyLookDateTv.visibility = View.VISIBLE
                    binding1.itemMyLookDimBackground.visibility = View.VISIBLE
                    isClick = false

                }
                else{
                    binding1.itemMyLookDateTv.visibility = View.GONE
                    binding1.itemMyLookDimBackground.visibility = View.GONE
                    isClick = true

                    //startFinishAcitivity(photo)
                }
            }

        })

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

    private fun startFinishAcitivity(photo: Photo) {
        startActivity(Intent(activity, FinishActivity::class.java))
    }


}