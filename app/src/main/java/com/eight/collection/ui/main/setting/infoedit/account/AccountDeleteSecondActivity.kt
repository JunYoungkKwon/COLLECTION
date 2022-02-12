package com.eight.collection.ui.main.setting.infoedit.account

import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.eight.collection.R
import com.eight.collection.data.entities.User
import com.eight.collection.data.remote.auth.Auth
import com.eight.collection.data.remote.auth.AuthService
import com.eight.collection.databinding.ActivityAccountDeleteBinding
import com.eight.collection.databinding.ActivityAccountDeleteSecondBinding
import com.eight.collection.databinding.ActivityNicknameEditBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.login.LoginFirstActivity
import com.eight.collection.utils.saveChangeNickName

class AccountDeleteSecondActivity: BaseActivity<ActivityAccountDeleteSecondBinding>(ActivityAccountDeleteSecondBinding::inflate),DeleteAccountView, View.OnClickListener{




    override fun initAfterBinding() {
        binding.accountSecondBtnIb.setOnClickListener(this)
        binding.accountSecondBackBtnIv.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
            binding.accountSecondBtnIb -> deleteAccount()
            binding.accountSecondBackBtnIv -> finishActivity()
        }
    }
    private fun deleteAccount() {
        if(binding.accountSecondPwEt.text.toString().isNotEmpty()){
            if(binding.accountSecondPwCheckEt.text.toString().isEmpty())
            {
                binding.accountSecondPwCheckHighlightView.setBackgroundColor(Color.parseColor("#c77a4a"))
                binding.accountSecondPwCheckFailIv.visibility = View.VISIBLE
                binding.accountSecondPwCheckFailTv.visibility = View.VISIBLE
                binding.accountSecondPwCheckFailTv.text= "비밀번호 확인을 입력해주세요."

                binding.accountSecondPwHighlightView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.accountSecondPwFailIv.visibility = View.INVISIBLE
                binding.accountSecondPwFailTv.visibility = View.INVISIBLE

            }

        }
        if(!binding.accountSecondPwEt.text.toString().equals(binding.accountSecondPwCheckEt.text.toString())){

            binding.accountSecondPwCheckHighlightView.setBackgroundColor(Color.parseColor("#c77a4a"))
            binding.accountSecondPwCheckFailIv.visibility = View.VISIBLE
            binding.accountSecondPwCheckFailTv.visibility = View.VISIBLE
            binding.accountSecondPwCheckFailTv.text= "비밀번호와 일치하지 않습니다"

            binding.accountSecondPwHighlightView.setBackgroundColor(Color.parseColor("#c3b5ac"))
            binding.accountSecondPwFailIv.visibility = View.INVISIBLE
            binding.accountSecondPwFailTv.visibility = View.INVISIBLE
        }
        val password = binding.accountSecondPwEt.text.toString()
        val user = User("", password, "", "", "","","","")

        AuthService.deleteAccount(this, user)
    }

    override fun onDeleteAccountLoading() {
        binding.loginLoadingInIv.visibility = View.VISIBLE
        binding.loginLoadingCircleIv.visibility = View.VISIBLE
        binding.loginLoadingBackgroundIv.visibility = View.VISIBLE
        //로딩이미지 애니메이션
        val animation = AnimationUtils.loadAnimation(this, R.anim.rotate)
        binding.loginLoadingCircleIv.startAnimation(animation)
        binding.loginDimBackground.visibility = View.VISIBLE
    }

    override fun onDeleteAccountSuccess() {
        binding.loginLoadingCircleIv.visibility = View.GONE
        binding.loginLoadingInIv.visibility = View.GONE
        binding.loginLoadingBackgroundIv.visibility = View.GONE
        binding.loginLoadingCircleIv.clearAnimation()
        binding.loginDimBackground.visibility = View.INVISIBLE

        startActivityWithClear(LoginFirstActivity::class.java)
    }

    override fun onDeleteAccountFailure(code: Int, message: String) {
        binding.loginLoadingCircleIv.visibility = View.GONE
        binding.loginLoadingInIv.visibility = View.GONE
        binding.loginLoadingBackgroundIv.visibility = View.GONE
        binding.loginLoadingCircleIv.clearAnimation()
        binding.loginDimBackground.visibility = View.INVISIBLE
        when (code) {
            3019,3020 -> {
                binding.accountSecondPwHighlightView.setBackgroundColor(Color.parseColor("#c77a4a"))
                binding.accountSecondPwFailIv.visibility = View.VISIBLE
                binding.accountSecondPwFailTv.visibility = View.VISIBLE
                binding.accountSecondPwFailTv.text= message
                binding.accountSecondPwCheckHighlightView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.accountSecondPwCheckFailIv.visibility = View.INVISIBLE
                binding.accountSecondPwCheckFailTv.visibility = View.INVISIBLE
            }
            else -> {
                Log.d("DeleteAccount/ERROR", "error")
            }
        }
    }
}