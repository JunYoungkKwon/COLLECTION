package com.eight.collection.ui.main.setting.infoedit.password

import com.eight.collection.data.remote.auth.Auth

interface ChangePwView {
    fun onChangePwLoading()
    fun onChangePwSuccess(auth: Auth)
    fun onChangePwFailure(code: Int, message: String)
}