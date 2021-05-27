package com.example.apicalling

import android.content.Context
import com.example.apicalling.retrofit.RetrofitApi
import com.example.apicalling.retrofit.RetrofitInstance
import com.example.apicalling.source.DataRepositery
import com.example.apicalling.viewmodel.MainViewModel
import retrofit2.Response

class RequestPolling(val context: Context,val viewModel: MainViewModel) {

    private  val dataRepositery = DataRepositery(RetrofitInstance.service)
    var page =0
    fun polling()
    {
       val data = viewModel.requestPolling()
        if(data)
        {
            polling()
        }
    }

    fun requestPollingData(){
        page++
            val request = RetrofitInstance.buildService(RetrofitApi::class.java)
            dataRepositery.getApi(object : DataRepositery.DataList{
                override fun <T> returnList(user: Response<T>) {
                    if(user.isSuccessful)
                    {
                        requestPollingData()
                    }
                }
            },request.getUsers(page))
        }

    }
