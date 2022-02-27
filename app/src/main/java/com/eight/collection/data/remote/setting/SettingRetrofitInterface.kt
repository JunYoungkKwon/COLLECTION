package com.eight.collection.data.remote.setting

import com.eight.collection.data.entities.User
import retrofit2.Call
import retrofit2.http.*

interface SettingRetrofitInterface {

    @PATCH("app/ootd/deletion")
    fun deleteOOTD(@Query("date") date : String): Call<SettingResponse>

}