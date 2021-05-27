package com.example.apicalling.api

import com.example.apicalling.model.User
import com.example.apicalling.model.Users

interface UsersApi {

   suspend fun getUsers(): Users

   fun getAllUser(): List<User>



}