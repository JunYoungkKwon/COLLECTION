package com.eight.collection.ui.signup

import android.view.View
import android.widget.Toast
import com.eight.collection.data.entities.User
import com.eight.collection.data.remote.auth.AuthService
import com.eight.collection.databinding.ActivitySignupFirstBinding
import com.eight.collection.ui.BaseActivity

class SignupSecondActivity: BaseActivity<ActivitySignupFirstBinding>(ActivitySignupFirstBinding::inflate), View.OnClickListener {

    override fun initAfterBinding() {
        binding.signUpFirstIcBack.setOnClickListener(this)
        binding.signUpFirstNextButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
            binding.signUpFirstIcBack -> finish()
            /*binding.signUpSignUpBtn -> signUp()*/
        }
    }

    /* private fun getUser(): User {
         val email: String =
             binding.signUpIdEt.text.toString() + "@" + binding.signUpDirectInputEt.text.toString()
         val pwd: String = binding.signUpPasswordEt.text.toString()
         val name: String = binding.signUpNameEt.text.toString()

         return User(email, pwd, name)
     }*/

    /*private fun signUp() {
        if (binding.signUpIdEt.text.toString()
                .isEmpty() || binding.signUpDirectInputEt.text.toString().isEmpty()
        ) {
            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.signUpNameEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이름 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.signUpPasswordEt.text.toString() != binding.signUpPasswordCheckEt.text.toString()) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        AuthService.signUp(this, getUser())
    }*/

    /*override fun onSignUpLoading() {
        binding.signUpLoadingPb.visibility = View.VISIBLE
    }

    override fun onSignUpSuccess() {
        binding.signUpLoadingPb.visibility = View.GONE

        finish()
    }

    override fun onSignUpFailure(code: Int, message: String) {
        binding.signUpLoadingPb.visibility = View.GONE

        when(code) {
            2016, 2017 -> {
                binding.signUpEmailErrorTv.visibility = View.VISIBLE
                binding.signUpEmailErrorTv.text = message
            }
        }
    }*/
    /*SignUpView*/
}