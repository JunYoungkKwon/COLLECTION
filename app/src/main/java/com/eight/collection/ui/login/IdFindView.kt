package com.eight.collection.ui.login

import com.eight.collection.data.remote.auth.Auth

interface IdFindView {
    fun onIdFindLoading()
    fun onIdFindSuccess(auth: Auth)
    fun onIdFindFailure(code: Int, message: String)
}