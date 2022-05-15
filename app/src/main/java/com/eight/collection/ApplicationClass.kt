package com.eight.collection

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.eight.collection.config.XAccessTokenInterceptor
import com.eight.collection.utils.SecretCode
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass : Application() {
    companion object{
        const val X_ACCESS_TOKEN: String = "X-ACCESS-TOKEN"         // JWT Token Key
        const val TAG: String = "COLLECTION-APP"                      // Log, SharedPreference
        const val APP_DATABASE = "$TAG-DB"

        const val DEV_URL: String = "https://www.collection-dev.shop/"     // 테스트 서버 주소
        const val PROD_URL: String = "https://collection-ootd.shop/"    // 실서버 주소
        const val BASE_URL: String = PROD_URL

        lateinit var mSharedPreferences: SharedPreferences
        lateinit var retrofit: Retrofit

        var CODE: String = ""

        fun getCode(): String {
            return CODE
        }

        fun saveCode(code: String) {
            this.CODE = code
        }
    }





    override fun onCreate() {
        super.onCreate()

        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(30000, TimeUnit.MILLISECONDS)
            .connectTimeout(30000, TimeUnit.MILLISECONDS)
            .addNetworkInterceptor(XAccessTokenInterceptor()) // JWT 자동 헤더 전송
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mSharedPreferences = applicationContext.getSharedPreferences(TAG, Context.MODE_PRIVATE)
    }
}