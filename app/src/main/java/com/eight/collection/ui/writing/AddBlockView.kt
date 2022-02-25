package com.eight.collection.ui.writing

interface AddBlockView {
    fun onAddBlockLoading()
    fun onAddBlockSuccess()
    fun onAddBlockFailure(code: Int, message: String)
}