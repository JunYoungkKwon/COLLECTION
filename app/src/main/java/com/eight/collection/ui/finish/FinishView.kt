package com.eight.collection.ui.finish

import com.eight.collection.data.remote.auth.Auth
import com.eight.collection.data.remote.finish.Finish

interface FinishView {
    fun onFinishLoading()
    fun onFinishSuccess(finish: Finish)
    fun onFinishFailure(code: Int, message: String)
}