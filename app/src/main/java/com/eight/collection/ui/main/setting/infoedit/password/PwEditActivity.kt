package com.eight.collection.ui.main.setting.infoedit.password

import android.graphics.Color
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
import com.eight.collection.databinding.ActivityPwEditBinding
import com.eight.collection.ui.BaseActivity

class PwEditActivity: BaseActivity<ActivityPwEditBinding>(ActivityPwEditBinding::inflate),ChangePwView, View.OnClickListener{

    override fun initAfterBinding() {
        binding.pwEditIb.setOnClickListener(this)
        binding.pwBackBtnIv.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
            binding.pwEditIb -> changePw()
            binding.pwBackBtnIv -> finishActivity()

        }
    }

    private fun changePw() {

        if (binding.pwNewPwEt.text.toString().isNotEmpty() && binding.pwOriginPwEt.text.toString().isNotEmpty()) {
            if (binding.pwNewPwEt.text.toString().equals(binding.pwOriginPwEt.text.toString())) {
                //origin show
                binding.pwNewPwHighlightView.setBackgroundColor(Color.parseColor("#c77a4a"))
                binding.pwNewPwFailIv.visibility = View.VISIBLE
                binding.pwNewPwFailTv.visibility = View.VISIBLE
                binding.pwNewPwFailTv.text = "기존의 비밀번호와 동일합니다."
                //extra hide
                binding.pwOriginPwHighlightView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.pwOriginPwFailIv.visibility = View.INVISIBLE
                binding.pwOriginPwFailTv.visibility = View.INVISIBLE
                binding.pwNewCheckPwHighlightView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.pwNewCheckPwFailIv.visibility = View.INVISIBLE
                binding.pwNewCheckPwFailTv.visibility = View.INVISIBLE
                return
            }
        }

//        if (binding.pwOriginPwEt.text.toString().isEmpty()){
//            binding.pwOriginPwHighlightView.setBackgroundColor(Color.parseColor("#c77a4a"))
//            binding.pwOriginPwFailIv.visibility = View.VISIBLE
//            binding.pwOriginPwFailTv.visibility = View.VISIBLE
//            binding.pwOriginPwFailTv.text= "비밀번호는 6~15자리를 입력해주세요"
//
//            binding.pwNewCheckPwHighlightView.setBackgroundColor(Color.parseColor("#c3b5ac"))
//            binding.pwNewCheckPwFailIv.visibility = View.INVISIBLE
//            binding.pwNewCheckPwFailTv.visibility = View.INVISIBLE
//            binding.pwNewPwHighlightView.setBackgroundColor(Color.parseColor("#c3b5ac"))
//            binding.pwNewPwFailIv.visibility = View.INVISIBLE
//            binding.pwNewPwFailTv.visibility = View.INVISIBLE
//            return
//        }
//
//        if (binding.pwNewPwEt.text.toString().isEmpty()){
//            //origin show
//            binding.pwNewPwHighlightView.setBackgroundColor(Color.parseColor("#c77a4a"))
//            binding.pwNewPwFailIv.visibility = View.VISIBLE
//            binding.pwNewPwFailTv.visibility = View.VISIBLE
//            binding.pwNewPwFailTv.text = "비밀번호는 6~15자리를 입력해주세요"
//            //extra hide
//            binding.pwOriginPwHighlightView.setBackgroundColor(Color.parseColor("#c3b5ac"))
//            binding.pwOriginPwFailIv.visibility = View.INVISIBLE
//            binding.pwOriginPwFailTv.visibility = View.INVISIBLE
//            binding.pwNewCheckPwHighlightView.setBackgroundColor(Color.parseColor("#c3b5ac"))
//            binding.pwNewCheckPwFailIv.visibility = View.INVISIBLE
//            binding.pwNewCheckPwFailTv.visibility = View.INVISIBLE
//            return
//        }
//
//        if (binding.pwNewCheckPwEt.text.toString().isEmpty()){
//            //origin show
//            binding.pwNewCheckPwHighlightView.setBackgroundColor(Color.parseColor("#c77a4a"))
//            binding.pwNewCheckPwFailIv.visibility = View.VISIBLE
//            binding.pwNewCheckPwFailTv.visibility = View.VISIBLE
//            binding.pwNewCheckPwFailTv.text = "비밀번호는 6~15자리를 입력해주세요"
//            //extra hide
//            binding.pwNewPwHighlightView.setBackgroundColor(Color.parseColor("#c3b5ac"))
//            binding.pwNewPwFailIv.visibility = View.INVISIBLE
//            binding.pwNewPwFailTv.visibility = View.INVISIBLE
//            binding.pwOriginPwHighlightView.setBackgroundColor(Color.parseColor("#c3b5ac"))
//            binding.pwOriginPwFailIv.visibility = View.INVISIBLE
//            binding.pwOriginPwFailTv.visibility = View.INVISIBLE
//            return
//        }


        if (binding.pwNewPwEt.text.toString().isNotEmpty() && binding.pwNewCheckPwEt.text.toString().isNotEmpty()) {
            if (!binding.pwNewPwEt.text.toString().equals(binding.pwNewCheckPwEt.text.toString())) {
                //origin show
                binding.pwNewCheckPwHighlightView.setBackgroundColor(Color.parseColor("#c77a4a"))
                binding.pwNewCheckPwFailIv.visibility = View.VISIBLE
                binding.pwNewCheckPwFailTv.visibility = View.VISIBLE
                binding.pwNewCheckPwFailTv.text = "새 비밀번호와 일치하지 않습니다."
                //extra hide
                binding.pwNewPwHighlightView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.pwNewPwFailIv.visibility = View.INVISIBLE
                binding.pwNewPwFailTv.visibility = View.INVISIBLE
                binding.pwOriginPwHighlightView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.pwOriginPwFailIv.visibility = View.INVISIBLE
                binding.pwOriginPwFailTv.visibility = View.INVISIBLE
                return
            }
        }

        val orginpw = binding.pwOriginPwEt.text.toString()
        val newpw = binding.pwNewPwEt.text.toString()
        val newcheckpw = binding.pwNewCheckPwEt.text.toString()
        val user = User("", "", "", "", "", orginpw, newpw, newcheckpw)

        AuthService.changePw(this, user)
    }

    override fun onChangePwLoading() {
        binding.loginLoadingInIv.visibility = View.VISIBLE
        binding.loginLoadingCircleIv.visibility = View.VISIBLE
        binding.loginLoadingBackgroundIv.visibility = View.VISIBLE
        //로딩이미지 애니메이션
        val animation = AnimationUtils.loadAnimation(this, R.anim.rotate)
        binding.loginLoadingCircleIv.startAnimation(animation)
        binding.loginDimBackground.visibility = View.VISIBLE
    }

    override fun onChangePwSuccess(auth: Auth) {
        binding.loginLoadingCircleIv.visibility = View.GONE
        binding.loginLoadingInIv.visibility = View.GONE
        binding.loginLoadingBackgroundIv.visibility = View.GONE
        binding.loginLoadingCircleIv.clearAnimation()
        binding.loginDimBackground.visibility = View.INVISIBLE

        binding.pwNewPwHighlightView.setBackgroundColor(Color.parseColor("#c3b5ac"))
        binding.pwNewPwFailIv.visibility = View.INVISIBLE
        binding.pwNewPwFailTv.visibility = View.INVISIBLE
        binding.pwOriginPwHighlightView.setBackgroundColor(Color.parseColor("#c3b5ac"))
        binding.pwOriginPwFailIv.visibility = View.INVISIBLE
        binding.pwOriginPwFailTv.visibility = View.INVISIBLE
        binding.pwNewCheckPwHighlightView.setBackgroundColor(Color.parseColor("#c3b5ac"))
        binding.pwNewCheckPwFailIv.visibility = View.INVISIBLE
        binding.pwNewCheckPwFailTv.visibility = View.INVISIBLE

        Toast(this).showCustomToast("비밀번호가 변경되었습니다.", this)
    }

    override fun onChangePwFailure(code: Int, message: String) {
        binding.loginLoadingCircleIv.visibility = View.GONE
        binding.loginLoadingInIv.visibility = View.GONE
        binding.loginLoadingBackgroundIv.visibility = View.GONE
        binding.loginLoadingCircleIv.clearAnimation()
        binding.loginDimBackground.visibility = View.INVISIBLE
        when (code) {
            //originPw error
            3014, 3004, 3012 -> {
                //origin show
                binding.pwOriginPwHighlightView.setBackgroundColor(Color.parseColor("#c77a4a"))
                binding.pwOriginPwFailIv.visibility = View.VISIBLE
                binding.pwOriginPwFailTv.visibility = View.VISIBLE
                binding.pwOriginPwFailTv.text= message
                //extra hide
                binding.pwNewCheckPwHighlightView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.pwNewCheckPwFailIv.visibility = View.INVISIBLE
                binding.pwNewCheckPwFailTv.visibility = View.INVISIBLE
                binding.pwNewPwHighlightView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.pwNewPwFailIv.visibility = View.INVISIBLE
                binding.pwNewPwFailTv.visibility = View.INVISIBLE
            }
            3116 , 3015 -> {
                //origin show
                binding.pwNewPwHighlightView.setBackgroundColor(Color.parseColor("#c77a4a"))
                binding.pwNewPwFailIv.visibility = View.VISIBLE
                binding.pwNewPwFailTv.visibility = View.VISIBLE
                binding.pwNewPwFailTv.text= message
                //extra hide
                binding.pwNewCheckPwHighlightView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.pwNewCheckPwFailIv.visibility = View.INVISIBLE
                binding.pwNewCheckPwFailTv.visibility = View.INVISIBLE
                binding.pwOriginPwHighlightView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.pwOriginPwFailTv.visibility = View.INVISIBLE
                binding.pwOriginPwFailIv.visibility = View.INVISIBLE
            }

            3117 , 3016-> {
                //origin show
                binding.pwNewCheckPwHighlightView.setBackgroundColor(Color.parseColor("#c77a4a"))
                binding.pwNewCheckPwFailIv.visibility = View.VISIBLE
                binding.pwNewCheckPwFailTv.visibility = View.VISIBLE
                binding.pwNewCheckPwFailTv.text= message
                //extra hide
                binding.pwOriginPwHighlightView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.pwOriginPwFailIv.visibility = View.INVISIBLE
                binding.pwOriginPwFailTv.visibility = View.INVISIBLE
                binding.pwNewPwHighlightView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.pwNewPwFailIv.visibility = View.INVISIBLE
                binding.pwNewPwFailTv.visibility = View.INVISIBLE
            }

            else -> {
                Log.d("Sever/PwEditActivity/Error", "error")
            }
        }
    }
}