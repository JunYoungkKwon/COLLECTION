package com.eight.collection.data.remote.deleteblock

import com.eight.collection.data.entities.Write.Content
import retrofit2.Call
import retrofit2.http.*

interface DeleteBlockRetrofitInterface {
    @PATCH("app/ootd/delete-block")
    fun deleteBlock(@Query("Clothes") clothes : Int,
                 @Query("PWW") pww : Int,
                 @Body content : Content
    ): Call<DeleteBlockResponse>
}