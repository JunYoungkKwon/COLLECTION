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

fun saveChangeNickName(nickName: String) {
    val editor = mSharedPreferences.edit()
    editor.putString("nickName", nickName)

    editor.apply()
}

fun saveName(name: String) {
    val editor = mSharedPreferences.edit()
    editor.putString("name", name)

    editor.apply()
}

fun saveIntroduceIs(getIntroduceis : Boolean) {
    val editor = mSharedPreferences.edit()
    editor.putBoolean("getIntroduceIs", getIntroduceis)

    editor.apply()
}

fun savePWWC(PWWC : Int){
    val editor = mSharedPreferences.edit()
    editor.putInt("PWWC", PWWC)

    editor.apply()
}

fun saveContent(content : String){
    val editor = mSharedPreferences.edit()
    editor.putString("content", content)

    editor.apply()
}


fun getJwt(): String? = mSharedPreferences.getString (X_ACCESS_TOKEN, null)

fun getUserId(): String? = mSharedPreferences.getString("userId", null)

fun getNickName(): String? = mSharedPreferences.getString("nickName", null)

fun getName(): String? = mSharedPreferences.getString("name", null)

fun getPWWC(): Int =  mSharedPreferences.getInt("PWWC", -1)

fun getIntroduceIs(): Boolean = mSharedPreferences.getBoolean("getIntroduceIs", false)

fun getContent() : String? = mSharedPreferences.getString("content", null)
