package com.eight.collection.ui.main.week

import com.eight.collection.data.entities.Calendar
import com.eight.collection.data.remote.auth.Auth

interface WeeklyView {
    fun onWeeklyLoading()
    fun onWeeklySuccess(weekly: ArrayList<Diary>)
    fun onWeeklyFailure(code: Int, message: String)
}