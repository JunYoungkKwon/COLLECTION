package com.eight.collection.ui.login

import android.view.View
import com.eight.collection.databinding.ActivityIdFindFirstBinding
import com.eight.collection.databinding.ActivityIdFindSecondBinding
import com.eight.collection.databinding.ActivityLoginFirstBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.signup.SignupFirstActivity
import com.eight.collection.utils.getUserId

class IdFindSecondActivity: BaseActivity<ActivityIdFindSecondBinding>(ActivityIdFindSecondBinding::inflate), View.OnClickListener {


    override fun initAfterBinding() {
//        binding.loginLoginBtnOffIv.setOnClickListener(this)
//        binding.loginSignupBtnIv.setOnClickListener(this)
        binding.idFindSecondPwFindTv.setOnClickListener(this)
        binding.idFindFirstNameTv.text = getUserId()

    }

    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
//            binding.loginLoginBtnOffIv -> {
//                startNextActivity(LoginSecondActivity::class.java)
//                slideRight()
//            }
//            binding.loginSignupBtnIv -> {
//                startNextActivity(SignupFirstActivity::class.java)
//                slideRight()
//            }

            binding.idFindSecondPwFindTv -> {
                startNextActivity(PwFindFirstActivity::class.java)
                slideRight()
            }
        }
    }
}