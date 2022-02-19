package com.eight.collection.data.remote.weekly

import android.util.Log
import com.eight.collection.ApplicationClass.Companion.TAG
import com.eight.collection.ApplicationClass.Companion.retrofit
import com.eight.collection.data.entities.User
import com.eight.collection.ui.login.LoginView
import com.eight.collection.ui.main.month.MonthView
import com.eight.collection.ui.main.setting.infoedit.account.DeleteAccountView
import com.eight.collection.ui.main.setting.infoedit.nickname.ChangeNickNameView
import com.eight.collection.ui.main.setting.infoedit.password.ChangePwView
import com.eight.collection.ui.main.setting.infoedit.phonenumber.ChangePhoneNumberView
import com.eight.collection.ui.main.week.WeeklyView
import com.eight.collection.ui.signup.CheckIdView
import com.eight.collection.ui.signup.CheckNicknameView
import com.eight.collection.ui.signup.SignUpView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object WeeklyService {
    fun getWeek(weeklyView: WeeklyView) {
        val weeklyService = retrofit.create(WeeklyRetrofitInterface::class.java)

        weeklyView.onWeeklyLoading()

        weeklyService.getWeek().enqueue(object : Callback<WeeklyResponse> {
            override fun onResponse(call: Call<WeeklyResponse>, response: Response<WeeklyResponse>) {

                val resp = response.body()!!

                when(resp.code){
                    1005 -> weeklyView.onWeeklySuccess(resp.result!!.weekly)
                    else -> weeklyView.onWeeklyFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<WeeklyResponse>, t: Throwable) {
                Log.d("$TAG/API-ERROR", t.message.toString())

                weeklyView.onWeeklyFailure(400, "네트워크 오류가 발생했습니다.")
            }
        })
    }
}