package com.eight.collection.ui.writing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eight.collection.databinding.ActivityWritefirstBinding

class WritefirstActivity : AppCompatActivity(){
    lateinit var binding : ActivityWritefirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWritefirstBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}