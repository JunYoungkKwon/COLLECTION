package com.eight.collection.ui.main.setting.infoedit.phonenumber

import com.eight.collection.data.remote.auth.Auth

interface ChangePhoneNumberView {
    fun onChangePhoneNumberLoading()
    fun onChangePhoneNumberSuccess(auth: Auth)
    fun onChangePhoneNumberFailure(code: Int, message: String)
}