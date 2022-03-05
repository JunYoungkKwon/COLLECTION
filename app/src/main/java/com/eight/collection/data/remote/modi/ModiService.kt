package com.eight.collection.data.remote.modi

import android.util.Log
import com.eight.collection.ApplicationClass.Companion.TAG
import com.eight.collection.ApplicationClass.Companion.retrofit
import com.eight.collection.data.remote.auth.AuthResponse
import com.eight.collection.ui.writing.GetAddedBlockView
import com.eight.collection.ui.writing.ModiView
import com.eight.collection.ui.writing.ReceiveS3URLView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ModiService {
    fun modi(modiView: ModiView, date: String) {
        val modiService = retrofit.create(ModiRetrofitInterface::class.java)

        modiView.onModiLoading()

        modiService.modi(date).enqueue(object : Callback<ModiResponse> {
            override fun onResponse(call: Call<ModiResponse>, response: Response<ModiResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1012 -> modiView.onModiSuccess(resp.result!!)
                    else -> modiView.onModiFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<ModiResponse>, t: Throwable) {
                Log.d("$TAG/API-ERROR", t.message.toString())
                modiView.onModiFailure(400, "네트워크 오류가 발생했습니다.")
            }
        })
    }
}