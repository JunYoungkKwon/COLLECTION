package com.eight.collection.ui.signup

interface CheckNicknameView {
    fun onCheckNicknameLoading()
    fun onCheckNicknameSuccess()
    fun onCheckNicknameFailure(code: Int, message: String)
}