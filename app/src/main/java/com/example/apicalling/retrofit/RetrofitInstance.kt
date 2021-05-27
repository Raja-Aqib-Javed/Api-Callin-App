package com.example.apicalling.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
   private val builder = Retrofit.Builder()
       .baseUrl("http://reqres.in/api/")
       .addConverterFactory(GsonConverterFactory.create())
       .client(client.build())
    val retrofit = builder.build()
    val service = retrofit.create(RetrofitApi::class.java)

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}