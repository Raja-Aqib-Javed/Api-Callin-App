package com.example.apicalling.model

 data class Users(
     val data: List<User>
 )
data class User(
    val id: Int,
    val email: String,
    val first_name: String,
    val last_name: String,
    val avatar: String
)
//"data": [
//{
//    "id": 7,
//    "email": "michael.lawson@reqres.in",
//    "first_name": "Michael",
//    "last_name": "Lawson",
//    "avatar": "https://reqres.in/img/faces/7-image.jpg"
//}