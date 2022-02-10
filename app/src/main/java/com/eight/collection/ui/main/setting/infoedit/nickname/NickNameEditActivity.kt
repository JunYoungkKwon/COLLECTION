package com.eight.collection.ui.main.setting.infoedit.nickname

import android.graphics.Color
import android.nfc.Tag
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.eight.collection.R
import com.eight.collection.data.entities.User
import com.eight.collection.data.remote.auth.Auth
import com.eight.collection.data.remote.auth.AuthService
import com.eight.collection.databinding.ActivityNicknameEditBinding
import com.eight.collection.ui.BaseActivity

class NickNameEditActivity: BaseActivity<ActivityNicknameEditBinding>(ActivityNicknameEditBinding::inflate), ChangeNickNameView, View.OnClickListener{

    override fun initAfterBinding() {
        binding.nicknameEditIb.setOnClickListener(this)
        binding.nicknameBackBtnIv.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
//            binding.nicknameEditIb -> changeNickName()
            binding.nicknameBackBtnIv -> finishActivity()

        }
    }
    private fun changeNickName() {
        val nickname = binding.nicknameTextEt.text.toString()
        val user = User("", "", "", nickname, "")

        AuthService.changeNickName(this, 8,user)
    }

    override fun onChangeNickNameLoading() {
        binding.loginLoadingInIv.visibility = View.VISIBLE
        binding.loginLoadingCircleIv.visibility = View.VISIBLE
        binding.loginLoadingBackgroundIv.visibility = View.VISIBLE
        //로딩이미지 애니메이션
        val animation = AnimationUtils.loadAnimation(this, R.anim.rotate)
        binding.loginLoadingCircleIv.startAnimation(animation)
        binding.loginDimBackground.visibility = View.VISIBLE
    }

    override fun onChangeNickNameSuccess(auth: Auth) {
        binding.loginLoadingCircleIv.visibility = View.GONE
        binding.loginLoadingInIv.visibility = View.GONE
        binding.loginLoadingBackgroundIv.visibility = View.GONE
        binding.loginLoadingCircleIv.clearAnimation()
        binding.loginDimBackground.visibility = View.INVISIBLE

        showToast("닉네임변경 성공")
    }

    override fun onChangeNickNameFailure(code: Int, message: String) {
        binding.loginLoadingCircleIv.visibility = View.GONE
        binding.loginLoadingInIv.visibility = View.GONE
        binding.loginLoadingBackgroundIv.visibility = View.GONE
        binding.loginLoadingCircleIv.clearAnimation()
        binding.loginDimBackground.visibility = View.INVISIBLE
        when (code) {
            //NickName 에러
            3002, 3000, 3050, 3051 -> {
                //오류 토스트 보이게
                Toast(this).showCustomToast(message, this)
            }

            else -> {
                Log.d("NickNameEdit/ERROR", "error")
                showToast("SEVER ERROR ")
            }
        }
    }
}