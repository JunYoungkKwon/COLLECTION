package com.eight.collection.ui.signup

import android.content.BroadcastReceiver
import android.content.IntentFilter
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
import androidx.core.widget.addTextChangedListener
import com.eight.collection.ApplicationClass
import com.eight.collection.ApplicationClass.Companion.TAG
import com.eight.collection.R
import com.eight.collection.data.entities.User
import com.eight.collection.data.remote.auth.AuthService
import com.eight.collection.databinding.ActivitySignupThirdBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.login.LoginSecondActivity
import com.eight.collection.ui.main.MainActivity
import com.eight.collection.utils.SMSReceiver
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class SignupThirdActivity: BaseActivity<ActivitySignupThirdBinding>(ActivitySignupThirdBinding::inflate), SignUpView, CheckIdView, View.OnClickListener {

    var checkId : Int = 0
    val br: BroadcastReceiver = SMSReceiver()
    private var phoneNum = ""
    private var phoneEdit = ""
    private val auth = Firebase.auth
    private var storedVerificationId = ""
    private var skip = false

    override fun initAfterBinding() {
        binding.signUpThirdIcBack.setOnClickListener(this)
        binding.signUpThirdFinishButton.setOnClickListener(this)
        binding.signUpThirdDoubleCheckIv.setOnClickListener(this)
        binding.signUpThirdPhoneEt.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        val value = intent.getStringExtra("postnickname")
        binding.signUpThirdNicknameEt.setText(value)
        binding.signUpThirdPhoneCheckIb.setOnClickListener(this)
        binding.signUpThirdSecretNumberCheckIb.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        val client = SmsRetriever.getClient(this)
        val task = client.startSmsRetriever()
        task.addOnSuccessListener {
            val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
            registerReceiver(br, intentFilter)
        }
        task.addOnFailureListener { }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(br)
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
                    if(skip == false){
                        return
                    }
                    else {
                        signUp()
                    }
                }
            }
            binding.signUpThirdDoubleCheckIv -> checkId()
            binding.signUpThirdIcBack -> finish()
            binding.signUpThirdPhoneCheckIb -> {
                phoneNum = binding.signUpThirdPhoneEt.text.toString().replace("[^0-9]".toRegex(), "")
                if (phoneNum.length > 3){
                    phoneNumberChange(phoneNum)
                    secretNum()
                }
            }
            binding.signUpThirdSecretNumberCheckIb -> {
                val smsCode = binding.signUpThirdSecretNumberEt.text.toString()
                val credential = PhoneAuthProvider.getCredential(storedVerificationId, smsCode)
                signInWithPhoneAuthCredential(credential)
            }
        }
    }

    private fun getUser() : User {
        val id : String = binding.signUpThirdIdEt.text.toString()
        val pwd : String = binding.signUpThirdPasswordEt.text.toString()
        val name : String = binding.signUpThirdNameEt.text.toString()
        val nickname : String = binding.signUpThirdNicknameEt.text.toString()
        val phonenumber = binding.signUpThirdPhoneEt.text.toString().replace("[^0-9]".toRegex(), "")

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

    private fun secretNum() {
        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d(TAG, "onVerificationCompleted")
                binding.signUpThirdSecretNumberEt.setText(ApplicationClass.getCode())

            }

            override fun onVerificationFailed(e: FirebaseException) {
                //핸드폰 번호 입력 받았는데 이상할 경우
                Log.w(TAG, "onVerificationFailed")
                binding.signUpThirdPhoneUnderscoreView.setBackgroundColor(Color.parseColor("#c77a4a"))
                binding.signUpThirdPhoneErrorTv.visibility = View.VISIBLE
                binding.signUpThirdPhoneErrorTv.text= "*핸드폰 번호를 다시 확인해 주세요."

                binding.signUpThirdNameUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.signUpThirdNameErrorTv.visibility = View.GONE
                binding.signUpThirdIdUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.signUpThirdIdErrorTv.visibility = View.GONE
                binding.signUpThirdPasswordUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.signUpThirdPasswordErrorTv.visibility = View.GONE
                binding.signUpThirdPasswordCheckUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.signUpThirdPasswordCheckErrorTv.visibility = View.GONE
                binding.signUpThirdSecretNumberUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.signUpThirdSecretNumberErrorTv.visibility = View.GONE
                binding.signUpThirdBirthUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.signUpThirdBirthErrorTv.visibility = View.GONE

                if (e is FirebaseAuthInvalidCredentialsException) { }
                else if (e is FirebaseTooManyRequestsException) { }

            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                //문자 발송시
                binding.signUpThirdPhoneUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.signUpThirdPhoneErrorTv.visibility = View.GONE
                binding.signUpThirdIdUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.signUpThirdIdErrorTv.visibility = View.GONE
                binding.signUpThirdNameUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.signUpThirdNameErrorTv.visibility = View.GONE
                binding.signUpThirdPasswordUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.signUpThirdPasswordErrorTv.visibility = View.GONE
                binding.signUpThirdPasswordCheckUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.signUpThirdPasswordCheckErrorTv.visibility = View.GONE
                binding.signUpThirdBirthUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.signUpThirdBirthErrorTv.visibility = View.GONE

                binding.signUpThirdSecretNumberEt.isFocusable = true
                storedVerificationId = verificationId
            }
        }

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneEdit)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }


    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    skip = true
                    binding.signUpThirdSecretNumberUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                    binding.signUpThirdSecretNumberErrorTv.visibility = View.GONE
                    binding.signUpThirdIdUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                    binding.signUpThirdIdErrorTv.visibility = View.GONE
                    binding.signUpThirdPhoneUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                    binding.signUpThirdPhoneErrorTv.visibility = View.GONE
                    binding.signUpThirdNameUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                    binding.signUpThirdNameErrorTv.visibility = View.GONE
                    binding.signUpThirdPasswordUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                    binding.signUpThirdPasswordErrorTv.visibility = View.GONE
                    binding.signUpThirdPasswordCheckUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                    binding.signUpThirdPasswordCheckErrorTv.visibility = View.GONE
                    binding.signUpThirdBirthUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                    binding.signUpThirdBirthErrorTv.visibility = View.GONE
                    Toast(this).showCustomToast("인증 성공", this)

                    binding.signUpThirdSecretNumberEt.isFocusable = false
                } else {
                    binding.signUpThirdSecretNumberUnderscoreView.setBackgroundColor(Color.parseColor("#c77a4a"))
                    binding.signUpThirdSecretNumberErrorTv.visibility = View.VISIBLE
                    binding.signUpThirdSecretNumberErrorTv.text= "*인증번호를 다시 확인해 주세요."

                    binding.signUpThirdIdUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                    binding.signUpThirdIdErrorTv.visibility = View.GONE
                    binding.signUpThirdPhoneUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                    binding.signUpThirdPhoneErrorTv.visibility = View.GONE
                    binding.signUpThirdNameUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                    binding.signUpThirdNameErrorTv.visibility = View.GONE
                    binding.signUpThirdPasswordUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                    binding.signUpThirdPasswordErrorTv.visibility = View.GONE
                    binding.signUpThirdPasswordCheckUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                    binding.signUpThirdPasswordCheckErrorTv.visibility = View.GONE
                    binding.signUpThirdBirthUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                    binding.signUpThirdBirthErrorTv.visibility = View.GONE
                    if (task.exception is FirebaseAuthInvalidCredentialsException) { }
                }
            }
    }


    private fun phoneNumberChange(msg : String): String {

        if (msg.isNotEmpty()){
            val firstNumber : String = msg.substring(0,3)
            phoneEdit = msg.substring(3)

            when(firstNumber){
                "010" -> phoneEdit = "+8210$phoneEdit"
                "011" -> phoneEdit = "+8211$phoneEdit"
                "016" -> phoneEdit = "+8216$phoneEdit"
                "017" -> phoneEdit = "+8217$phoneEdit"
                "018" -> phoneEdit = "+8218$phoneEdit"
                "019" -> phoneEdit = "+8219$phoneEdit"
                "106" -> phoneEdit = "+82106$phoneEdit"
            }
            return phoneEdit
        }
        return phoneEdit
    }
}