package com.eight.collection.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.eight.collection.R
import com.eight.collection.databinding.ActivityMainBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.writing.WritefirstActivity

class MainActivity: BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private lateinit var navHostFragment: NavHostFragment


    override fun initAfterBinding() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController: NavController = navHostFragment.findNavController()

        binding.mainBottomNavigation.setupWithNavController(navController)
        binding.mainBottomNavigation.itemIconTintList = null

    }

    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)

    }




}