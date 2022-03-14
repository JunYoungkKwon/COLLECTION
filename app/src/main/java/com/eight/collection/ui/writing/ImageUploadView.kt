package com.eight.collection.ui.writing

interface ImageUploadView {
    fun onImageUploadLoading()
    fun onImageUploadSuccess(url: String)
    fun onImageUploadFailure(code: Int, message: String)
}