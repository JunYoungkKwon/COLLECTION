package com.eight.collection.data.remote.finish

import android.util.Log
import com.eight.collection.ApplicationClass.Companion.TAG
import com.eight.collection.ApplicationClass.Companion.retrofit
import com.eight.collection.data.entities.User
import com.eight.collection.ui.finish.FinishView
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
import java.time.LocalDate
import java.util.*

object FinishService {

    fun getFinish(finishView: FinishView, date: String) {
        val finishService = retrofit.create(FinishRetrofitInterface::class.java)

        finishView.onFinishLoading()

        finishService.getFinish(date).enqueue(object : Callback<FinishResponse> {
            override fun onResponse(call: Call<FinishResponse>, response: Response<FinishResponse>) {
                val resp = response.body()!!

                when(resp.code){
                    1014 -> finishView.onFinishSuccess(resp.result!!)
                    else -> finishView.onFinishFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<FinishResponse>, t: Throwable) {
                Log.d("$TAG/API-ERROR", t.message.toString())

                finishView.onFinishFailure(400, "네트워크 오류가 발생했습니다.")
            }
        })
    }
}