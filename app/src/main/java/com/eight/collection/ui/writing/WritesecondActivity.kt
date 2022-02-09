package com.eight.collection.ui.writing

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eight.collection.databinding.ActivityWritesecondBinding
import com.eight.collection.ui.finish.FinishActivity
import com.google.android.material.tabs.TabLayoutMediator

class WritesecondActivity : AppCompatActivity(){
    lateinit var binding : ActivityWritesecondBinding

    private var photoDatas = ArrayList<Photo>();


    val information = arrayListOf("PLACE","WEATHER","WHO")

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


}