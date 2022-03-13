package com.eight.collection.ui.main.mylook

import com.eight.collection.data.entities.Calendar
import com.eight.collection.data.entities.MyLook
import com.eight.collection.data.remote.auth.Auth
import com.eight.collection.data.remote.mylook.MyLookResult

interface MyLook3View {
    fun onMyLook3Loading()
    fun onMyLook3Success(myLookResult: MyLookResult)
    fun onMyLook3Failure(code: Int, message: String)
}