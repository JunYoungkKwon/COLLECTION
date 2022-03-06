package com.eight.collection.ui.signup

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.eight.collection.R
import com.eight.collection.databinding.ActivitySignupFirstBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.login.LoginFirstActivity
import com.eight.collection.ui.login.LoginSecondActivity
import com.eight.collection.ui.main.setting.privacy.PrivacyRule

class SignupFirstActivity: BaseActivity<ActivitySignupFirstBinding>(ActivitySignupFirstBinding::inflate), View.OnClickListener {
    override fun initAfterBinding() {
        binding.signUpFirstIcBack.setOnClickListener(this)
        binding.signUpFirstNextButton.setOnClickListener(this)
        binding.signUpFirstAgreeFirstIv.setOnClickListener(this)
        binding.signUpFirstAgreeFirstTv.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v == null) return
        when (v) {
            binding.signUpFirstIcBack -> startNextActivity(LoginFirstActivity::class.java)
            binding.signUpFirstAgreeFirstIv, binding.signUpFirstAgreeFirstTv -> {
                startNextActivity(PrivacyRule::class.java)
                slideRight()
            }
        }
        if (binding.signUpFirstCheckFirstIv.isChecked) {
            when (v) {
                binding.signUpFirstNextButton -> startNextActivity(SignupSecondActivity::class.java)
            }
        }
        else {
            when (v) {
                binding.signUpFirstNextButton -> {
                    var layoutInflater = LayoutInflater.from(this).inflate(R.layout.toast_custom,null)
                    var text : TextView = layoutInflater.findViewById(R.id.toast_text_tv)
                    text.text = "개인정보 수집제공 동의를 해주세요."
                    var toast = Toast(this)
                    toast.view = layoutInflater
                    toast.setGravity(Gravity.BOTTOM, 0, 370)
                    toast.show()
                }
            }
        }
    }
}