package com.eight.collection.ui.main.setting.infoedit.nickname

import com.eight.collection.data.remote.auth.Auth

interface ChangeNickNameView {
    fun onChangeNickNameLoading()
    fun onChangeNickNameSuccess(auth: Auth)
    fun onChangeNickNameFailure(code: Int, message: String)
}