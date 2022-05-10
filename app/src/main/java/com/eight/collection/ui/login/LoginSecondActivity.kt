package com.eight.collection.ui.login

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import com.eight.collection.R
import com.eight.collection.data.entities.User
import com.eight.collection.data.remote.auth.Auth
import com.eight.collection.data.remote.auth.AuthService
import com.eight.collection.databinding.ActivityLoginSecondBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.main.MainActivity
import com.eight.collection.ui.main.setting.infoedit.nickname.NickNameEditActivity
import com.eight.collection.ui.signup.SignupFirstActivity
import com.eight.collection.utils.saveJwt
import com.eight.collection.utils.saveName
import com.eight.collection.utils.saveNickName
import com.eight.collection.utils.saveUserId

class LoginSecondActivity: BaseActivity<ActivityLoginSecondBinding>(ActivityLoginSecondBinding::inflate), LoginView, View.OnClickListener {

    override fun initAfterBinding() {
        binding.loginLoginBtnOffIv.setOnClickListener(this)
        binding.loginBackBtnIv.setOnClickListener(this)
        binding.loginIdFindTv.setOnClickListener(this)
        binding.loginPwFindTv.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
            binding.loginLoginBtnOffIv -> login()
            binding.loginBackBtnIv -> finishActivity()
            binding.loginIdFindTv -> {
                startNextActivity(IdFindFirstActivity::class.java)
                slideRight()}
            binding.loginPwFindTv -> {
                startNextActivity(PwFindFirstActivity::class.java)
                slideRight()}
        }
    }

    private fun login() {
        val id = binding.loginIdEt.text.toString()
        val password = binding.loginPwEt.text.toString()
        val user = User(id, password, "", "", "","","","")

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

        saveJwt(auth.jwt)
        saveUserId(auth.userId)
        saveNickName(auth.nickName)
        saveName(auth.name)

        startActivityWithClear(MainActivity::class.java)
        fadeIn()
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
                binding.loginPwFailIv.visibility = View.GONE
                binding.loginPwFailTv.visibility = View.GONE

                val constraints = ConstraintSet()
                constraints.clone(binding.loginCl)
                constraints.connect(
                    binding.loginPwEt.id,
                    ConstraintSet.TOP,
                    binding.loginIdFailIv.id,
                    ConstraintSet.BOTTOM,
                    convertDpToPixel(10f, this)
                )
                constraints.applyTo(binding.loginCl)
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
                binding.loginIdFailIv.visibility = View.GONE
                binding.loginIdFailTv.visibility = View.GONE

                val constraints = ConstraintSet()
                constraints.clone(binding.loginCl)
                constraints.connect(
                    binding.loginPwEt.id,
                    ConstraintSet.TOP,
                    binding.loginIdHighlightView.id,
                    ConstraintSet.BOTTOM,
                    convertDpToPixel(30f, this)
                )
                constraints.applyTo(binding.loginCl)


            }

            else -> {
                Log.d("LoginSecond/Error", "error")
            }
        }
    }

    fun convertDpToPixel(dp: Float, context: Context): Int {
        return (dp * (context.resources
            .displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
    }
}