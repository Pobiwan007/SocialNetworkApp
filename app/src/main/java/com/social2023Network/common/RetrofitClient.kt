package com.social2023Network.common

import android.util.Log
import com.google.gson.GsonBuilder
import com.social2023Network.common.network.NetworkService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val TIME_OUT: Long = 150
    private val gson = GsonBuilder().setLenient().create()

    private val okHttpClient = OkHttpClient.Builder()
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor{
            val resp = it.proceed(it.request())
            if(resp.code == 200){
                try{
                    println(resp.peekBody(2048).byteString())
                }
                catch (e: Exception) {
                    Log.e("exeption", e.toString())
                    println("Error parse json from intercept..............")
                }
            } else
                println(resp)
            resp
        }.build()
    val retrofitAnime : NetworkService by lazy {
        Retrofit.Builder()
            .baseUrl(AllApi.BASE_ANIME)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build().create(NetworkService::class.java)
    }
}