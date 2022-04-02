package com.eight.collection.ui.main

import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.eight.collection.R
import com.eight.collection.databinding.ActivityMainBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.introduce.IntroduceDialog
import com.eight.collection.ui.main.mylook.MyLookActivity
import com.eight.collection.utils.getIntroduceIs

class MainActivity: BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    lateinit var fragment: MyLookActivity
    private lateinit var navHostFragment: NavHostFragment

    var getIntroduceIs : Boolean = getIntroduceIs()


    override fun initAfterBinding() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController: NavController = navHostFragment.findNavController()


        if(getIntroduceIs == false){
            startActivity(Intent(this, IntroduceDialog ::class.java))
        }

        binding.mainBottomNavigation.setupWithNavController(navController)
        binding.mainBottomNavigation.itemIconTintList = null

    }
}