package com.eight.collection.ui.main.mylook

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import com.eight.collection.data.remote.mylook.MyLookResult
import com.eight.collection.data.remote.mylook.MyLookService
import com.eight.collection.databinding.ActivityMyLookBinding
import com.eight.collection.ui.BaseActivity
import java.util.ArrayList

class MyLookActivity(): BaseActivity<ActivityMyLookBinding>(ActivityMyLookBinding::inflate),View.OnClickListener, MyLookView, MyLook2View, MyLook3View , MyLook4View, MyLook5View   {


    private  lateinit var myLookRVAdapter: MyLookRVAdapter

    override fun initAfterBinding() {
        binding.mylookFirstCl.setOnClickListener(this)
        binding.mylookSecondCl.setOnClickListener(this)
        binding.mylookThirdCl.setOnClickListener(this)
        binding.mylookFourthCl.setOnClickListener(this)
        binding.mylookFifthCl.setOnClickListener(this)

        var itemList = ArrayList<String>()

        itemList.apply {
            add("작성한 OOTD를 룩포인트 별로 확인해보세요!")
            add("PHOTO를 탭하여 전체 페이지를 확인해보세요!")
        }

        val adapter = MyLookVPA(itemList, true)
        binding.myLookBannerVp.setAdapter(adapter)

    }

    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
            binding.mylookFirstCl -> {
                val intent = Intent(this, MyLookDetailActivity::class.java)
                val num = binding.itemMyLook01Tv.text.toString()
                intent.apply {
                    this.putExtra("number",num)
                }
                startActivity(intent)
            }
            binding.mylookSecondCl -> {
                val intent = Intent(this, MyLookDetailActivity::class.java)
                val num = binding.itemMyLook02Tv.text.toString()
                intent.apply {
                    this.putExtra("number",num)
                }
                startActivity(intent)
            }
            binding.mylookThirdCl -> {
                val intent = Intent(this, MyLookDetailActivity::class.java)
                val num = binding.itemMyLook03Tv.text.toString()
                intent.apply {
                    this.putExtra("number",num)
                }
                startActivity(intent)
            }
            binding.mylookFourthCl -> {
                val intent = Intent(this, MyLookDetailActivity::class.java)
                val num = binding.itemMyLook04Tv.text.toString()
                intent.apply {
                    this.putExtra("number",num)
                }
                startActivity(intent)
            }
            binding.mylookFifthCl -> {
                val intent = Intent(this, MyLookDetailActivity::class.java)
                val num = binding.itemMyLook05Tv.text.toString()
                intent.apply {
                    this.putExtra("number",num)
                }
                startActivity(intent)
            }
        }
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
        myLookRVAdapter = MyLookRVAdapter(this)
        binding.itemMyLookImgRecyclerview.adapter = myLookRVAdapter
        myLookRVAdapter.addOOTD(myLookResult.lastOOTDArr)

        myLookRVAdapter.setMyItemClickListener(object :MyLookRVAdapter.MyitemClickListener{

            override fun onItemClick(myLookOOTD: MyLookOOTD, position: Int, context: Context) {
                val intent = Intent(context, MyLookDetailActivity::class.java)
                val num = binding.itemMyLook01Tv.text.toString()
                intent.apply {
                    this.putExtra("number", num)
                }
                startActivity(intent)
            }

        })

        if(myLookResult.lastOOTDArr.isNullOrEmpty()){
            binding.weekDefault11Text.visibility = View.VISIBLE
            binding.itemMyLookImgRecyclerview.visibility = View.GONE
        }
        else{
            binding.weekDefault11Text.visibility = View.GONE
            binding.itemMyLookImgRecyclerview.visibility = View.VISIBLE
        }

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
        myLookRVAdapter = MyLookRVAdapter(this)
        binding.itemMyLookImg2Recyclerview.adapter = myLookRVAdapter
        myLookRVAdapter.addOOTD(myLookResult.lastOOTDArr)

        myLookRVAdapter.setMyItemClickListener(object :MyLookRVAdapter.MyitemClickListener{

            override fun onItemClick(myLookOOTD: MyLookOOTD, position: Int, context: Context) {
                val intent = Intent(context, MyLookDetailActivity::class.java)
                val num = binding.itemMyLook02Tv.text.toString()
                intent.apply {
                    this.putExtra("number", num)
                }
                startActivity(intent)
            }

        })

        if(myLookResult.lastOOTDArr.isNullOrEmpty()){
            binding.weekDefault12Text.visibility = View.VISIBLE
            binding.itemMyLookImg2Recyclerview.visibility = View.GONE

        }
        else{
            binding.weekDefault12Text.visibility = View.GONE
            binding.itemMyLookImg2Recyclerview.visibility = View.VISIBLE
        }
    }

    override fun onMyLook2Failure(code: Int, message: String) {}
    override fun onMyLook3Loading() {}

    override fun onMyLook3Success(myLookResult: MyLookResult) {
        myLookRVAdapter = MyLookRVAdapter(this)
        binding.itemMyLookImg3Recyclerview.adapter = myLookRVAdapter
        myLookRVAdapter.addOOTD(myLookResult.lastOOTDArr)

        myLookRVAdapter.setMyItemClickListener(object :MyLookRVAdapter.MyitemClickListener{

            override fun onItemClick(myLookOOTD: MyLookOOTD, position: Int, context: Context) {
                val intent = Intent(context, MyLookDetailActivity::class.java)
                val num = binding.itemMyLook03Tv.text.toString()
                intent.apply {
                    this.putExtra("number", num)
                }
                startActivity(intent)
            }

        })

        if(myLookResult.lastOOTDArr.isNullOrEmpty()){
            binding.weekDefault13Text.visibility = View.VISIBLE
            binding.itemMyLookImg3Recyclerview.visibility = View.GONE
        }
        else{
            binding.weekDefault13Text.visibility = View.GONE
            binding.itemMyLookImg3Recyclerview.visibility = View.VISIBLE
        }
    }

    override fun onMyLook3Failure(code: Int, message: String) {}

    override fun onMyLook4Loading() {}

    override fun onMyLook4Success(myLookResult: MyLookResult) {
        myLookRVAdapter = MyLookRVAdapter(this)
        binding.itemMyLookImg4Recyclerview.adapter = myLookRVAdapter
        myLookRVAdapter.addOOTD(myLookResult.lastOOTDArr)

        myLookRVAdapter.setMyItemClickListener(object :MyLookRVAdapter.MyitemClickListener{

            override fun onItemClick(myLookOOTD: MyLookOOTD, position: Int, context: Context) {
                val intent = Intent(context, MyLookDetailActivity::class.java)
                val num = binding.itemMyLook04Tv.text.toString()
                intent.apply {
                    this.putExtra("number", num)
                }
                startActivity(intent)
            }

        })

        if(myLookResult.lastOOTDArr.isNullOrEmpty()){
            binding.weekDefault14Text.visibility = View.VISIBLE
            binding.itemMyLookImg4Recyclerview.visibility = View.GONE
        }
        else{
            binding.weekDefault14Text.visibility = View.GONE
            binding.itemMyLookImg4Recyclerview.visibility = View.VISIBLE
        }
    }

    override fun onMyLook4Failure(code: Int, message: String) {}

    override fun onMyLook5Loading() {}

    override fun onMyLook5Success(myLookResult: MyLookResult) {
        myLookRVAdapter = MyLookRVAdapter(this)
        binding.itemMyLookImg5Recyclerview.adapter = myLookRVAdapter
        myLookRVAdapter.addOOTD(myLookResult.lastOOTDArr)

        myLookRVAdapter.setMyItemClickListener(object :MyLookRVAdapter.MyitemClickListener{

            override fun onItemClick(myLookOOTD: MyLookOOTD, position: Int, context: Context) {
                val intent = Intent(context, MyLookDetailActivity::class.java)
                val num = binding.itemMyLook05Tv.text.toString()
                intent.apply {
                    this.putExtra("number", num)
                }
                startActivity(intent)
            }

        })

        if(myLookResult.lastOOTDArr.isNullOrEmpty()){
            binding.weekDefault15Text.visibility = View.VISIBLE
            binding.itemMyLookImg5Recyclerview.visibility = View.GONE
        }
        else{
            binding.weekDefault15Text.visibility = View.GONE
            binding.itemMyLookImg5Recyclerview.visibility = View.VISIBLE
        }
    }

    override fun onMyLook5Failure(code: Int, message: String) {}

}