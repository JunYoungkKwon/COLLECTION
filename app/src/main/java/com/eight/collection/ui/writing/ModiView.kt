package com.eight.collection.ui.writing

import com.eight.collection.data.remote.modi.ModiResult


interface ModiView {
    fun onModiLoading()
    fun onModiSuccess(modiresult : ModiResult)
    fun onModiFailure(code: Int, message: String)
}