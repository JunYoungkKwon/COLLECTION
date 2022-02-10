package com.eight.collection.ui.writing.first

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eight.collection.R
import com.eight.collection.databinding.ActivityWritefirstBinding
import com.eight.collection.ui.writing.first.top.WritefirstTopRVAdapter
import com.eight.collection.ui.writing.second.WritesecondActivity
import com.google.android.material.tabs.TabLayoutMediator

class WritefirstActivity : AppCompatActivity(), WritefirstTopRVAdapter.ColorClickListner{
    lateinit var binding : ActivityWritefirstBinding
    private var photoDatas = ArrayList<Photo>()
    val information = arrayListOf("TOP","BOTTOM","SHOES","ETC")
    private var colortext : String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWritefirstBinding.inflate(layoutInflater)

        setContentView(binding.root)


        //Photo 더미 데이터 생성
        photoDatas.apply {
            add(Photo(R.drawable.example1))
            add(Photo(R.drawable.example2))
            add(Photo(R.drawable.example3))
            add(Photo(R.drawable.example4))
            add(Photo(R.drawable.example1))
        }

        //Photo 부분 Adapter 연결
        val photoRVAdapter = PhotoRVAdapter(photoDatas)
        binding.writefirstPhotoRecyclerview.adapter = photoRVAdapter
        binding.writefirstPhotoRecyclerview.layoutManager = LinearLayoutManager(baseContext,LinearLayoutManager.HORIZONTAL,false)


        //Write First PAGE - 뷰페이저 연결
        val writefirstAdapter = WritefirstVPA(this)
        binding.writefirstColorVp.adapter = writefirstAdapter
        TabLayoutMediator(binding.writefirstColorTb,binding.writefirstColorVp){
                tab, position ->
            tab.text = information[position]
        }.attach()


        binding.writefirstColorTopSelectorRed.setOnClickListener{
            colortext = "red"
            colorTextPost()
        }



        // 다음버튼 클릭시 Writing Second Page Start
        binding.writefirstNextButton.setOnClickListener{
            startActivity(Intent(this, WritesecondActivity::class.java))
        }



    }

    override fun colorTextPost() {
    }


}