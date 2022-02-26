package com.eight.collection.data.remote.deleteblock

import android.util.Log
import com.eight.collection.ApplicationClass.Companion.TAG
import com.eight.collection.ApplicationClass.Companion.retrofit
import com.eight.collection.data.entities.Write.Block
import com.eight.collection.data.entities.Write.Content
import com.eight.collection.ui.writing.DeleteBlockView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DeleteBlockService {
    fun deleteBlock(deleteBlockView: DeleteBlockView, block : Block) {
        val deleteBlockService = retrofit.create(DeleteBlockRetrofitInterface::class.java)

        deleteBlockView.onDeleteBlockLoading()

        //이 부분 오류
        deleteBlockService.deleteBlock(block.clothes,block.pww,Content(block.content)).enqueue(object : Callback<DeleteBlockResponse> {
            override fun onResponse(call: Call<DeleteBlockResponse>, response: Response<DeleteBlockResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1024 -> {
                        deleteBlockView.onDeleteBlockSuccess()
                        }
                    else -> {
                        deleteBlockView.onDeleteBlockFailure(resp.code, resp.message)
                    }
                }
            }

            override fun onFailure(call: Call<DeleteBlockResponse>, t: Throwable) {
                Log.d("$TAG/API-ERROR", t.message.toString())
                deleteBlockView.onDeleteBlockFailure(400, "네트워크 오류가 발생했습니다.")
            }
        })
    }
}