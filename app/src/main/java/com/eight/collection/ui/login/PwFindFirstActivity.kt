package com.eight.collection.ui.login

import android.graphics.Color
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import com.eight.collection.R
import com.eight.collection.data.remote.auth.Auth
import com.eight.collection.data.remote.auth.AuthService
import com.eight.collection.databinding.ActivityLoginFirstBinding
import com.eight.collection.databinding.ActivityPwFindFirstBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.signup.SignupFirstActivity
import com.eight.collection.utils.saveUserId

class PwFindFirstActivity: BaseActivity<ActivityPwFindFirstBinding>(ActivityPwFindFirstBinding::inflate), View.OnClickListener , PwFindView {


    override fun initAfterBinding() {
        binding.pwFindFirstBackBtnIv.setOnClickListener(this)
        binding.pwFindFirstPhoneEt.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        binding.pwFindSecondEditIb.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
            binding.pwFindFirstBackBtnIv -> {
                finishActivity()
            }
            binding.pwFindSecondEditIb -> {
                findPw()
            }
        }
    }
    private fun findPw() {
        val name = binding.pwFindFirstNameEt.text.toString()
        val id = binding.pwFindFirstIdEt.text.toString()
        val phoneNum = binding.pwFindFirstPhoneEt.text.toString().replace("[^0-9]".toRegex(), "")


        AuthService.findPw(this, name,phoneNum,id)
    }

    override fun onPwFindLoading() {
        binding.loginLoadingInIv.visibility = View.VISIBLE
        binding.loginLoadingCircleIv.visibility = View.VISIBLE
        binding.loginLoadingBackgroundIv.visibility = View.VISIBLE
        //로딩이미지 애니메이션
        val animation = AnimationUtils.loadAnimation(this, R.anim.rotate)
        binding.loginLoadingCircleIv.startAnimation(animation)
        binding.loginDimBackground.visibility = View.VISIBLE
    }

    override fun onPwFindSuccess() {
        binding.loginLoadingCircleIv.visibility = View.GONE
        binding.loginLoadingInIv.visibility = View.GONE
        binding.loginLoadingBackgroundIv.visibility = View.GONE
        binding.loginLoadingCircleIv.clearAnimation()
        binding.loginDimBackground.visibility = View.INVISIBLE

        startNextActivity(PwFindSecondActivity::class.java)
        slideRight()
    }

    override fun onPwFindFailure(code: Int, message: String) {
        binding.loginLoadingCircleIv.visibility = View.GONE
        binding.loginLoadingInIv.visibility = View.GONE
        binding.loginLoadingBackgroundIv.visibility = View.GONE
        binding.loginLoadingCircleIv.clearAnimation()
        binding.loginDimBackground.visibility = View.INVISIBLE

        when(code) {
            //ID 에러
            3000, 3001, 3002 -> {
                //ID 보이게
                binding.pwFindFirstIdUnderscoreView.setBackgroundColor(Color.parseColor("#c77a4a"))
                binding.pwFindFirstIdErrorTv.visibility = View.VISIBLE
                binding.pwFindFirstIdErrorTv.text= message
                //PW 안보이게
                binding.pwFindFirstNameUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.pwFindFirstNameErrorTv.visibility = View.GONE
                binding.pwFindFirstPhoneUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.pwFindFirstPhoneErrorTv.visibility = View.GONE
            }
            //Name 에러
            3041, 3072, 4002, 3050, 3051, 4022 -> {
                binding.pwFindFirstNameUnderscoreView.setBackgroundColor(Color.parseColor("#c77a4a"))
                binding.pwFindFirstNameErrorTv.visibility = View.VISIBLE
                binding.pwFindFirstNameErrorTv.text= message

                binding.pwFindFirstIdUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.pwFindFirstIdErrorTv.visibility = View.GONE
                binding.pwFindFirstPhoneUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.pwFindFirstPhoneErrorTv.visibility = View.GONE
            }
            //Phone 에러
            3008, 3009, 3010 -> {
                binding.pwFindFirstPhoneUnderscoreView.setBackgroundColor(Color.parseColor("#c77a4a"))
                binding.pwFindFirstPhoneErrorTv.visibility = View.VISIBLE
                binding.pwFindFirstPhoneErrorTv.text= message

                binding.pwFindFirstIdUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.pwFindFirstIdErrorTv.visibility = View.GONE
                binding.pwFindFirstNameUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.pwFindFirstNameErrorTv.visibility = View.GONE

            }

            else -> {
                Log.d("PwFindFirst/Error", "error")
            }
        }
    }
}