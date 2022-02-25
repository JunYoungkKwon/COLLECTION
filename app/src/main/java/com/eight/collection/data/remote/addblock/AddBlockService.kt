package com.eight.collection.data.remote.addblock

import android.util.Log
import com.eight.collection.ApplicationClass.Companion.TAG
import com.eight.collection.ApplicationClass.Companion.retrofit
import com.eight.collection.data.entities.Write.Block
import com.eight.collection.ui.writing.AddBlockView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AddBlockService {
    fun addBlock(addBlockView: AddBlockView, block : Block) {
        val addBlockService = retrofit.create(AddBlockRetrofitInterface::class.java)

        addBlockView.onAddBlockLoading()

        //이 부분 오류
        addBlockService.addBlock(block.content,block.clothes,block.pww).enqueue(object : Callback<AddBlockResponse> {
            override fun onResponse(call: Call<AddBlockResponse>, response: Response<AddBlockResponse>) {

                //여기서부터 오류
                val resp = response.body()!!

                when(resp.code){
                    1007 -> {
                        addBlockView.onAddBlockSuccess()
                        Log.d("Tag","Success")
                    }
                    else -> {
                        addBlockView.onAddBlockFailure(resp.code, resp.message)
                        Log.d("Tag","Fail")
                    }
                }
            }

            override fun onFailure(call: Call<AddBlockResponse>, t: Throwable) {
                Log.d("$TAG/API-ERROR", t.message.toString())
                addBlockView.onAddBlockFailure(400, "네트워크 오류가 발생했습니다.")
            }
        })
    }
}