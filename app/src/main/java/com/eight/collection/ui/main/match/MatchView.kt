package com.eight.collection.ui.main.match

import com.eight.collection.data.entities.Calendar
import com.eight.collection.data.entities.Diary
import com.eight.collection.data.remote.auth.Auth

interface MatchView {
    fun onMatchLoading()
    fun onMatchSuccess(match: MutableList<Diary>)
    fun onMatchFailure(code: Int, message: String)
}