package com.example.apicalling.retrofit

import com.example.apicalling.api.UsersApi
import com.example.apicalling.model.RootResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi:UsersApi {

//    @GET("users?page=2")
//    override suspend fun getUsers(): Users
//
////    fun getAllUsers():List<Users>
//@GET("users?page=2")
//fun getAllUsers(@Query("api_key")key: String): Call<Users>
//
//    @GET("unknown")
//    fun getAllResources(@Query("api_key")key: String): Call<Resources>

    @GET("users?page=2")
    fun getApi(@Query("api_key")key: String):Call<RootResponse>

    @GET("users")
    fun getUsers(@Query("page") page:Int):Call<RootResponse>

    @GET("users")
    fun getPopularUsers(@Query("page") page: Int):Response<RootResponse>
}
