package com.eight.collection.ui.main.mylook

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import com.eight.collection.R
import com.eight.collection.data.remote.mylook.MyLookResult
import com.eight.collection.data.remote.mylook.MyLookService
import com.eight.collection.databinding.ActivityMyLookSecondBinding
import com.eight.collection.databinding.ItemMyLookSecondPhotoBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.finish.FinishActivity
import com.google.gson.Gson
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class

MyLookDetailActivity(): BaseActivity<ActivityMyLookSecondBinding>(ActivityMyLookSecondBinding::inflate), MyLookDetailView {

    private  lateinit var myLookDetailRVAdapter: MyLookDetailRVAdapter
    private var itemList = ArrayList<String>()

    override fun initAfterBinding() {
        setInit()


        itemList.apply {
            add("사진을 탭하여 작성날짜를 확인해보세요!")
            add("사진을 길게 터치해서 상세페이지를 확인해보세요!")
        }

        val adapter = MyLookVPA(itemList, true)
        binding.myLookSecondBannerVp.setAdapter(adapter)

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

        if(myLookResult.lastOOTDDetailArr.isNullOrEmpty()){
            binding.weekDefault1Text.visibility = View.VISIBLE
            binding.weekDefault2Text.visibility = View.VISIBLE
            binding.weekDefaultIv.visibility = View.VISIBLE
        }
        else{
            binding.weekDefault1Text.visibility = View.GONE
            binding.weekDefault2Text.visibility = View.GONE
            binding.weekDefaultIv.visibility = View.GONE
        }


        myLookDetailRVAdapter.setMyItemClickListener(object :MyLookDetailRVAdapter.MyitemClickListener{
            override fun onItemClick(myLookOOTD: MyLookOOTD, position: Int) {
            }

        })

        myLookDetailRVAdapter.setMyItemLongClickListener(object :MyLookDetailRVAdapter.MyitemLongClickListener{
            override fun onItemLongClick(myLookOOTD: MyLookOOTD, position: Int) {
                val date: Date = myLookOOTD.date
                val localdate: LocalDate = date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                val formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val convertDate: String = localdate.format(formatters)

                val intent = Intent(applicationContext, FinishActivity::class.java)
                intent.apply {
                    this.putExtra("date",convertDate)
                }
                startActivity(intent)
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