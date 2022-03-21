package com.eight.collection.data.remote.match

import android.util.Log
import com.eight.collection.ApplicationClass.Companion.TAG
import com.eight.collection.ApplicationClass.Companion.retrofit
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
}