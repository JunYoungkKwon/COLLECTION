package com.eight.collection.ui.writing

interface DeleteBlockView {
    fun onDeleteBlockLoading()
    fun onDeleteBlockSuccess()
    fun onDeleteBlockFailure(code: Int, message: String)
}