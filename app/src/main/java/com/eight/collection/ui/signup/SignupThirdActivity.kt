package com.eight.collection.ui.signup

import android.graphics.Color
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.eight.collection.ApplicationClass.Companion.TAG
import com.eight.collection.R
import com.eight.collection.data.entities.User
import com.eight.collection.data.remote.auth.AuthService
import com.eight.collection.databinding.ActivitySignupThirdBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.login.LoginSecondActivity
import com.eight.collection.ui.main.MainActivity

class SignupThirdActivity: BaseActivity<ActivitySignupThirdBinding>(ActivitySignupThirdBinding::inflate), SignUpView, CheckIdView, View.OnClickListener {

    var checkId : Int = 0

    override fun initAfterBinding() {
        binding.signUpThirdIcBack.setOnClickListener(this)
        binding.signUpThirdFinishButton.setOnClickListener(this)
        binding.signUpThirdDoubleCheckIv.setOnClickListener(this)
        val value = intent.getStringExtra("postnickname")
        binding.signUpThirdNicknameEt.setText(value)

    }

    override fun onClick(v: View?) {
        if(v == null) return

        when(v) {
            binding.signUpThirdFinishButton -> {
                if(checkId == 0){
                    var layoutInflater = LayoutInflater.from(this).inflate(R.layout.toast_custom,null)
                    var text : TextView = layoutInflater.findViewById(R.id.toast_text_tv)
                    text.text = "중복확인을 해주세요."
                    var toast = Toast(this)
                    toast.view = layoutInflater
                    toast.setGravity(Gravity.BOTTOM, 0, 410)
                    toast.show()
                    return
                }
                checkId()
                if(checkId == 1){
                    signUp()
                }
            }
            binding.signUpThirdDoubleCheckIv -> checkId()
            binding.signUpThirdIcBack -> finish()
        }
    }

    private fun getUser() : User {
        val id : String = binding.signUpThirdIdEt.text.toString()
        val pwd : String = binding.signUpThirdPasswordEt.text.toString()
        val name : String = binding.signUpThirdNameEt.text.toString()
        val nickname : String = binding.signUpThirdNicknameEt.text.toString()
        val phonenumber : String = binding.signUpThirdPhoneEt.text.toString()

        return User(id,pwd,name,nickname,phonenumber,"","","")
    }

    private fun signUp() {
        if (binding.signUpThirdPasswordEt.text.toString() != binding.signUpThirdPasswordCheckEt.text.toString()) {
            binding.signUpThirdPasswordCheckUnderscoreView.setBackgroundColor(Color.parseColor("#c77a4a"))
            binding.signUpThirdPasswordCheckErrorTv.visibility = View.VISIBLE
            binding.signUpThirdPasswordCheckErrorTv.text= "*비밀번호가 일치하지 않습니다."

            //나머지 원래대로
            binding.signUpThirdNameUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
            binding.signUpThirdNameErrorTv.visibility = View.GONE

            binding.signUpThirdIdUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
            binding.signUpThirdIdErrorTv.visibility = View.GONE

            binding.signUpThirdPasswordUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
            binding.signUpThirdPasswordErrorTv.visibility = View.GONE

            binding.signUpThirdPhoneUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
            binding.signUpThirdPhoneErrorTv.visibility = View.GONE

            return
        }

        AuthService.signUp(this, getUser())
    }

    override fun onSignUpLoading() {
    }

    override fun onSignUpSuccess() {
        var layoutInflater = LayoutInflater.from(this).inflate(R.layout.toast_custom,null)
        var text : TextView = layoutInflater.findViewById(R.id.toast_text_tv)
        text.text = "회원가입에 성공하였습니다."
        var toast = Toast(this)
        toast.view = layoutInflater
        toast.setGravity(Gravity.BOTTOM, 0, 270)
        toast.show()
        startActivityWithClear(LoginSecondActivity::class.java)
    }

