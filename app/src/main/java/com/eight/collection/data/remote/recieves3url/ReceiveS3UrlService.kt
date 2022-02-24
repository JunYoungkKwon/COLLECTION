package com.eight.collection.data.remote.recieves3url

import android.util.Log
import com.eight.collection.ApplicationClass.Companion.TAG
import com.eight.collection.ApplicationClass.Companion.retrofit
import com.eight.collection.data.remote.auth.AuthResponse
import com.eight.collection.ui.writing.ReceiveS3URLView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ReceiveS3UrlService {
    fun receiveS3url(receiveS3UrlView: ReceiveS3URLView) {
        val receiveS3UrlService = retrofit.create(ReceiveS3UrlRetrofitInterface::class.java)

        receiveS3UrlView.onReceiveS3URLLoading()

        receiveS3UrlService.receiveS3Url().enqueue(object : Callback<ReceiveS3UrlResponse> {
            override fun onResponse(call: Call<ReceiveS3UrlResponse>, response: Response<ReceiveS3UrlResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1027 -> receiveS3UrlView.onReceiveS3URLSuccess(resp.result!!.presignedurl)
                    else -> receiveS3UrlView.onReceiveS3URLFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<ReceiveS3UrlResponse>, t: Throwable) {
                Log.d("$TAG/API-ERROR", t.message.toString())
                receiveS3UrlView.onReceiveS3URLFailure(400, "네트워크 오류가 발생했습니다.")
            }
        })
    }
}