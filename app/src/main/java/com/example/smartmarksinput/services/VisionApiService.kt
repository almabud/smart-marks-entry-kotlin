package com.example.smartmarksinput.services


import android.graphics.Bitmap
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface VisionApiService {

    @Headers("Ocp-Apim-Subscription-Key: 111174b0a48141309993a37a3fddf562", "Content-Type: application/octet-stream")
    @POST("vision/v2.0/read/core/asyncBatchAnalyze")
    fun postImage(@Body image: RequestBody): Call<ResponseBody>

    @Headers("Ocp-Apim-Subscription-Key: 111174b0a48141309993a37a3fddf562")
    @GET
    fun getExtractedImageData(@Url url: String): Call<ResponseBody>

}