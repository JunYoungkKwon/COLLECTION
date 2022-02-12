package com.eight.collection.ui.main.setting.infoedit.account

import com.eight.collection.data.remote.auth.Auth

interface DeleteAccountView {
    fun onDeleteAccountLoading()
    fun onDeleteAccountSuccess()
    fun onDeleteAccountFailure(code: Int, message: String)
}