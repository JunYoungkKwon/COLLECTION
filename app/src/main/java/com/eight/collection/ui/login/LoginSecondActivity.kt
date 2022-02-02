package com.eight.collection.ui.login

import android.view.View
import android.widget.Toast
import com.eight.collection.data.entities.User
import com.eight.collection.data.remote.auth.Auth
import com.eight.collection.data.remote.auth.AuthService
import com.eight.collection.databinding.ActivityLoginSecondBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.main.MainActivity
import com.eight.collection.ui.signup.SignupFirstActivity
import com.eight.collection.utils.saveJwt

class LoginSecondActivity: BaseActivity<ActivityLoginSecondBinding>(ActivityLoginSecondBinding::inflate), LoginView, View.OnClickListener {

    override fun initAfterBinding() {
        binding.loginLoginBtnOffIv.setOnClickListener(this)
        binding.loginBackBtnIv.setOnClickListener(this)
        //login()
    }

    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
            binding.loginLoginBtnOffIv -> startNextActivity(SignupFirstActivity::class.java)
            binding.loginBackBtnIv -> finish()
        }
    }

    private fun login() {
        if (binding.loginIdEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.loginPwEt.text.toString().isEmpty()) {
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

//        val email = binding.loginIdEt.text.toString() + "@" + binding.loginDirectInputEt.text.toString()
//        val password = binding.loginPasswordEt.text.toString()
//        val user = User(email, password, "")

        //AuthService.login(this, user)
    }

//    override fun onLoginLoading() {
//        binding.loginLoadingPb.visibility = View.VISIBLE
//    }
//
//    override fun onLoginSuccess(auth: Auth) {
//        binding.loginLoadingPb.visibility = View.GONE
//
//        saveJwt(auth.jwt)
//        startActivityWithClear(MainActivity::class.java)
//    }
//
//    override fun onLoginFailure(code: Int, message: String) {
//        binding.loginLoadingPb.visibility = View.GONE
//
//        when(code) {
//            2015, 2019, 3014 -> {
//                binding.loginErrorTv.visibility = View.VISIBLE
//                binding.loginErrorTv.text= message
//            }
//        }
//    }
}