    override fun onSignUpFailure(code: Int, message: String) {
        when(code) {
            //Name 에러
            3041, 3050, 3051, 3072 -> {
                binding.signUpThirdNameUnderscoreView.setBackgroundColor(Color.parseColor("#c77a4a"))
                binding.signUpThirdNameErrorTv.visibility = View.VISIBLE
                binding.signUpThirdNameErrorTv.text= '*' + message

                //나머지 원래대로
                binding.signUpThirdIdUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.signUpThirdIdErrorTv.visibility = View.GONE

                binding.signUpThirdPasswordUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.signUpThirdPasswordErrorTv.visibility = View.GONE

                binding.signUpThirdPasswordCheckUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.signUpThirdPasswordCheckErrorTv.visibility = View.GONE

                binding.signUpThirdPhoneUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.signUpThirdPhoneErrorTv.visibility = View.GONE


            }
            //ID 에러
            3000, 3001, 3002, 3050, 3051, 3074 -> {
                binding.signUpThirdIdUnderscoreView.setBackgroundColor(Color.parseColor("#c77a4a"))
                binding.signUpThirdIdErrorTv.visibility = View.VISIBLE
                binding.signUpThirdIdErrorTv.text= '*' + message
                binding.signUpThirdIdErrorTv.setTextColor(Color.parseColor("#c77a4a"))

                //나머지 원래대로
                binding.signUpThirdNameUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.signUpThirdNameErrorTv.visibility = View.GONE

                binding.signUpThirdPasswordUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.signUpThirdPasswordErrorTv.visibility = View.GONE

                binding.signUpThirdPasswordCheckUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.signUpThirdPasswordCheckErrorTv.visibility = View.GONE

                binding.signUpThirdPhoneUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.signUpThirdPhoneErrorTv.visibility = View.GONE

            }
            //Password 에러
            3003, 3004, 3050, 3051, 3080 -> {
                binding.signUpThirdPasswordUnderscoreView.setBackgroundColor(Color.parseColor("#c77a4a"))
                binding.signUpThirdPasswordErrorTv.visibility = View.VISIBLE
                binding.signUpThirdPasswordErrorTv.text= '*' + message

                //나머지 원래대로
                binding.signUpThirdNameUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.signUpThirdNameErrorTv.visibility = View.GONE

                binding.signUpThirdIdUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.signUpThirdIdErrorTv.visibility = View.GONE

                binding.signUpThirdPasswordCheckUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.signUpThirdPasswordCheckErrorTv.visibility = View.GONE

                binding.signUpThirdPhoneUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.signUpThirdPhoneErrorTv.visibility = View.GONE
            }
            //Phone 에러
            3008, 3009, 3010, 3050, 3051 -> {
                binding.signUpThirdPhoneUnderscoreView.setBackgroundColor(Color.parseColor("#c77a4a"))
                binding.signUpThirdPhoneErrorTv.visibility = View.VISIBLE
                binding.signUpThirdPhoneErrorTv.text= '*' + message

                //나머지 원래대로
                binding.signUpThirdNameUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.signUpThirdNameErrorTv.visibility = View.GONE

                binding.signUpThirdIdUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.signUpThirdIdErrorTv.visibility = View.GONE

                binding.signUpThirdPasswordUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.signUpThirdPasswordErrorTv.visibility = View.GONE

                binding.signUpThirdPasswordCheckUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.signUpThirdPasswordCheckErrorTv.visibility = View.GONE

            }
            else -> {
                showToast("SEVER ERROR")
            }
        }
    }

    private fun checkId() {
        val id : String = binding.signUpThirdIdEt.text.toString()
        AuthService.checkId(this, id)
    }


    override fun onCheckIdLoading() {
    }

    override fun onCheckIdSuccess() {
        binding.signUpThirdIdUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
        binding.signUpThirdIdErrorTv.visibility = View.VISIBLE
        binding.signUpThirdIdErrorTv.text= "*사용가능한 아이디입니다."
        binding.signUpThirdIdErrorTv.setTextColor(Color.parseColor("#71a238"))
        checkId = 1
    }

    override fun onCheckIdFailure(code: Int, message: String) {
        when(code) {
            //ID 에러
            3000, 3002, 3050, 3051 -> {
                binding.signUpThirdIdUnderscoreView.setBackgroundColor(Color.parseColor("#c77a4a"))
                binding.signUpThirdIdErrorTv.visibility = View.VISIBLE
                binding.signUpThirdIdErrorTv.text = '*' + message
                binding.signUpThirdIdErrorTv.setTextColor(Color.parseColor("#c77a4a"))

                //나머지 원래대로
                binding.signUpThirdNameUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.signUpThirdNameErrorTv.visibility = View.GONE

                binding.signUpThirdPasswordUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.signUpThirdPasswordErrorTv.visibility = View.GONE

                binding.signUpThirdPasswordCheckUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.signUpThirdPasswordCheckErrorTv.visibility = View.GONE

                binding.signUpThirdPhoneUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.signUpThirdPhoneErrorTv.visibility = View.GONE

                checkId = 0
            }
            else -> {
                showToast("SEVER ERROR")
                checkId = 0
            }
        }
    }

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_third)
        Log.d("Log","SignupThirdActivity")

        var data:String?
        data = intent.getStringExtra("key")

        Log.d("Log","value:" + data)
    }*/

    /*override fun onSignUpLoading() {
        binding.signUpLoadingPb.visibility = View.VISIBLE
    }

    override fun onSignUpSuccess() {
        binding.signUpLoadingPb.visibility = View.GONE

        finish()
    }

    override fun onSignUpFailure(code: Int, message: String) {
        binding.signUpLoadingPb.visibility = View.GONE

        when(code) {
            2016, 2017 -> {
                binding.signUpEmailErrorTv.visibility = View.VISIBLE
                binding.signUpEmailErrorTv.text = message
            }
        }
    }*/
    /*SignUpView*/
}