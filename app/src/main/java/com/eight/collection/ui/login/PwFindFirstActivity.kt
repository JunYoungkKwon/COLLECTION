package com.eight.collection.ui.login

import android.graphics.Color
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import com.eight.collection.R
import com.eight.collection.data.remote.auth.Auth
import com.eight.collection.data.remote.auth.AuthService
import com.eight.collection.databinding.ActivityLoginFirstBinding
import com.eight.collection.databinding.ActivityPwFindFirstBinding
import com.eight.collection.ui.BaseActivity
import com.eight.collection.ui.signup.SignupFirstActivity
import com.eight.collection.utils.saveUserId
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager.TAG
import androidx.lifecycle.Observer
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class PwFindFirstActivity: BaseActivity<ActivityPwFindFirstBinding>(ActivityPwFindFirstBinding::inflate), View.OnClickListener , PwFindView {

    private var phoneNum = ""
    private var phoneEdit = ""
    private val code = "123456"
    private val TAG = "FireTest"
    private val auth = Firebase.auth
    //private val credential = AuthCredential.CONTENTS_FILE_DESCRIPTOR
    private var storedVerificationId = ""

    override fun initAfterBinding() {
        binding.pwFindFirstBackBtnIv.setOnClickListener(this)
        binding.pwFindFirstPhoneEt.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        binding.pwFindSecondEditIb.setOnClickListener(this)
        binding.pwFindFirstSecretNumberCheckIb.setOnClickListener(this)
        binding.pwFindFirstPhoneCheckIb.setOnClickListener(this)
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

//        if (binding.pwFindFirstSecretNumberEt.text.toString().isEmpty()) {
//            return
//        }

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
            }

            override fun onVerificationFailed(e: FirebaseException) {
                //핸드폰 번호 입력 받았는데 이상할 경우
                Log.w(TAG, "onVerificationFailed")

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }

                // Show a message and update the UI
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                //문자 발송시
                Log.d(TAG, "onCodeSent")
                storedVerificationId = verificationId

            }
        }

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneEdit)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                        showToast("인증완료")
                    Log.d(TAG, "success")

                    val user = task.result?.user
                } else {
                    // Sign in failed, display a message and update the UI

                    Log.d(TAG, "fail1")
                    showToast("인증실패")
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        showToast("fail2")
                    }
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
            Log.d("국가코드로 변경된 번호 ",phoneEdit)
            return phoneEdit
        }
        return phoneEdit
    }

