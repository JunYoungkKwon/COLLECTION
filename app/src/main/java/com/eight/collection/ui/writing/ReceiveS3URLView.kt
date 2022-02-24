package com.eight.collection.ui.writing

interface ReceiveS3URLView {
    fun onReceiveS3URLLoading()
    fun onReceiveS3URLSuccess(url: String)
    fun onReceiveS3URLFailure(code: Int, message: String)
}