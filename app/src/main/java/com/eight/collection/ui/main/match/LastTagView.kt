package com.eight.collection.ui.main.match

import com.eight.collection.data.entities.Calendar
import com.eight.collection.data.entities.Diary
import com.eight.collection.data.remote.auth.Auth

interface LastTagView {
    fun onLastTagLoading()
    fun onLastTagSuccess(lastTag: ArrayList<LastTag>)
    fun onLastTagFailure(code: Int, message: String)
}