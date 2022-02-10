package com.eight.collection.data.remote.auth

import com.eight.collection.data.entities.User
import retrofit2.Call
import retrofit2.http.*

interface AuthRetrofitInterface {
    @POST("app/user/register")
    fun signUp(@Body user: User): Call<AuthResponse>

    @POST("app/user/login")
    fun login(@Body user: User): Call<AuthResponse>

    @GET("app/user/check-nickname")
    fun checkNickname(@Query("nickname") nickname : String): Call<AuthResponse>

    @GET("app/user/duplicate-id")
    fun checkId(@Query("ID") id : String): Call<AuthResponse>

    @PATCH("app/user/modi-nickname/:userIdx")
    fun changeNickName(@Path("userIdx") userIdx:Int, @Body user: User): Call<AuthResponse>

//    @GET("/users/auto-login")
//    fun autoLogin(): Call<AuthResponse>
}