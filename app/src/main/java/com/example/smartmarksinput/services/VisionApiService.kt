package com.example.smartmarksinput.services

import okhttp3.Call
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.POST
import retrofit2.http.Multipart
import retrofit2.http.Part


interface VisionApiService {

    @Multipart
    @POST("/yourEndPoint")
    fun postImage(@Part image: MultipartBody.Part): Call
}