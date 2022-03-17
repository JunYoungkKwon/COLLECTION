package com.eight.collection.ui.main.match.weather

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.eight.collection.R
import com.eight.collection.databinding.ActivityMainBinding
import com.eight.collection.databinding.ActivityMatchPlaceBinding
import com.eight.collection.databinding.ActivityMatchWeatherBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.introduce.IntroduceFirstDialog
import com.eight.collection.ui.main.mylook.MyLookActivity
import com.eight.collection.utils.getIntroduceIs

class WeatherActivity: BaseActivity<ActivityMatchWeatherBinding>(ActivityMatchWeatherBinding::inflate) {

    override fun initAfterBinding() {
        // EditText (검색창) 눌렀을시 이벤트
        binding.matchWeatherSearchViewTv.setOnClickListener{
            binding.matchWeatherDefaultTv.visibility = View.GONE
            binding.matchWeatherVeryColdBt.visibility = View.GONE
            binding.matchWeatherVeryHotBt.visibility = View.GONE
            binding.matchWeatherColdBt.visibility = View.GONE
            binding.matchWeatherHotBt.visibility = View.GONE
            binding.matchWeatherSosoBt.visibility = View.GONE
            binding.matchWeatherSnowBt.visibility = View.GONE
            binding.matchWeatherRainBt.visibility = View.GONE
            binding.matchWeatherHailBt.visibility = View.GONE
            binding.matchWeatherClothIv.visibility = View.GONE
            binding.matchWeatherSearchViewTv.visibility = View.GONE
            binding.matchWeatherSearchIc.visibility = View.GONE
            binding.matchWeatherSearchBackIc.visibility = View.VISIBLE
            binding.matchWeatherSearchDeleteIc.visibility = View.VISIBLE
            binding.matchWeatherSearchButtonRecyclerview.visibility = View.VISIBLE
            binding.matchWeatherSearchBt.visibility = View.VISIBLE
            binding.matchWeatherSearchEt.visibility = View.VISIBLE
        }

        binding.matchWeatherSearchBackIc.setOnClickListener{
            binding.matchWeatherDefaultTv.visibility = View.VISIBLE
            binding.matchWeatherVeryColdBt.visibility = View.VISIBLE
            binding.matchWeatherVeryHotBt.visibility = View.VISIBLE
            binding.matchWeatherColdBt.visibility = View.VISIBLE
            binding.matchWeatherHotBt.visibility = View.VISIBLE
            binding.matchWeatherSosoBt.visibility = View.VISIBLE
            binding.matchWeatherSnowBt.visibility = View.VISIBLE
            binding.matchWeatherRainBt.visibility = View.VISIBLE
            binding.matchWeatherHailBt.visibility = View.VISIBLE
            binding.matchWeatherClothIv.visibility = View.VISIBLE
            binding.matchWeatherSearchViewTv.visibility = View.VISIBLE
            binding.matchWeatherSearchIc.visibility = View.VISIBLE
            binding.matchWeatherSearchBackIc.visibility = View.GONE
            binding.matchWeatherSearchDeleteIc.visibility = View.GONE
            binding.matchWeatherSearchButtonRecyclerview.visibility = View.GONE
            binding.matchWeatherSearchBt.visibility = View.GONE
            binding.matchWeatherSearchEt.setText("")
            binding.matchWeatherSearchEt.visibility = View.GONE
        }
    }
}