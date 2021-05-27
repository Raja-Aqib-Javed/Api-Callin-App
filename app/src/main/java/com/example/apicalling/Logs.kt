package com.example.apicalling

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Logs(
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "id") var id:Int=0,
    @ColumnInfo(name = "method_name") var methodName:String?=null,
    @ColumnInfo(name = "url") var url:String?=null,
    @ColumnInfo(name = "request") var plainRequest:String?=null,
    @ColumnInfo(name = "response") var response:String?=null
//    @ColumnInfo(name = "enc_req") var encRequest:String?=null,
//    @ColumnInfo(name = "enc_req_key") var encRequestKey:String?=null,
//    @ColumnInfo(name = "enc_res") var encResponse:String?=null,
//    @ColumnInfo(name = "enc_res_key") var encResponseKey:String?=null
)