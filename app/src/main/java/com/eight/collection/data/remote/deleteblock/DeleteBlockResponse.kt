package com.eight.collection.data.remote.deleteblock

import com.google.gson.annotations.SerializedName

data class DeleteBlockResult(@SerializedName("deleted block") val deletedblock: String?)
data class DeleteBlockResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: DeleteBlockResult?
)