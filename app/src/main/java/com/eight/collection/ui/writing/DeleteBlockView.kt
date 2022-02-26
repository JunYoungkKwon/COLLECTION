package com.eight.collection.ui.writing

interface DeleteBlockView {
    fun onDeleteBlockLoading()
    fun onDeleteBlockSuccess(content : String)
    fun onDeleteBlockFailure(code: Int, message: String)
}