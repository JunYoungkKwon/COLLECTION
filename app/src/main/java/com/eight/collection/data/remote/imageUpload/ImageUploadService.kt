package com.eight.collection.data.remote.imageUpload

import android.util.Log
import com.eight.collection.ApplicationClass.Companion.TAG
import com.eight.collection.ApplicationClass.Companion.retrofit
import com.eight.collection.ui.writing.ImageUploadView
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

object ImageUploadService {
    fun imageUpload(imageUploadView: ImageUploadView, file : File) {
        val imageUploadService = retrofit.create(ImageUploadRetrofitInterface::class.java)
        var requestFile : RequestBody = file.asRequestBody("image/png".toMediaTypeOrNull())
        var body : MultipartBody.Part = MultipartBody.Part.createFormData("image", file.name, requestFile)
        Log.d("body","${requestFile}")
        Log.d("body","${body}")
        Log.d("body","${file}")
        Log.d("body","${file.name}")
        imageUploadView.onImageUploadLoading()
        imageUploadService.imageUpload(body).enqueue(object : Callback<ImageUploadResponse> {
            override fun onResponse(call: Call<ImageUploadResponse>, response: Response<ImageUploadResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1025 -> {
                        imageUploadView.onImageUploadSuccess(resp.result!!.s3imageurl)
                        Log.d("success","success")
                    }
                    else -> {
                        imageUploadView.onImageUploadFailure(resp.code, resp.message)
                        Log.d("fail","fail")
                    }
                }
            }

            override fun onFailure(call: Call<ImageUploadResponse>, t: Throwable) {
                Log.d("$TAG/API-ERROR", t.message.toString())
                imageUploadView.onImageUploadFailure(400, "네트워크 오류가 발생했습니다.")
            }
        })
    }
}