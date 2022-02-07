package com.eight.collection.ui.signup

interface CheckIdView {
    fun onCheckIdLoading()
    fun onCheckIdSuccess()
    fun onCheckIdFailure(code: Int, message: String)
}