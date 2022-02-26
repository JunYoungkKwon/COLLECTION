package com.eight.collection.ui.writing

interface AddBlockView {
    fun onAddBlockLoading()
    fun onAddBlockSuccess(content : String)
    fun onAddBlockFailure(code: Int, message: String)
}