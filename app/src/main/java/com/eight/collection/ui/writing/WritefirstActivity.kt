package com.eight.collection.ui.writing

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.eight.collection.R
import com.eight.collection.databinding.ActivityWritefirstBinding
import com.eight.collection.databinding.FragmentWritefirstTopBinding
import com.google.android.material.tabs.TabLayoutMediator

class WritefirstActivity : AppCompatActivity(), CustomDialogInterface{
    lateinit var binding : ActivityWritefirstBinding

    private var photoDatas = ArrayList<Photo>();


    val information = arrayListOf("TOP","BOTTOM","SHOES","ETC")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWritefirstBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.writefirstNextButton.setOnClickListener {
            startActivity(Intent(this, WritesecondActivity::class.java))
        }



        val writefirstAdapter = WritefirstVPA(this)
        binding.writefirstColorVp.adapter = writefirstAdapter
        TabLayoutMediator(binding.writefirstColorTb,binding.writefirstColorVp){
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


        val customDialog = CustomDialog(this,this)
        binding.writefirstColorTopSelectorRed.setOnClickListener{
            customDialog.show()
        }

    }

    override fun onAddButtonClicked() {
        Toast.makeText(this, "추가", Toast.LENGTH_SHORT).show()
    }

    override fun onCancelButtonClicked() {
        Toast.makeText(this, "취소", Toast.LENGTH_SHORT).show()
    }


}