//    private val callbacks by lazy {
//        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//            // 번호인증 혹은 기타 다른 인증(구글로그인, 이메일로그인 등) 끝난 상태
//            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//                // This callback will be invoked in two situations:
//                // 1 - Instant verification. In some cases the phone number can be instantly
//                //     verified without needing to send or enter a verification code.
//                // 2 - Auto-retrieval. On some devices Google Play services can automatically
//                //     detect the incoming verification SMS and perform verification without
//                //     user action.
//                showToast("인증코드가 전송되었습니다. 90초 이내에 입력해주세요 :)")
//                UserInfo.phoneAuthNum = credential.smsCode.toString()
//                binding.phoneAuthEtAuthNum.setText(credential.smsCode.toString())
//                binding.phoneAuthEtAuthNum.isEnabled = false
//                Handler(Looper.getMainLooper()).postDelayed({
//                    verifyPhoneNumberWithCode(credential)
//                }, 1000)
//            }
//
//            // 번호인증 실패 상태
//            override fun onVerificationFailed(e: FirebaseException) {
//                // This callback is invoked in an invalid request for verification is made,
//                // for instance if the the phone number format is not valid.
//                Log.w(TAG, "onVerificationFailed", e)
//                if (e is FirebaseAuthInvalidCredentialsException) {
//                    // Invalid request
//                } else if (e is FirebaseTooManyRequestsException) {
//                    // The SMS quota for the project has been exceeded
//                }
//                showToast("인증실패")
//            }
//
//            // 전화번호는 확인 되었으나 인증코드를 입력해야 하는 상태
//            override fun onCodeSent(
//                verificationId: String,
//                token: PhoneAuthProvider.ForceResendingToken
//            ) {
//                // The SMS verification code has been sent to the provided phone number, we
//                // now need to ask the user to enter the code and then construct a credential
//                // by combining the code with a verification ID.
//                Log.d(TAG, "onCodeSent:$verificationId")
//                // Save verification ID and resending token so we can use them later
//                storedVerificationId = verificationId // verificationId 와 전화번호인증코드 매칭해서 인증하는데 사용예정
//                resendToken = token
//            }
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        // Initialize Firebase Auth
//        auth = Firebase.auth
//        binding.vm = viewModel
//        initViewModelCallback()
//    }
//
//    private fun initViewModelCallback() {
//        with(viewModel) {
//            requestPhoneAuth.observe(this@PhoneAuthActivity, Observer { // 인증번호 요청
//                UserInfo.tel = viewModel.etPhoneNum.value.toString() // 전화번호 저장
//                if (it) {
//                    startPhoneNumberVerification(
//                        "+82" + viewModel.etPhoneNum.value.toString().substring(1)
//                    )
//                } else {
//                    showToast("전화번호를 입력해주세요")
//                }
//            })
//
//            requestResendPhoneAuth.observe(this@PhoneAuthActivity, Observer { // 인증번호 재요청
//                if (it) {
//                    resendVerificationCode(
//                        "+82" + viewModel.etPhoneNum.value.toString().substring(1)
//                        , resendToken
//                    )
//                } else {
//                    showToast("전화번호를 입력해주세요")
//                }
//            })
//
//            authComplete.observe(this@PhoneAuthActivity, Observer { // 인증완료 버튼 클릭 시
//                // 휴대폰 인증번호로 인증 및 로그인 실행
//                // onCodeSent() 에서 받은 vertificationID 와 문자메시지로 전송한 인증코드값으로 Credintial 만든 후 인증 시도
//                try {
//                    val phoneCredential =
//                        PhoneAuthProvider.getCredential(
//                            storedVerificationId,
//                            viewModel.etAuthNum.value!!
//                        )
//                    verifyPhoneNumberWithCode(phoneCredential)
//                } catch (e: Exception) {
//                    Log.d(TAG, e.toString())
//                }
//            })
//        }
//    }
//
//    // 전화번호 인증코드 요청
//    private fun startPhoneNumberVerification(phoneNumber: String) {
//        val options = PhoneAuthOptions.newBuilder(auth)
//            .setPhoneNumber(phoneNumber)       // Phone number to verify
//            .setTimeout(90L, TimeUnit.SECONDS) // Timeout and unit
//            .setActivity(this)                 // Activity (for callback binding)
//            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
//            .build()
//        PhoneAuthProvider.verifyPhoneNumber(options)
//
//        binding.phoneAuthBtnAuth.run {
//            text = "재전송"
//            setTextColor(
//                ContextCompat.getColor(
//                    this@PhoneAuthActivity, R.color.dark_gray_333333
//                )
//            )
//            background = ContextCompat.getDrawable(
//                this@PhoneAuthActivity, R.drawable.bg_btn_stroke_dark_gray_333333_radius_8dp
//            )
//        }
//    }
//
//    // 전화번호 인증코드 재요청
//    private fun resendVerificationCode(
//        phoneNumber: String,
//        token: PhoneAuthProvider.ForceResendingToken?
//    ) {
//        val optionsBuilder = PhoneAuthOptions.newBuilder(auth)
//            .setPhoneNumber(phoneNumber)       // Phone number to verify
//            .setTimeout(90L, TimeUnit.SECONDS) // Timeout and unit
//            .setActivity(this)                 // Activity (for callback binding)
//            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
//        if (token != null) {
//            optionsBuilder.setForceResendingToken(token) // callback's ForceResendingToken
//        }
//        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
//    }
//
//    // 전화번호 인증 실행 (onCodeSent() 에서 받은 vertificationID 와
//    // 문자로 받은 인증코드로 생성한 PhoneAuthCredential 사용)
//    private fun verifyPhoneNumberWithCode(phoneAuthCredential: PhoneAuthCredential) {
//        UserInfo.tel = binding.phoneAuthEtPhoneNum.text.toString()
//        if (UserInfo.tel.isNotBlank() && UserInfo.phoneAuthNum.isNotBlank() &&
//            (UserInfo.tel == binding.phoneAuthEtPhoneNum.text.toString() && UserInfo.phoneAuthNum == binding.phoneAuthEtAuthNum.text.toString())
//        ) { // 이전에  인증한 번호와 인증번호인 경우
//            showToast("인증 성공")
//            UserInfo.tel = binding.phoneAuthEtPhoneNum.text.toString()
//            startActivity(Intent(this@PhoneAuthActivity, UserInfoActivity::class.java))
//            return
//        }
//        Firebase.auth.signInWithCredential(phoneAuthCredential)
//            .addOnCompleteListener(this@PhoneAuthActivity) { task ->
//                if (task.isSuccessful) {
//                    showToast("인증 성공")
//                    UserInfo.tel = binding.phoneAuthEtPhoneNum.text.toString()
//                    binding.phoneAuthEtAuthNum.isEnabled = true
//                    startActivity(
//                        Intent(this@PhoneAuthActivity, UserInfoActivity::class.java)
//                    )
//                } else {
//                    binding.phoneAuthTvAuthNum.text =
//                        getString(R.string.auth_num_wrong_text)
//                    binding.phoneAuthTvAuthNum.setTextColor(
//                        ContextCompat.getColor(this@PhoneAuthActivity, R.color.red_FF5050)
//                    )
//                    binding.phoneAuthEtAuthNum.isEnabled = true
//                }
//            }
//    }

}