package com.moneyconverter.api

import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


interface ApiServices {

    companion object {
        private const val BASE_API = /*"http://apilayer.net/"*/"https://free.currconv.com"
        private const val timeoutTime = 30

        private val defaultHttpClient = OkHttpClient.Builder()
            .connectTimeout(timeoutTime.toLong(), TimeUnit.SECONDS)
            .writeTimeout(timeoutTime.toLong(), TimeUnit.SECONDS)
            .readTimeout(timeoutTime.toLong(), TimeUnit.SECONDS).build()


        private val sessionRetrofit = Retrofit.Builder()
            .baseUrl(BASE_API)
            .addConverterFactory(GsonConverterFactory.create())
            .client(defaultHttpClient)
            .build()


        val service: ApiServices = sessionRetrofit.create(ApiServices::class.java)
    }

    @GET("/api/v7/convert")
    fun getConvertValue(@Query("q")current:String, @Query("compact")compact:String, @Query("apiKey")key:String): Call<JsonObject>

}