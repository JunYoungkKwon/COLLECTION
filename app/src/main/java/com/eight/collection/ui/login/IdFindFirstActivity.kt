package com.eight.collection.ui.login

import android.graphics.Color
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintSet
import com.eight.collection.R
import com.eight.collection.data.entities.User
import com.eight.collection.data.remote.auth.Auth
import com.eight.collection.data.remote.auth.AuthService
import com.eight.collection.databinding.ActivityIdFindFirstBinding
import com.eight.collection.databinding.ActivityLoginFirstBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.main.MainActivity
import com.eight.collection.ui.main.setting.infoedit.account.AccountDeleteSecondActivity
import com.eight.collection.ui.signup.SignupFirstActivity
import com.eight.collection.utils.saveJwt
import com.eight.collection.utils.saveName
import com.eight.collection.utils.saveNickName
import com.eight.collection.utils.saveUserId

class IdFindFirstActivity: BaseActivity<ActivityIdFindFirstBinding>(ActivityIdFindFirstBinding::inflate), View.OnClickListener, IdFindView {


    override fun initAfterBinding() {
        binding.idFindFirstBackBtnIv.setOnClickListener(this)
        binding.idFindFirstPhoneEt.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        binding.idFindFirstEditIb.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
            binding.idFindFirstBackBtnIv -> {
                finishActivity()
            }

            binding.idFindFirstEditIb -> {
                idFind()
            }
        }
    }

    private fun idFind() {
        val nickname = binding.idFindFirstNameEt.text.toString()
        val phonenumber = binding.idFindFirstPhoneEt.text.toString().replace("[^0-9]".toRegex(), "")

        AuthService.findId(this, nickname, phonenumber)
    }


    override fun onIdFindLoading() {
        binding.loginLoadingInIv.visibility = View.VISIBLE
        binding.loginLoadingCircleIv.visibility = View.VISIBLE
        binding.loginLoadingBackgroundIv.visibility = View.VISIBLE
        //로딩이미지 애니메이션
        val animation = AnimationUtils.loadAnimation(this, R.anim.rotate)
        binding.loginLoadingCircleIv.startAnimation(animation)
        binding.loginDimBackground.visibility = View.VISIBLE
    }

    override fun onIdFindSuccess(auth: Auth) {
        binding.loginLoadingCircleIv.visibility = View.GONE
        binding.loginLoadingInIv.visibility = View.GONE
        binding.loginLoadingBackgroundIv.visibility = View.GONE
        binding.loginLoadingCircleIv.clearAnimation()
        binding.loginDimBackground.visibility = View.INVISIBLE

        saveUserId(auth.userId)

        startNextActivity(IdFindSecondActivity::class.java)
        slideRight()
    }

    override fun onIdFindFailure(code: Int, message: String) {
        binding.loginLoadingCircleIv.visibility = View.GONE
        binding.loginLoadingInIv.visibility = View.GONE
        binding.loginLoadingBackgroundIv.visibility = View.GONE
        binding.loginLoadingCircleIv.clearAnimation()
        binding.loginDimBackground.visibility = View.INVISIBLE

        when(code) {
            //ID 에러
            3041, 3072, 4002, 3050, 3051, 4022 -> {
                //ID 보이게
                binding.idFindFirstNameEtHighlightView.setBackgroundColor(Color.parseColor("#c77a4a"))
                binding.idFindFirstNameFailTv.visibility = View.VISIBLE
                binding.idFindFirstNameFailTv.text= message
                //PW 안보이게
                binding.idFindFirstPhoneHighlightView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.idFindFirstPhoneFailTv.visibility = View.GONE
            }
            //Phone 에러
            3008, 3009, 3010 -> {
                //PW 보이게
                binding.idFindFirstPhoneHighlightView.setBackgroundColor(Color.parseColor("#c77a4a"))
                binding.idFindFirstPhoneFailTv.visibility = View.VISIBLE
                binding.idFindFirstPhoneFailTv.text= message
                //ID 부분 안보이게
                binding.idFindFirstNameEtHighlightView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.idFindFirstNameFailTv.visibility = View.GONE
            }

            else -> {
                Log.d("IdFindFirst/Error", "error")
            }
        }
    }
}