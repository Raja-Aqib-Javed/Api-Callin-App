package com.example.apicalling.model

import com.google.gson.annotations.SerializedName

data class RootResponse(
        @SerializedName("page") var page: Int?=null,
                        @SerializedName("per_page") var per_page: Int,
                        @SerializedName("total") var total: Int,
                        @SerializedName("total_pages") var total_pages: Int,
                        @SerializedName("data") var data: ArrayList<User>) {

}