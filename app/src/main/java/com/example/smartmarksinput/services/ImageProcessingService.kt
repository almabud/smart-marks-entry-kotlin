package com.example.smartmarksinput.services

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class ImageProcessingService {

    fun sendImage(photo: Bitmap){
        val baos = ByteArrayOutputStream()
        photo.compress(Bitmap.CompressFormat.JPEG, 50, baos)
        val file = baos.toByteArray()

        val visionApiService: VisionApiService = RetrofitClient.buildService(VisionApiService::class.java)

        val requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);

        val requestCall: Call<ResponseBody> = visionApiService.postImage(requestBody)


        requestCall.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("fail", call.toString())
                Log.d("fail Message", t.toString())
            }

            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {

                if(response.isSuccessful){
                    Log.d("success", call.toString())
                    Log.d("success2", response.toString())
                    val location = response.headers().get("Operation-Location")
                    Log.d("Location_Azure", location.toString())


                }
                else{

                }

            }

        })

    }


    fun getExtractedImageData(location: String){

    }
}