package com.eight.collection.data.remote.write

import android.util.Log
import com.eight.collection.ApplicationClass.Companion.TAG
import com.eight.collection.ApplicationClass.Companion.retrofit
import com.eight.collection.data.entities.Write.Block
import com.eight.collection.data.entities.Write.Content
import com.eight.collection.data.entities.Write.Write
import com.eight.collection.ui.writing.AddBlockView
import com.eight.collection.ui.writing.WriteView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object WriteService {
    fun write(writeView: WriteView, write : Write) {
        val writeService = retrofit.create(WriteRetrofitInterface::class.java)

        writeView.onWriteLoading()

        //이 부분 오류
        writeService.write(write.mode,write).enqueue(object : Callback<WriteResponse> {
            override fun onResponse(call: Call<WriteResponse>, response: Response<WriteResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1008 -> {
                        Log.d("Success","등록성공")
                    }
                    else -> {
                        writeView.onWriteFailure(resp.code,resp.message)
                        Log.d("fail",resp.message)
                    }
                }
            }

            override fun onFailure(call: Call<WriteResponse>, t: Throwable) {
                Log.d("$TAG/API-ERROR", t.message.toString())
                writeView.onWriteFailure(400, "네트워크 오류가 발생했습니다.")
            }
        })
    }
}