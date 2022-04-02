package com.eight.collection.ui.introduce

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.eight.collection.R
import com.eight.collection.databinding.DialogIntroduceBinding
import com.eight.collection.ui.login.LoginFirstActivity
import com.eight.collection.ui.splash.SplashActivity
import com.eight.collection.utils.saveIntroduceIs

class IntroduceDialog : AppCompatActivity() {
    lateinit var binding : com.eight.collection.databinding.DialogIntroduceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogIntroduceBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var adapter = IntroduceVPA(supportFragmentManager)
        binding.introduceVp.adapter = adapter



        //indicator

        binding.introduceVp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0:Int){

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(p0: Int) {
                when (p0) {
                    0 -> {
                        binding.introduceFirstIc.visibility = View.VISIBLE
                        binding.introduceSecondIc.visibility = View.GONE
                        binding.introduceThirdIc.visibility = View.GONE
                        binding.introduceFourthIc.visibility = View.GONE

                        binding.introduceNextIc.visibility = View.VISIBLE
                        binding.introducePreviousIc.visibility = View.INVISIBLE
                        binding.introduceCloseIc.visibility = View.INVISIBLE
                    }

                    1 -> {
                        binding.introduceFirstIc.visibility = View.GONE
                        binding.introduceSecondIc.visibility = View.VISIBLE
                        binding.introduceThirdIc.visibility = View.GONE
                        binding.introduceFourthIc.visibility = View.GONE

                        binding.introduceNextIc.visibility = View.VISIBLE
                        binding.introducePreviousIc.visibility = View.VISIBLE
                        binding.introduceCloseIc.visibility = View.INVISIBLE
                    }

                    2 -> {
                        binding.introduceFirstIc.visibility = View.GONE
                        binding.introduceSecondIc.visibility = View.GONE
                        binding.introduceThirdIc.visibility = View.VISIBLE
                        binding.introduceFourthIc.visibility = View.GONE

                        binding.introduceNextIc.visibility = View.VISIBLE
                        binding.introducePreviousIc.visibility = View.VISIBLE
                        binding.introduceCloseIc.visibility = View.INVISIBLE
                    }

                    else -> {
                        binding.introduceFirstIc.visibility = View.GONE
                        binding.introduceSecondIc.visibility = View.GONE
                        binding.introduceThirdIc.visibility = View.GONE
                        binding.introduceFourthIc.visibility = View.VISIBLE

                        binding.introduceNextIc.visibility = View.INVISIBLE
                        binding.introducePreviousIc.visibility = View.VISIBLE
                        binding.introduceCloseIc.visibility = View.VISIBLE


                        binding.introduceStartTv.visibility = View.VISIBLE
                        binding.introduceStart01Iv.visibility = View.VISIBLE
                        binding.introduceStart02Iv.visibility = View.VISIBLE
                        binding.introduceStart03Iv.visibility = View.VISIBLE
                        binding.introduceStart04Iv.visibility = View.VISIBLE
                        binding.introduceStart05Iv.visibility = View.VISIBLE
                        binding.introduceStart06Iv.visibility = View.VISIBLE
                    }
                }
            }
        })

        binding.introduceNextIc.setOnClickListener {
            var current = binding.introduceVp.currentItem
            if (current == 0){
                binding.introduceVp.setCurrentItem(1,true)
            }
            else if (current == 1){
                binding.introduceVp.setCurrentItem(2,true)
            }
            else if (current == 2){
                binding.introduceVp.setCurrentItem(3,true)
            }
        }

        binding.introducePreviousIc.setOnClickListener{
            var current = binding.introduceVp.currentItem
            if (current == 1){
                binding.introduceVp.setCurrentItem(0,true)
            }
            else if (current == 2){
                binding.introduceVp.setCurrentItem(1,true)
            }
            else if (current == 3){
                binding.introduceVp.setCurrentItem(2,true)
            }
        }

        binding.introduceCloseIc.setOnClickListener{
            saveIntroduceIs(true)
            finish()
        }

        binding.introduceStartTv.setOnClickListener{
            saveIntroduceIs(true)
            finish()
        }
    }
}