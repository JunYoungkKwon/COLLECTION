package com.eight.collection.data.remote.getaddedblock

import android.util.Log
import com.eight.collection.ApplicationClass.Companion.TAG
import com.eight.collection.ApplicationClass.Companion.retrofit
import com.eight.collection.data.remote.auth.AuthResponse
import com.eight.collection.ui.writing.GetAddedBlockView
import com.eight.collection.ui.writing.ReceiveS3URLView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GetAddedBlockService {
    fun getAddedBlock(getAddedBlockView: GetAddedBlockView) {
        val getAddedBlockService = retrofit.create(GetAddedBlockRetrofitInterface::class.java)

        getAddedBlockView.onGetAddedBlockLoading()

        getAddedBlockService.getAddedBlock().enqueue(object : Callback<GetAddedBlockResponse> {
            override fun onResponse(call: Call<GetAddedBlockResponse>, response: Response<GetAddedBlockResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1028 -> getAddedBlockView.onGetAddedBlockSuccess(resp.result!!)
                    else -> getAddedBlockView.onGetAddedBlockFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<GetAddedBlockResponse>, t: Throwable) {
                Log.d("$TAG/API-ERROR", t.message.toString())
                getAddedBlockView.onGetAddedBlockFailure(400, "네트워크 오류가 발생했습니다.")
            }
        })
    }
}