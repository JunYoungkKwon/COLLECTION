package com.eight.collection.data.remote.match

import android.util.Log
import com.eight.collection.ApplicationClass.Companion.TAG
import com.eight.collection.ApplicationClass.Companion.retrofit
import com.eight.collection.data.entities.Write.Content
import com.eight.collection.ui.main.match.DeleteTagView
import com.eight.collection.ui.main.match.LastTagView
import com.eight.collection.ui.main.match.MatchView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MatchService {
    fun getMatch(matchView: MatchView, PWWC: Int, keyword1: String, keyword2: String, color1: String, color2: String, startAt: String, endAt: String) {
        val matchService = retrofit.create(MatchRetrofitInterface::class.java)

        matchView.onMatchLoading()

        matchService.getMatch(PWWC,keyword1, keyword2, color1, color2, startAt, endAt).enqueue(object : Callback<MatchResponse> {
            override fun onResponse(call: Call<MatchResponse>, response: Response<MatchResponse>) {

                val resp = response.body()!!

                when(resp.code){
                    1020 -> matchView.onMatchSuccess(resp.result!!.match)
                    else -> matchView.onMatchFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<MatchResponse>, t: Throwable) {
                Log.d("$TAG/API-ERROR", t.message.toString())

                matchView.onMatchFailure(400, "네트워크 오류가 발생했습니다.")
            }
        })
    }

    fun getLastTag(lastTagView: LastTagView, PWWC: Int) {
        val lastTagService = retrofit.create(MatchRetrofitInterface::class.java)

        lastTagView.onLastTagLoading()

        lastTagService.getLastTag(PWWC).enqueue(object : Callback<LastTagResponse> {
            override fun onResponse(call: Call<LastTagResponse>, response: Response<LastTagResponse>) {

                val resp = response.body()!!

                when(resp.code){
                    1017 -> lastTagView.onLastTagSuccess(resp.result!!.lastTag)
                    else -> lastTagView.onLastTagFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<LastTagResponse>, t: Throwable) {
                Log.d("$TAG/API-ERROR", t.message.toString())

                lastTagView.onLastTagFailure(400, "네트워크 오류가 발생했습니다.")
            }
        })
    }

    fun deleteTag(deleteTagView: DeleteTagView, PWWC: Int, type : Int, content : Content){
        val deleteTagService = retrofit.create(MatchRetrofitInterface::class.java)

        deleteTagView.onDeleteTagLoading()


        if(type == 1) {
            deleteTagService.deleteBlock(PWWC, type, content)
                .enqueue(object : Callback<DeleteTagResponse> {
                    override fun onResponse(
                        call: Call<DeleteTagResponse>,
                        response: Response<DeleteTagResponse>
                    ) {

                        val resp = response.body()!!

                        when (resp.code) {
                            1018 -> deleteTagView.onDeleteTagSuccess()
                            else -> deleteTagView.onDeleteTagFailure(resp.code, resp.message)
                        }
                    }

                    override fun onFailure(call: Call<DeleteTagResponse>, t: Throwable) {
                        Log.d("$TAG/API-ERROR", t.message.toString())

                        deleteTagView.onDeleteTagFailure(400, "네트워크 오류가 발생했습니다.")
                    }
                })
        }
        else {
            deleteTagService.deleteBlock(PWWC, type, content)
                .enqueue(object : Callback<DeleteTagResponse> {
                    override fun onResponse(
                        call: Call<DeleteTagResponse>,
                        response: Response<DeleteTagResponse>
                    ) {

                        val resp = response.body()!!

                        when (resp.code) {
                            1018 -> deleteTagView.onDeleteTagSuccess()
                            else -> deleteTagView.onDeleteTagFailure(resp.code, resp.message)
                        }
                    }

                    override fun onFailure(call: Call<DeleteTagResponse>, t: Throwable) {
                        Log.d("$TAG/API-ERROR", t.message.toString())

                        deleteTagView.onDeleteTagFailure(400, t.message.toString())
                    }
                })
        }

    }
}