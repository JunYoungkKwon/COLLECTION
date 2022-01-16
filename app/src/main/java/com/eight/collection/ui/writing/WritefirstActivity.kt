package com.eight.collection.ui.writing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eight.collection.databinding.ActivityWritefirstBinding
import com.eight.collection.ui.main.month.MonthFragment
import com.google.android.material.tabs.TabLayoutMediator

class WritefirstActivity : AppCompatActivity(){
    lateinit var binding : ActivityWritefirstBinding

    val information = arrayListOf("TOP","BOTTOM","SHOES","ETC")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWritefirstBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}