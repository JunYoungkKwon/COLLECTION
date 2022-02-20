package com.eight.collection.ui.main.setting.infoedit.phonenumber

import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.eight.collection.R
import com.eight.collection.data.entities.User
import com.eight.collection.data.remote.auth.Auth
import com.eight.collection.data.remote.auth.AuthService
import com.eight.collection.databinding.ActivityNicknameEditBinding
import com.eight.collection.databinding.ActivityPhoneNumberEditBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.utils.saveChangeNickName

class PhoneNumberEditActivity: BaseActivity<ActivityPhoneNumberEditBinding>(ActivityPhoneNumberEditBinding::inflate), ChangePhoneNumberView, View.OnClickListener{

    override fun initAfterBinding() {
        binding.phoneNumberEditIb.setOnClickListener(this)
        binding.phoneNumberBackBtnIv.setOnClickListener(this)
        binding.phoneNumberTextEt.addTextChangedListener(PhoneNumberFormattingTextWatcher())

    }

    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
            binding.phoneNumberEditIb -> changePhoneNumber()
            binding.phoneNumberBackBtnIv -> finishActivity()
        }
    }

    private fun changePhoneNumber() {
        if (binding.phoneNumberTextEt.text.toString().isEmpty()) {
            Toast(this).showCustomToast("새로운 휴대폰번호를 입력해주세요.", this)
            return
        }
        val phonenumber = binding.phoneNumberTextEt.text.toString().replace("[^0-9]".toRegex(), "")
        val user = User("", "", "","",phonenumber,"","","")

        AuthService.changePhoneNumber(this, user)
    }

    override fun onChangePhoneNumberLoading() {
        binding.loginLoadingInIv.visibility = View.VISIBLE
        binding.loginLoadingCircleIv.visibility = View.VISIBLE
        binding.loginLoadingBackgroundIv.visibility = View.VISIBLE
        //로딩이미지 애니메이션
        val animation = AnimationUtils.loadAnimation(this, R.anim.rotate)
        binding.loginLoadingCircleIv.startAnimation(animation)
        binding.loginDimBackground.visibility = View.VISIBLE
    }

    override fun onChangePhoneNumberSuccess(auth: Auth) {
        binding.loginLoadingCircleIv.visibility = View.GONE
        binding.loginLoadingInIv.visibility = View.GONE
        binding.loginLoadingBackgroundIv.visibility = View.GONE
        binding.loginLoadingCircleIv.clearAnimation()
        binding.loginDimBackground.visibility = View.INVISIBLE

        Toast(this).showCustomToast("핸드폰번호가 변경되었습니다.", this)
    }

    override fun onChangePhoneNumberFailure(code: Int, message: String) {
        binding.loginLoadingCircleIv.visibility = View.GONE
        binding.loginLoadingInIv.visibility = View.GONE
        binding.loginLoadingBackgroundIv.visibility = View.GONE
        binding.loginLoadingCircleIv.clearAnimation()
        binding.loginDimBackground.visibility = View.INVISIBLE
        when (code) {
            //NickName 에러
            3008, 3009, 3010 -> {
                //오류 토스트 보이게
                Toast(this).showCustomToast(message, this)
            }

            else -> {
                Log.d("PhonwNumnerEdit/ERROR", "error")

            }
        }
    }
}