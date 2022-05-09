package com.eight.collection.ui.login

import android.view.View
import com.eight.collection.databinding.ActivityLoginFirstBinding
import com.eight.collection.databinding.ActivityPwFindFirstBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.signup.SignupFirstActivity

class PwFindFirstActivity: BaseActivity<ActivityPwFindFirstBinding>(ActivityPwFindFirstBinding::inflate), View.OnClickListener {


    override fun initAfterBinding() {
//        binding.loginLoginBtnOffIv.setOnClickListener(this)
//        binding.loginSignupBtnIv.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
//        if(v == null) return
//
//        when(v) {
//            binding.loginLoginBtnOffIv -> {
//                startNextActivity(LoginSecondActivity::class.java)
//                slideRight()
//            }
//            binding.loginSignupBtnIv -> {
//                startNextActivity(SignupFirstActivity::class.java)
//                slideRight()
//            }
//        }
    }
}