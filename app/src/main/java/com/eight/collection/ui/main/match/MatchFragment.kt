package com.eight.collection.ui.main.match

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
import com.eight.collection.ui.main.MainActivity

class MatchFragment(): BaseFragment<FragmentMatchBinding>(FragmentMatchBinding::inflate) {

    override fun initAfterBinding() {
        binding.matchColorTextIv.setOnClickListener{
            var layoutInflater = LayoutInflater.from(context).inflate(R.layout.toast_custom,null)
            var text : TextView = layoutInflater.findViewById(R.id.toast_text_tv)
            text.text = "매칭 기능은 현재 지원하지 않습니다."
            var toast = Toast(context)
            toast.view = layoutInflater
            toast.setGravity(Gravity.BOTTOM, 0, 150)
            toast.show()
        }

        binding.matchWeatherTextIv.setOnClickListener{
            var layoutInflater = LayoutInflater.from(context).inflate(R.layout.toast_custom,null)
            var text : TextView = layoutInflater.findViewById(R.id.toast_text_tv)
            text.text = "매칭 기능은 현재 지원하지 않습니다."
            var toast = Toast(context)
            toast.view = layoutInflater
            toast.setGravity(Gravity.BOTTOM, 0, 150)
            toast.show()
        }

        binding.matchPlaceTextIv.setOnClickListener{
            var layoutInflater = LayoutInflater.from(context).inflate(R.layout.toast_custom,null)
            var text : TextView = layoutInflater.findViewById(R.id.toast_text_tv)
            text.text = "매칭 기능은 현재 지원하지 않습니다."
            var toast = Toast(context)
            toast.view = layoutInflater
            toast.setGravity(Gravity.BOTTOM, 0, 150)
            toast.show()
        }

        binding.matchWhoTextIv.setOnClickListener{
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