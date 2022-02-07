package com.eight.collection.data.remote.auth

import android.util.Log
import com.eight.collection.ApplicationClass.Companion.TAG
import com.eight.collection.ApplicationClass.Companion.retrofit
import com.eight.collection.data.entities.User
import com.eight.collection.ui.login.LoginView
import com.eight.collection.ui.signup.CheckIdView
import com.eight.collection.ui.signup.CheckNicknameView
import com.eight.collection.ui.signup.SignUpView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AuthService {
    fun signUp(signUpView: SignUpView, user: User) {
        val authService = retrofit.create(AuthRetrofitInterface::class.java)

        signUpView.onSignUpLoading()

        authService.signUp(user).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {

                val resp = response.body()!!

                when(resp.code){
                    1000 -> signUpView.onSignUpSuccess()
                    else -> signUpView.onSignUpFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("$TAG/API-ERROR", t.message.toString())

                signUpView.onSignUpFailure(400, "네트워크 오류가 발생했습니다.")
            }
        })
    }

    fun checkNickname(checkNicknameView: CheckNicknameView, nickname: String) {
        val authService = retrofit.create(AuthRetrofitInterface::class.java)

        checkNicknameView.onCheckNicknameLoading()

        authService.checkNickname(nickname).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                val resp = response.body()!!

                when(resp.code){
                    1022 -> checkNicknameView.onCheckNicknameSuccess()
                    else -> checkNicknameView.onCheckNicknameFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("$TAG/API-ERROR", t.message.toString())

                checkNicknameView.onCheckNicknameFailure(400, "네트워크 오류가 발생했습니다.")
            }
        })
    }

    fun checkId(checkIdView: CheckIdView, id: String) {
        val authService = retrofit.create(AuthRetrofitInterface::class.java)

        checkIdView.onCheckIdLoading()

        authService.checkId(id).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                val resp = response.body()!!

                when(resp.code){
                    1021 -> checkIdView.onCheckIdSuccess()
                    else -> checkIdView.onCheckIdFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("$TAG/API-ERROR", t.message.toString())

                checkIdView.onCheckIdFailure(400, "네트워크 오류가 발생했습니다.")
            }
        })
    }


    fun login(loginView: LoginView, user: User) {
        val authService = retrofit.create(AuthRetrofitInterface::class.java)

        loginView.onLoginLoading()

        authService.login(user).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                val resp = response.body()!!

                when(resp.code){
                    1001 -> loginView.onLoginSuccess(resp.result!!)
                    else -> loginView.onLoginFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("$TAG/API-ERROR", t.message.toString())

                loginView.onLoginFailure(400, "네트워크 오류가 발생했습니다.")
            }
        })
    }

//    fun autoLogin(splashView: SplashView) {
//        val authService = retrofit.create(AuthRetrofitInterface::class.java)
//
//        splashView.onAutoLoginLoading()
//
//        authService.autoLogin().enqueue(object : Callback<AuthResponse> {
//            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
//                val resp = response.body()!!
//
//                when(resp.code){
//                    1000 -> splashView.onAutoLoginSuccess()
//                    else -> splashView.onAutoLoginFailure(resp.code, resp.message)
//                }
//            }
//
//            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
//                Log.d("$TAG/API-ERROR", t.message.toString())
//
//                splashView.onAutoLoginFailure(400, "네트워크 오류가 발생했습니다.")
//            }
//        })
//    }
}