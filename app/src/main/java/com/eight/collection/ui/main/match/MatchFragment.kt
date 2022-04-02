package com.eight.collection.ui.main.match

import android.content.Intent
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.eight.collection.R
import com.eight.collection.databinding.ActivityWritefirstBinding
import com.eight.collection.databinding.FragmentMonthBinding
import com.eight.collection.databinding.FragmentMatchBinding
import com.eight.collection.ui.BaseFragment
import com.eight.collection.ui.login.LoginSecondActivity
import com.eight.collection.ui.main.MainActivity
import com.eight.collection.ui.main.match.color.ColorActivity
import com.eight.collection.ui.main.match.place.PlaceActivity
import com.eight.collection.ui.main.match.weather.WeatherActivity
import com.eight.collection.ui.main.match.who.WhoActivity
import com.eight.collection.ui.main.mylook.MyLookActivity
import com.eight.collection.ui.signup.SignupFirstActivity

class MatchFragment(): BaseFragment<FragmentMatchBinding>(FragmentMatchBinding::inflate), View.OnClickListener {

    override fun initAfterBinding() {
        binding.matchColorTextIv.setOnClickListener(this)
        binding.matchWeatherTextIv.setOnClickListener(this)
        binding.matchPlaceTextIv.setOnClickListener(this)
        binding.matchWhoTextIv.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
            binding.matchWhoTextIv -> {
//                startActivity(Intent(activity, WhoActivity::class.java))
                var layoutInflater = LayoutInflater.from(context).inflate(R.layout.toast_custom,null)
                var text : TextView = layoutInflater.findViewById(R.id.toast_text_tv)
                text.text = "매칭 기능은 현재 지원하지 않습니다."
                var toast = Toast(context)
                toast.view = layoutInflater
                toast.setGravity(Gravity.BOTTOM, 0, 150)
                toast.show()
            }
            binding.matchPlaceTextIv -> {
                /*startActivity(Intent(activity, PlaceActivity::class.java))*/
                var layoutInflater = LayoutInflater.from(context).inflate(R.layout.toast_custom,null)
                var text : TextView = layoutInflater.findViewById(R.id.toast_text_tv)
                text.text = "매칭 기능은 현재 지원하지 않습니다."
                var toast = Toast(context)
                toast.view = layoutInflater
                toast.setGravity(Gravity.BOTTOM, 0, 150)
                toast.show()
            }
            binding.matchWeatherTextIv -> {
                /*startActivity(Intent(activity, WeatherActivity::class.java))*/
                var layoutInflater = LayoutInflater.from(context).inflate(R.layout.toast_custom,null)
                var text : TextView = layoutInflater.findViewById(R.id.toast_text_tv)
                text.text = "매칭 기능은 현재 지원하지 않습니다."
                var toast = Toast(context)
                toast.view = layoutInflater
                toast.setGravity(Gravity.BOTTOM, 0, 150)
                toast.show()
            }
            binding.matchColorTextIv -> {
                /*startActivity(Intent(activity, ColorActivity::class.java))*/
                var layoutInflater = LayoutInflater.from(context).inflate(R.layout.toast_custom,null)
                var text : TextView = layoutInflater.findViewById(R.id.toast_text_tv)
                text.text = "매칭 기능은 현재 지원하지 않습니다."
                var toast = Toast(context)
                toast.view = layoutInflater
                toast.setGravity(Gravity.BOTTOM, 0, 150)
                toast.show()
            }
        }
    }
}