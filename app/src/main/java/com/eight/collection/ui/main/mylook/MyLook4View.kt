package com.eight.collection.ui.main.mylook

import com.eight.collection.data.entities.Calendar
import com.eight.collection.data.entities.MyLook
import com.eight.collection.data.remote.auth.Auth
import com.eight.collection.data.remote.mylook.MyLookResult

interface MyLook4View {
    fun onMyLook4Loading()
    fun onMyLook4Success(myLookResult: MyLookResult)
    fun onMyLook4Failure(code: Int, message: String)
}