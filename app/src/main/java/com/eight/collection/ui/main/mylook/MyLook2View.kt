package com.eight.collection.ui.main.mylook

import com.eight.collection.data.entities.Calendar
import com.eight.collection.data.entities.MyLook
import com.eight.collection.data.remote.auth.Auth
import com.eight.collection.data.remote.mylook.MyLookResult

interface MyLook2View {
    fun onMyLook2Loading()
    fun onMyLook2Success(myLookResult: MyLookResult)
    fun onMyLook2Failure(code: Int, message: String)
}