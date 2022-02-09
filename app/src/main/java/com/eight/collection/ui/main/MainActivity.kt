package com.eight.collection.ui.main

import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.eight.collection.R
import com.eight.collection.databinding.ActivityMainBinding
import com.eight.collection.databinding.CalendarYearMonthHeaderBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.main.lookpoint.MyLookFragment
import com.eight.collection.ui.main.week.WeekFragment

class MainActivity: BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    lateinit var fragment: MyLookFragment
    private lateinit var navHostFragment: NavHostFragment


    override fun initAfterBinding() {
        navHostFragment =

            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController: NavController = navHostFragment.findNavController()

        binding.mainBottomNavigation.setupWithNavController(navController)
        binding.mainBottomNavigation.itemIconTintList = null

    }
}