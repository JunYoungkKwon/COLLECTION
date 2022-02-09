package com.eight.collection.ui.login

import android.content.Intent
import android.view.View
import android.widget.Toast
import com.eight.collection.data.entities.User
import com.eight.collection.data.remote.auth.Auth
import com.eight.collection.data.remote.auth.AuthService
import com.eight.collection.databinding.ActivityLoginFirstBinding
import com.eight.collection.databinding.ActivityLoginSecondBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.main.MainActivity
import com.eight.collection.ui.signup.SignupFirstActivity
import com.eight.collection.utils.saveJwt

class LoginFirstActivity: BaseActivity<ActivityLoginFirstBinding>(ActivityLoginFirstBinding::inflate), View.OnClickListener {


    override fun initAfterBinding() {
        binding.loginLoginBtnOffIv.setOnClickListener(this)
        binding.loginSignupBtnIv.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
            binding.loginLoginBtnOffIv -> {
                startNextActivity(LoginSecondActivity::class.java)
                slideRight()
            }
            binding.loginSignupBtnIv -> {
                startNextActivity(SignupFirstActivity::class.java)
                slideRight()
            }
        }
    }
}