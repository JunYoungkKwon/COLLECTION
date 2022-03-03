package com.eight.collection.ui.writing

import com.eight.collection.data.remote.getaddedblock.GetAddedBlockResult

interface GetAddedBlockView {
    fun onGetAddedBlockLoading()
    fun onGetAddedBlockSuccess(getaddedblockresult : GetAddedBlockResult)
    fun onGetAddedBlockFailure(code: Int, message: String)
}