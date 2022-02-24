package com.eight.collection.ui.writing.second

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.eight.collection.R
import com.eight.collection.data.remote.auth.AuthService
import com.eight.collection.databinding.ActivityWritesecondBinding
import com.eight.collection.ui.finish.FinishActivity
import com.eight.collection.ui.writing.ReceiveS3URLView
import com.google.android.material.tabs.TabLayoutMediator

class WritesecondActivity : AppCompatActivity(), ReceiveS3URLView{
    lateinit var binding : ActivityWritesecondBinding
    val information = arrayListOf("PLACE","WEATHER","WHO")
    lateinit var Url : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWritesecondBinding.inflate(layoutInflater)

        setContentView(binding.root)



        val writesecondAdapter = WritesecondVPA(this)
        binding.writesecondVp.adapter = writesecondAdapter
        TabLayoutMediator(binding.writesecondTb,binding.writesecondVp){
                tab, position ->
            tab.text = information[position]
        }.attach()


        binding.writesecondFinishButton2.setOnClickListener {

            startActivity(Intent(this, FinishActivity::class.java))
        }
    }

    // Presigned URL 받아오기 메소드

    /*private fun receiveS3Url() : String {
        AuthService.checkNickname(this, nickname)
    }*/

    override fun onReceiveS3URLLoading() {

    }

    override fun onReceiveS3URLSuccess(url : String){
        Url = url
    }

    override fun onReceiveS3URLFailure(code: Int, message: String) {
    }


}