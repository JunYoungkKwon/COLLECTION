package com.eight.collection.data.remote.setting

import android.util.Log
import com.eight.collection.ApplicationClass.Companion.TAG
import com.eight.collection.ApplicationClass.Companion.retrofit
import com.eight.collection.data.entities.User
import com.eight.collection.ui.login.LoginView
import com.eight.collection.ui.main.setting.infoedit.account.DeleteAccountView
import com.eight.collection.ui.main.setting.infoedit.nickname.ChangeNickNameView
import com.eight.collection.ui.main.setting.infoedit.password.ChangePwView
import com.eight.collection.ui.main.setting.infoedit.phonenumber.ChangePhoneNumberView
import com.eight.collection.ui.main.week.DeleteView
import com.eight.collection.ui.signup.CheckIdView
import com.eight.collection.ui.signup.CheckNicknameView
import com.eight.collection.ui.signup.SignUpView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object SettingService {
    fun deleteOOTD(deleteView: DeleteView, date: String) {
        val settingService = retrofit.create(SettingRetrofitInterface::class.java)

        deleteView.onDeleteLoading()

        settingService.deleteOOTD(date).enqueue(object : Callback<SettingResponse> {
            override fun onResponse(call: Call<SettingResponse>, response: Response<SettingResponse>) {
                val resp = response.body()!!

                when(resp.code){
                    1013 -> deleteView.onDeleteSuccess()
                    else -> deleteView.onDeleteFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<SettingResponse>, t: Throwable) {
                Log.d("$TAG/API-ERROR", t.message.toString())

                deleteView.onDeleteFailure(400, "네트워크 오류가 발생했습니다.")
            }
        })
    }
}