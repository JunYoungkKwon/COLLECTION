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

fun setColor(color: String) {
    val editor = mSharedPreferences.edit()
    editor.putString("color", color)

    editor.apply()
}

fun setSelectedId(Id : Int) {
    val editor = mSharedPreferences.edit()
    editor.putInt("selectedId", Id)

    editor.apply()
}


fun removeColor() {
    val editor = mSharedPreferences.edit()
    editor.remove("color")

    editor.apply()
}

fun removeSelectedId() {
    val editor = mSharedPreferences.edit()
    editor.remove("selectedId")

    editor.apply()
}



fun getJwt(): String? = mSharedPreferences.getString(X_ACCESS_TOKEN, null)

fun getUserId(): String? = mSharedPreferences.getString("userId", null)

fun getNickName(): String? = mSharedPreferences.getString("nickName", null)

fun getColor(): String? = mSharedPreferences.getString("color", null)

fun getSelectedId(): Int = mSharedPreferences.getInt("selectedId", -1)
