package com.eight.collection.ui.main.mylook

import com.eight.collection.data.entities.Calendar
import com.eight.collection.data.entities.MyLook
import com.eight.collection.data.remote.auth.Auth
import com.eight.collection.data.remote.mylook.MyLookResult

interface MyLookView {
    fun onMyLookLoading()
    fun onMyLookSuccess(myLookResult: MyLookResult)
    fun onMyLookFailure(code: Int, message: String)
}