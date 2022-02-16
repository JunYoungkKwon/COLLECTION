package com.eight.collection.ui.main.month

import com.eight.collection.data.entities.Calendar
import com.eight.collection.data.remote.auth.Auth

interface MonthView {
    fun onMonthLoading()
    fun onMonthSuccess(month: ArrayList<Calendar>)
    fun onMonthFailure(code: Int, message: String)
}