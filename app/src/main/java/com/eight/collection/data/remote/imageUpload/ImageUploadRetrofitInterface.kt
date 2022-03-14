package com.eight.collection.data.remote.imageUpload

import com.eight.collection.data.entities.Write.Content
import com.eight.collection.data.entities.Write.ImageUpload
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.io.File

interface ImageUploadRetrofitInterface {
    @Multipart
    @POST("app/ootd/upload-photo")
    fun imageUpload(
        @Part image : MultipartBody.Part
    ): Call <ImageUploadResponse>
}