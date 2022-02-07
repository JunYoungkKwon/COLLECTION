package com.eight.collection.ui.signup

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import com.eight.collection.data.remote.auth.AuthService
import com.eight.collection.databinding.ActivitySignupSecondBinding
import com.eight.collection.ui.BaseActivity

class SignupSecondActivity: BaseActivity<ActivitySignupSecondBinding>(ActivitySignupSecondBinding::inflate),CheckNicknameView, View.OnClickListener {

    override fun initAfterBinding() {
        binding.signUpSecondIcBack.setOnClickListener(this)
        binding.signUpSecondNextButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
            /*binding.signUpSecondNextButton -> checkNickname()*/
                binding.signUpSecondNextButton -> checkNickname()
                binding.signUpSecondIcBack -> finish()
        }
    }

    private fun checkNickname() {
        if (binding.signUpSecondNicknameEt.text.toString().isEmpty()) {
            Toast.makeText(this, "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        val nickname : String = binding.signUpSecondNicknameEt.text.toString()
        AuthService.checkNickname(this, nickname)
    }

    override fun onCheckNicknameLoading() {
        showToast("LOADING")
    }

    override fun onCheckNicknameSuccess() {
        Log.d("Log","SignupSecondActivity")
        val intent = Intent(this, SignupThirdActivity::class.java)
        intent.putExtra("postnickname",binding.signUpSecondNicknameEt.text.toString())
        startActivity(intent)
    }

    override fun onCheckNicknameFailure(code: Int, message: String) {
        when(code) {
            3005, 3006, 3007, 3050, 3051 -> {
                showToast(message)
            }
            else -> {
                showToast("SERVER ERROR")
            }
        }
    }

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