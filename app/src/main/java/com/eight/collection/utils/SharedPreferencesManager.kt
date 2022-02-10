package com.eight.collection.utils

import com.eight.collection.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.eight.collection.ApplicationClass.Companion.mSharedPreferences

fun saveJwt(jwtToken: String) {
    val editor = mSharedPreferences.edit()
    editor.putString(X_ACCESS_TOKEN, jwtToken)

    editor.apply()
}

fun saveUserId(userId: String) {
    val editor = mSharedPreferences.edit()
    editor.putString("userId", userId)

    editor.apply()
}

fun saveNickName(nickName: String) {
    val editor = mSharedPreferences.edit()
    editor.putString("nickName", nickName)

    editor.apply()
}

fun getJwt(): String? = mSharedPreferences.getString(X_ACCESS_TOKEN, null)

fun getUserId(): String? = mSharedPreferences.getString("userId", null)

fun getNickName(): String? = mSharedPreferences.getString("nickName", null)
