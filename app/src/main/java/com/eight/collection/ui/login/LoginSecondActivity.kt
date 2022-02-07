package com.eight.collection.ui.login

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.eight.collection.R
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
    }

    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
            binding.loginLoginBtnOffIv -> login()
            binding.loginBackBtnIv -> finish()
        }
    }

    private fun login() {
        val id = binding.loginIdEt.text.toString()
        val password = binding.loginPwEt.text.toString()
        val user = User(id, password, "", "", "")

        AuthService.login(this, user)
    }

    override fun onLoginLoading() {
        binding.loginLoadingInIv.visibility = View.VISIBLE
        binding.loginLoadingCircleIv.visibility = View.VISIBLE
        binding.loginLoadingBackgroundIv.visibility = View.VISIBLE
        //로딩이미지 애니메이션
        val animation = AnimationUtils.loadAnimation(this, R.anim.rotate)
        binding.loginLoadingCircleIv.startAnimation(animation)
        binding.loginDimBackground.visibility = View.VISIBLE

    }

    override fun onLoginSuccess(auth: Auth) {
        binding.loginLoadingCircleIv.visibility = View.GONE
        binding.loginLoadingInIv.visibility = View.GONE
        binding.loginLoadingBackgroundIv.visibility = View.GONE
        binding.loginLoadingCircleIv.clearAnimation()
        binding.loginDimBackground.visibility = View.INVISIBLE

        startActivityWithClear(MainActivity::class.java)
    }

    override fun onLoginFailure(code: Int, message: String) {
        binding.loginLoadingCircleIv.visibility = View.GONE
        binding.loginLoadingInIv.visibility = View.GONE
        binding.loginLoadingBackgroundIv.visibility = View.GONE
        binding.loginLoadingCircleIv.clearAnimation()
        binding.loginDimBackground.visibility = View.INVISIBLE
        when(code) {
            //ID 에러
            3000, 3011, 4002 -> {
                //ID 보이게
                binding.loginIdHighlightView.setBackgroundColor(Color.parseColor("#c77a4a"))
                binding.loginIdFailIv.visibility = View.VISIBLE
                binding.loginIdFailTv.visibility = View.VISIBLE
                binding.loginIdFailTv.text= message
                //PW 안보이게
                binding.loginPwHighlightView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.loginPwFailIv.visibility = View.INVISIBLE
                binding.loginPwFailTv.visibility = View.INVISIBLE
            }
            //PW 에러
            3003, 3012 -> {
                //PW 보이게
                binding.loginPwHighlightView.setBackgroundColor(Color.parseColor("#c77a4a"))
                binding.loginPwFailIv.visibility = View.VISIBLE
                binding.loginPwFailTv.visibility = View.VISIBLE
                binding.loginPwFailTv.text= message
                //ID 부분 안보이게
                binding.loginIdHighlightView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.loginIdFailIv.visibility = View.INVISIBLE
                binding.loginIdFailTv.visibility = View.INVISIBLE
            }

            else -> {
                showToast("SEVER ERROR ")
            }
        }
    }
}