package com.eight.collection.ui.signup

import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.eight.collection.R
import com.eight.collection.data.remote.auth.AuthService
import com.eight.collection.databinding.ActivityAddToptagBinding.inflate
import com.eight.collection.databinding.ActivitySignupSecondBinding
import com.eight.collection.databinding.ActivityWritesecondBinding.inflate
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
            var layoutInflater = LayoutInflater.from(this).inflate(R.layout.toast_custom,null)
            var text : TextView = layoutInflater.findViewById(R.id.toast_text_tv)
            text.text = "닉네임을 입력해주세요."
            var toast = Toast(this)
            toast.view = layoutInflater
            toast.setGravity(Gravity.BOTTOM, 0, 270)
            toast.show()
            return

        }
        val nickname : String = binding.signUpSecondNicknameEt.text.toString()
        AuthService.checkNickname(this, nickname)
    }

    override fun onCheckNicknameLoading() {
    }

    override fun onCheckNicknameSuccess() {
        val intent = Intent(this, SignupThirdActivity::class.java)
        intent.putExtra("postnickname",binding.signUpSecondNicknameEt.text.toString())
        startActivity(intent)
    }

    override fun onCheckNicknameFailure(code: Int, message: String) {
        when(code) {
            3005, 3006, 3007, 3050, 3051 -> {
                var layoutInflater = LayoutInflater.from(this).inflate(R.layout.toast_custom,null)
                var text : TextView = layoutInflater.findViewById(R.id.toast_text_tv)
                text.text = message
                var toast = Toast(this)
                toast.view = layoutInflater
                toast.setGravity(Gravity.BOTTOM, 0, 410)
                toast.show()
            }
            else -> {
                var layoutInflater = LayoutInflater.from(this).inflate(R.layout.toast_custom,null)
                var text : TextView = layoutInflater.findViewById(R.id.toast_text_tv)
                text.text = "SERVER ERROR"
                var toast = Toast(this)
                toast.view = layoutInflater
                toast.setGravity(Gravity.BOTTOM, 0, 410)
                toast.show()
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