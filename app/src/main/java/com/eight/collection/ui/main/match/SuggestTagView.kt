package com.eight.collection.ui.main.match

import com.eight.collection.data.entities.Suggest

interface SuggestTagView {
    fun onSuggestTagLoading()
    fun onSuggestTagSuccess(suggestion: ArrayList<Suggest>?)
    fun onSuggestTagFailure(code: Int, message: String)
}