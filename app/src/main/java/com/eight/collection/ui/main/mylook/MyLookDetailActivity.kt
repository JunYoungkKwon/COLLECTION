package com.eight.collection.ui.main.mylook

import android.content.Intent
import android.util.Log
import android.view.View
import com.eight.collection.R
import com.eight.collection.data.remote.mylook.MyLookResult
import com.eight.collection.data.remote.mylook.MyLookService
import com.eight.collection.databinding.*
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.finish.FinishActivity
import com.google.gson.Gson
import java.util.ArrayList

class

MyLookDetailActivity(): BaseActivity<ActivityMyLookSecondBinding>(ActivityMyLookSecondBinding::inflate), MyLookDetailView {

    private  lateinit var myLookDetailRVAdapter: MyLookDetailRVAdapter
    lateinit var binding1: ItemMyLookSecondPhotoBinding

    private var isClick: Boolean = true
    private  var gson: Gson = Gson()
    private  var photoDatas = ArrayList<Photo>()
    private var itemList = ArrayList<String>()

    override fun initAfterBinding() {
        setInit()
        binding1 = ItemMyLookSecondPhotoBinding.inflate(layoutInflater)

        itemList.apply {
            add("사진을 탭하여 작성날짜를 확인해보세요!")
            add("더블탭으로 상세페이지를 확인해보세요!")
        }

        val adapter = MyLookVPA(itemList, true)
        binding.myLookSecondBannerVp.setAdapter(adapter)

    }

    private fun startFinishAcitivity(photo: Photo) {
        startActivity(Intent(this, FinishActivity::class.java))
    }

    private fun setInit() {
        val num = intent.getStringExtra("number")
        when(num){
            "01" -> {
                binding.myLookSecond01Tv.text = "01"
                binding.myLookSecondPoint5Iv.setImageResource(R.drawable.mylook_point_5)
                MyLookService.getDetailMyLook(this, 5)
            }
            "02" -> {
                binding.myLookSecond01Tv.text = "02"
                binding.myLookSecondPoint5Iv.setImageResource(R.drawable.mylook_point_4)
                MyLookService.getDetailMyLook(this, 4)
            }
            "03" -> {
                binding.myLookSecond01Tv.text = "03"
                binding.myLookSecondPoint5Iv.setImageResource(R.drawable.mylook_point_3)
                MyLookService.getDetailMyLook(this, 3)
            }
            "04" -> {
                binding.myLookSecond01Tv.text = "04"
                binding.myLookSecondPoint5Iv.setImageResource(R.drawable.mylook_point_2)
                MyLookService.getDetailMyLook(this, 2)
            }
            "05" -> {
                binding.myLookSecond01Tv.text = "05"
                binding.myLookSecondPoint5Iv.setImageResource(R.drawable.mylook_point_1)
                MyLookService.getDetailMyLook(this, 1)
            }
        }
    }

    override fun onMyLookDetailLoading() {}

    override fun onMyLookDetailSuccess(myLookResult: MyLookResult) {
        myLookDetailRVAdapter = MyLookDetailRVAdapter(this)
        binding.myLookSecondScrollRecyclerview.adapter = myLookDetailRVAdapter
        myLookDetailRVAdapter.addOOTD(myLookResult.lastOOTDDetailArr)

        myLookDetailRVAdapter.setMyItemClickListener(object : MyLookDetailRVAdapter.MyitemClickListener{

            override fun onItemClick(myLookOOTD: MyLookOOTD) {
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
    }

    override fun onMyLookDetailFailure(code: Int, message: String) {
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


}