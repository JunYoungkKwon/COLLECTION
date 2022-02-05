package com.eight.collection.data.remote.auth

import com.eight.collection.data.entities.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("app/user/register")
    fun signUp(@Body user: User): Call<AuthResponse>

    @POST("app/user/login")
    fun login(@Body user: User): Call<AuthResponse>

    @GET("app/user/duplicate-nickname")
    fun checkNickname()

//    @GET("/users/auto-login")
//    fun autoLogin(): Call<AuthResponse>
}