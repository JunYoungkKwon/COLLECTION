package com.eight.collection.ui.main.week

import com.eight.collection.data.entities.Calendar
import com.eight.collection.data.entities.Diary
import com.eight.collection.data.remote.auth.Auth

interface DeleteView {
    fun onDeleteLoading()
    fun onDeleteSuccess()
    fun onDeleteFailure(code: Int, message: String)
}