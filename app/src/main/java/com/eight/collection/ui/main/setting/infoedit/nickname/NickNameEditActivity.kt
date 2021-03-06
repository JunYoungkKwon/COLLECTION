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
import com.eight.collection.ui.login.LoginFirstActivity
import com.eight.collection.utils.saveChangeNickName
import com.eight.collection.utils.saveNickName

class NickNameEditActivity: BaseActivity<ActivityNicknameEditBinding>(ActivityNicknameEditBinding::inflate), ChangeNickNameView, View.OnClickListener{

    override fun initAfterBinding() {
        binding.nicknameEditIb.setOnClickListener(this)
        binding.nicknameBackBtnIv.setOnClickListener(this)

    }



    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
            binding.nicknameEditIb -> changeNickName()
            binding.nicknameBackBtnIv -> finishActivity()

        }
    }
    private fun changeNickName() {
        val nickname = binding.nicknameTextEt.text.toString()
        val user = User("", "", "", nickname, "","","","")

        AuthService.changeNickName(this, user)
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
        saveChangeNickName(auth.nickName)

        Toast(this).showCustomToast("닉네임이 변경되었습니다.", this)
    }

    override fun onChangeNickNameFailure(code: Int, message: String) {
        binding.loginLoadingCircleIv.visibility = View.GONE
        binding.loginLoadingInIv.visibility = View.GONE
        binding.loginLoadingBackgroundIv.visibility = View.GONE
        binding.loginLoadingCircleIv.clearAnimation()
        binding.loginDimBackground.visibility = View.INVISIBLE
        when (code) {
            //NickName 에러
            3007, 3017, 3073, 3006 -> {
                //오류 토스트 보이게
                Toast(this).showCustomToast(message, this)
            }

            else -> {
                Log.d("NickNameEdit/ERROR", "error")
            }
        }
    }
}