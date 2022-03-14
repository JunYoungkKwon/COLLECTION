package com.eight.collection.data.remote.mylook

import android.util.Log
import com.eight.collection.ApplicationClass.Companion.TAG
import com.eight.collection.ApplicationClass.Companion.retrofit
import com.eight.collection.data.remote.calendar.CalendarRetrofitInterface
import com.eight.collection.ui.login.LoginView
import com.eight.collection.ui.main.month.MonthView
import com.eight.collection.ui.main.mylook.*
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

object MyLookService {
    fun getMainMyLook(myLookView: MyLookView, lookpoint:Int) {
        val mainMyLookService = retrofit.create(MyLookRetrofitInterface::class.java)

        myLookView.onMyLookLoading()

        mainMyLookService.getMainMyLook(lookpoint).enqueue(object : Callback<MyLookResponse> {
            override fun onResponse(call: Call<MyLookResponse>, response: Response<MyLookResponse>) {

                val resp = response.body()!!

                when(resp.code){
                    1015 -> myLookView.onMyLookSuccess(resp.result!!)
                    else -> myLookView.onMyLookFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<MyLookResponse>, t: Throwable) {
                Log.d("$TAG/API-ERROR", t.message.toString())

                myLookView.onMyLookFailure(400, "네트워크 오류가 발생했습니다.")
            }
        })
    }

    fun getMain2MyLook(myLook2View: MyLook2View, lookpoint:Int) {
        val mainMyLookService = retrofit.create(MyLookRetrofitInterface::class.java)

        myLook2View.onMyLook2Loading()

        mainMyLookService.getMainMyLook(lookpoint).enqueue(object : Callback<MyLookResponse> {
            override fun onResponse(call: Call<MyLookResponse>, response: Response<MyLookResponse>) {

                val resp = response.body()!!

                when(resp.code){
                    1015 -> myLook2View.onMyLook2Success(resp.result!!)
                    else -> myLook2View.onMyLook2Failure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<MyLookResponse>, t: Throwable) {
                Log.d("$TAG/API-ERROR", t.message.toString())

                myLook2View.onMyLook2Failure(400, "네트워크 오류가 발생했습니다.")
            }
        })
    }

    fun getMain3MyLook(myLook3View: MyLook3View, lookpoint:Int) {
        val mainMyLookService = retrofit.create(MyLookRetrofitInterface::class.java)

        myLook3View.onMyLook3Loading()

        mainMyLookService.getMainMyLook(lookpoint).enqueue(object : Callback<MyLookResponse> {
            override fun onResponse(call: Call<MyLookResponse>, response: Response<MyLookResponse>) {

                val resp = response.body()!!

                when(resp.code){
                    1015 -> myLook3View.onMyLook3Success(resp.result!!)
                    else -> myLook3View.onMyLook3Failure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<MyLookResponse>, t: Throwable) {
                Log.d("$TAG/API-ERROR", t.message.toString())

                myLook3View.onMyLook3Failure(400, "네트워크 오류가 발생했습니다.")
            }
        })
    }

    fun getMain4MyLook(myLook4View: MyLook4View, lookpoint:Int) {
        val mainMyLookService = retrofit.create(MyLookRetrofitInterface::class.java)

        myLook4View.onMyLook4Loading()

        mainMyLookService.getMainMyLook(lookpoint).enqueue(object : Callback<MyLookResponse> {
            override fun onResponse(call: Call<MyLookResponse>, response: Response<MyLookResponse>) {

                val resp = response.body()!!

                when(resp.code){
                    1015 -> myLook4View.onMyLook4Success(resp.result!!)
                    else -> myLook4View.onMyLook4Failure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<MyLookResponse>, t: Throwable) {
                Log.d("$TAG/API-ERROR", t.message.toString())

                myLook4View.onMyLook4Failure(400, "네트워크 오류가 발생했습니다.")
            }
        })
    }

    fun getMain5MyLook(myLook5View: MyLook5View, lookpoint:Int) {
        val mainMyLookService = retrofit.create(MyLookRetrofitInterface::class.java)

        myLook5View.onMyLook5Loading()

        mainMyLookService.getMainMyLook(lookpoint).enqueue(object : Callback<MyLookResponse> {
            override fun onResponse(call: Call<MyLookResponse>, response: Response<MyLookResponse>) {

                val resp = response.body()!!

                when(resp.code){
                    1015 -> myLook5View.onMyLook5Success(resp.result!!)
                    else -> myLook5View.onMyLook5Failure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<MyLookResponse>, t: Throwable) {
                Log.d("$TAG/API-ERROR", t.message.toString())

                myLook5View.onMyLook5Failure(400, "네트워크 오류가 발생했습니다.")
            }
        })
    }

    fun getDetailMyLook(myLookDetailView: MyLookDetailView, lookpoint:Int) {
        val datailMyLookService = retrofit.create(MyLookRetrofitInterface::class.java)

        myLookDetailView.onMyLookDetailLoading()

        datailMyLookService.getDetailMyLook(lookpoint).enqueue(object : Callback<MyLookResponse> {
            override fun onResponse(call: Call<MyLookResponse>, response: Response<MyLookResponse>) {

                val resp = response.body()!!

                when(resp.code){
                    1016 -> myLookDetailView.onMyLookDetailSuccess(resp.result!!)
                    else -> myLookDetailView.onMyLookDetailFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<MyLookResponse>, t: Throwable) {
                Log.d("$TAG/API-ERROR", t.message.toString())

                myLookDetailView.onMyLookDetailFailure(400, "네트워크 오류가 발생했습니다.")
            }
        })
    }
}