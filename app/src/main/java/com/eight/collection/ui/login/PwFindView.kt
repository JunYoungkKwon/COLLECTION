package com.eight.collection.ui.login

import com.eight.collection.data.remote.auth.Auth

interface PwFindView {
    fun onPwFindLoading()
    fun onPwFindSuccess()
    fun onPwFindFailure(code: Int, message: String)
}