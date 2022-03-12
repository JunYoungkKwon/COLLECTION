package com.eight.collection.ui.login

import android.view.View
import com.eight.collection.databinding.ActivityLoginFirstBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.introduce.IntroduceFirstDialog
import com.eight.collection.ui.signup.SignupFirstActivity
import com.eight.collection.utils.getIntroduceIs
import com.eight.collection.utils.saveJwt

class LoginFirstActivity: BaseActivity<ActivityLoginFirstBinding>(ActivityLoginFirstBinding::inflate), View.OnClickListener {
    lateinit var introduceDialog: IntroduceFirstDialog
    var getIntroduceIs : Boolean = getIntroduceIs()

    override fun initAfterBinding() {
        binding.loginLoginBtnOffIv.setOnClickListener(this)
        binding.loginSignupBtnIv.setOnClickListener(this)
        introduceDialog = IntroduceFirstDialog(this)

        if(getIntroduceIs == false){
            introduceDialog.show()
        }
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