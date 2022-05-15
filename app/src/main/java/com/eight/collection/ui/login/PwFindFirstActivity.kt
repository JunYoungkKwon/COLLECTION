package com.eight.collection.ui.login

import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.graphics.Color
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.eight.collection.ApplicationClass
import com.eight.collection.R
import com.eight.collection.data.remote.auth.AuthService
import com.eight.collection.databinding.ActivityPwFindFirstBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.utils.*
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit


class PwFindFirstActivity: BaseActivity<ActivityPwFindFirstBinding>(ActivityPwFindFirstBinding::inflate), View.OnClickListener , PwFindView {

    private var phoneNum = ""
    private var phoneEdit = ""
    private val TAG = "FireTest"
    private val auth = Firebase.auth
    private var storedVerificationId = ""
    private var skip = false
    val br: BroadcastReceiver = SMSReceiver()

    override fun initAfterBinding() {
        binding.pwFindFirstBackBtnIv.setOnClickListener(this)
        binding.pwFindFirstPhoneEt.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        binding.pwFindSecondEditIb.setOnClickListener(this)
        binding.pwFindFirstSecretNumberCheckIb.setOnClickListener(this)
        binding.pwFindFirstPhoneCheckIb.setOnClickListener(this)

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
            binding.pwFindFirstBackBtnIv -> {
                finishActivity()
            }
            binding.pwFindSecondEditIb -> {
                findPw()
            }
            binding.pwFindFirstPhoneCheckIb -> {
                phoneNum = binding.pwFindFirstPhoneEt.text.toString().replace("[^0-9]".toRegex(), "")
                if (phoneNum.length > 3){
                    phoneNumberChange(phoneNum)
                    secretNum()
                }
            }

            binding.pwFindFirstSecretNumberCheckIb -> {
                val smsCode = binding.pwFindFirstSecretNumberEt.text.toString()
                val credential = PhoneAuthProvider.getCredential(storedVerificationId, smsCode)
                signInWithPhoneAuthCredential(credential)
            }
        }
    }
    private fun findPw() {
        val name = binding.pwFindFirstNameEt.text.toString()
        val id = binding.pwFindFirstIdEt.text.toString()
        phoneNum = binding.pwFindFirstPhoneEt.text.toString().replace("[^0-9]".toRegex(), "")

        //if (skip == false) {return}

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

     private fun secretNum() {
        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d(TAG, "onVerificationCompleted")
                binding.pwFindFirstSecretNumberEt.setText(ApplicationClass.getCode())

            }

            override fun onVerificationFailed(e: FirebaseException) {
                //핸드폰 번호 입력 받았는데 이상할 경우
                Log.w(TAG, "onVerificationFailed")
                binding.pwFindFirstPhoneUnderscoreView.setBackgroundColor(Color.parseColor("#c77a4a"))
                binding.pwFindFirstPhoneErrorTv.visibility = View.VISIBLE
                binding.pwFindFirstPhoneErrorTv.text= "*핸드폰 번호를 다시 확인해 주세요."

                binding.pwFindFirstIdUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.pwFindFirstIdErrorTv.visibility = View.GONE
                binding.pwFindFirstNameUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.pwFindFirstNameErrorTv.visibility = View.GONE
                binding.pwFindFirstSecretNumberUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.pwFindFirstSecretNumberErrorTv.visibility = View.GONE

                if (e is FirebaseAuthInvalidCredentialsException) { }
                else if (e is FirebaseTooManyRequestsException) { }

            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                //문자 발송시
                binding.pwFindFirstPhoneUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.pwFindFirstPhoneErrorTv.visibility = View.GONE
                binding.pwFindFirstIdUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.pwFindFirstIdErrorTv.visibility = View.GONE
                binding.pwFindFirstNameUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                binding.pwFindFirstNameErrorTv.visibility = View.GONE

                binding.pwFindFirstSecretNumberEt.isFocusable = true
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
                    binding.pwFindFirstSecretNumberUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                    binding.pwFindFirstSecretNumberErrorTv.visibility = View.GONE
                    binding.pwFindFirstIdUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                    binding.pwFindFirstIdErrorTv.visibility = View.GONE
                    binding.pwFindFirstPhoneUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                    binding.pwFindFirstPhoneErrorTv.visibility = View.GONE
                    binding.pwFindFirstNameUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                    binding.pwFindFirstNameErrorTv.visibility = View.GONE
                    Toast(this).showCustomToast("인증 성공", this)
                } else {
                    binding.pwFindFirstSecretNumberUnderscoreView.setBackgroundColor(Color.parseColor("#c77a4a"))
                    binding.pwFindFirstSecretNumberErrorTv.visibility = View.VISIBLE
                    binding.pwFindFirstSecretNumberErrorTv.text= "*인증번호를 다시 확인해 주세요."

                    binding.pwFindFirstIdUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                    binding.pwFindFirstIdErrorTv.visibility = View.GONE
                    binding.pwFindFirstPhoneUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                    binding.pwFindFirstPhoneErrorTv.visibility = View.GONE
                    binding.pwFindFirstNameUnderscoreView.setBackgroundColor(Color.parseColor("#c3b5ac"))
                    binding.pwFindFirstNameErrorTv.visibility = View.GONE
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