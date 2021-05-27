package com.example.apicalling.model

data class Resources(
        val data:List<Resource>
)
data class Resource(
    val id: Int,
    val name: String,
    val year: String,
    val color: String,
    val pantone_value: String
    )