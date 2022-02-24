package com.eight.collection.data.remote.auth

import android.util.Log
import com.eight.collection.ApplicationClass.Companion.TAG
import com.eight.collection.ApplicationClass.Companion.retrofit
import com.eight.collection.data.entities.User
import com.eight.collection.ui.login.LoginView
import com.eight.collection.ui.main.setting.infoedit.account.DeleteAccountView
import com.eight.collection.ui.main.setting.infoedit.nickname.ChangeNickNameView
import com.eight.collection.ui.main.setting.infoedit.password.ChangePwView
import com.eight.collection.ui.main.setting.infoedit.phonenumber.ChangePhoneNumberView
import com.eight.collection.ui.signup.CheckIdView
import com.eight.collection.ui.signup.CheckNicknameView
import com.eight.collection.ui.signup.SignUpView
import com.eight.collection.ui.writing.ReceiveS3URLView
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

    fun changeNickName(changeNickNameView: ChangeNickNameView, user: User) {
        val authService = retrofit.create(AuthRetrofitInterface::class.java)

        changeNickNameView.onChangeNickNameLoading()

        authService.changeNickName(user).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                val resp = response.body()!!

                when(resp.code){
                    1002 -> changeNickNameView.onChangeNickNameSuccess(resp.result!!)
                    else -> changeNickNameView.onChangeNickNameFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("$TAG/API-ERROR", t.message.toString())

                changeNickNameView.onChangeNickNameFailure(400, "네트워크 오류가 발생했습니다.")
            }
        })
    }

    fun changePhoneNumber(changePhoneNumberView: ChangePhoneNumberView, user: User) {
        val authService = retrofit.create(AuthRetrofitInterface::class.java)

        changePhoneNumberView.onChangePhoneNumberLoading()

        authService.changePhoneNumber(user).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                val resp = response.body()!!

                when(resp.code){
                    1002 -> changePhoneNumberView.onChangePhoneNumberSuccess(resp.result!!)
                    else -> changePhoneNumberView.onChangePhoneNumberFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("$TAG/API-ERROR", t.message.toString())

                changePhoneNumberView.onChangePhoneNumberFailure(400, "네트워크 오류가 발생했습니다.")
            }
        })
    }

    fun changePw(changePwView: ChangePwView, user: User) {
        val authService = retrofit.create(AuthRetrofitInterface::class.java)

        changePwView.onChangePwLoading()

        authService.changePw(user).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                val resp = response.body()!!

                when(resp.code){
                    1002 -> changePwView.onChangePwSuccess(resp.result!!)
                    else -> changePwView.onChangePwFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("$TAG/API-ERROR", t.message.toString())

                changePwView.onChangePwFailure(400, "네트워크 오류가 발생했습니다.")
            }
        })
    }

    fun deleteAccount(deleteAccountView: DeleteAccountView, user: User) {
        val authService = retrofit.create(AuthRetrofitInterface::class.java)

        deleteAccountView.onDeleteAccountLoading()

        authService.deleteAccount(user).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                val resp = response.body()!!

                when(resp.code){
                    1003 -> deleteAccountView.onDeleteAccountSuccess()
                    else -> deleteAccountView.onDeleteAccountFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("$TAG/API-ERROR", t.message.toString())

                deleteAccountView.onDeleteAccountFailure(400, "네트워크 오류가 발생했습니다.")
            }
        })
    }

}