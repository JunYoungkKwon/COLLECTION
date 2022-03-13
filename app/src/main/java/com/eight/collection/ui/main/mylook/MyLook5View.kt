package com.eight.collection.ui.main.mylook

import com.eight.collection.data.entities.Calendar
import com.eight.collection.data.entities.MyLook
import com.eight.collection.data.remote.auth.Auth
import com.eight.collection.data.remote.mylook.MyLookResult

interface MyLook5View {
    fun onMyLook5Loading()
    fun onMyLook5Success(myLookResult: MyLookResult)
    fun onMyLook5Failure(code: Int, message: String)
}