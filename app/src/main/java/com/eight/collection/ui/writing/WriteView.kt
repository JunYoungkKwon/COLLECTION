package com.eight.collection.ui.writing

interface WriteView {
    fun onWriteLoading()
    fun onWriteSuccess()
    fun onWriteFailure(code: Int, message: String)
}