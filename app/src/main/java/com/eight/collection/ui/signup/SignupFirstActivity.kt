package com.eight.collection.ui.signup

import android.os.Bundle
import android.view.View
import com.eight.collection.R
import com.eight.collection.databinding.ActivitySignupFirstBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.login.LoginFirstActivity
import com.eight.collection.ui.login.LoginSecondActivity

class SignupFirstActivity: BaseActivity<ActivitySignupFirstBinding>(ActivitySignupFirstBinding::inflate), View.OnClickListener {
    override fun initAfterBinding() {
        binding.signUpFirstIcBack.setOnClickListener(this)
        binding.signUpFirstNextButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
            binding.signUpFirstIcBack -> finishActivity()
            binding.signUpFirstNextButton -> {
                startNextActivity(SignupSecondActivity::class.java)
                slideRight()}
        }
    }

}