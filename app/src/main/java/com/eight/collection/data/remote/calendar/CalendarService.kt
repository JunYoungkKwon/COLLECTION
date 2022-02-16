package com.eight.collection.data.remote.calendar

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
import com.eight.collection.ui.signup.CheckIdView
import com.eight.collection.ui.signup.CheckNicknameView
import com.eight.collection.ui.signup.SignUpView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CalendarService {
    fun getMonth(monthView: MonthView) {
        val calendarService = retrofit.create(CalendarRetrofitInterface::class.java)

        monthView.onMonthLoading()

        calendarService.getMonth().enqueue(object : Callback<CalendarResponse> {
            override fun onResponse(call: Call<CalendarResponse>, response: Response<CalendarResponse>) {

                val resp = response.body()!!

                when(resp.code){
                    1004 -> monthView.onMonthSuccess(resp.result!!.monthly)
                    else -> monthView.onMonthFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<CalendarResponse>, t: Throwable) {
                Log.d("$TAG/API-ERROR", t.message.toString())

                monthView.onMonthFailure(400, "네트워크 오류가 발생했습니다.")
            }
        })
    }
}