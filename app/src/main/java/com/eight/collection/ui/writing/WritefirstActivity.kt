package com.eight.collection.ui.writing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.eight.collection.R
import com.eight.collection.databinding.ActivityWritefirstBinding
import com.google.android.material.tabs.TabLayoutMediator

class WritefirstActivity : AppCompatActivity(){
    lateinit var binding : ActivityWritefirstBinding
    private var photoDatas = ArrayList<Photo>();

    val information = arrayListOf("TOP","BOTTOM","SHOES","ETC")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWritefirstBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val writefirstAdapter = WritefirstVPA(this)
        binding.writefirstColorVp.adapter = writefirstAdapter
        TabLayoutMediator(binding.wrtiefirstColorTb,binding.writefirstColorVp){
                tab, position ->
            tab.text = information[position]
        }.attach()


        //더미 데이터 리스트 생성
        photoDatas.apply {
            add(Photo(R.drawable.example1))
            add(Photo(R.drawable.example2))
            add(Photo(R.drawable.example3))
            add(Photo(R.drawable.example4))
            add(Photo(R.drawable.example1))
        }

        val photoRVAdapter = PhotoRVAdapter(photoDatas)

        binding.writefirstPhotoRecyclerview.adapter = photoRVAdapter

        binding.writefirstPhotoRecyclerview.layoutManager = LinearLayoutManager(baseContext,LinearLayoutManager.HORIZONTAL,false)
    }


}