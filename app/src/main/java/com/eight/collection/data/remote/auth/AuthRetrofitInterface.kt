package com.eight.collection.data.remote.auth

import com.eight.collection.data.entities.User
import okhttp3.internal.http.hasBody
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

    @PATCH("app/user/modi-nickname")
    fun changeNickName(@Body user: User): Call<AuthResponse>

    @PATCH("app/user/modi-phone")
    fun changePhoneNumber(@Body user: User): Call<AuthResponse>

    @PATCH("app/user/modi-password")
    fun changePw(@Body user: User): Call<AuthResponse>

    @PATCH("app/user/unregister")
    fun deleteAccount(@Body user: User): Call<AuthResponse>

    @GET("app/user/autoLogin")
    fun autoLogin(): Call<AuthResponse>
}