package com.example.apicalling.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apicalling.*
import com.example.apicalling.model.RootResponse
import com.example.apicalling.model.User
import com.example.apicalling.retrofit.RetrofitApi
import com.example.apicalling.retrofit.RetrofitInstance
import com.example.apicalling.source.DataRepositery
import com.google.gson.Gson
import retrofit2.Response
import kotlin.collections.ArrayList

class MainViewModel(): ViewModel() {
    private  val dataRepositery = DataRepositery(RetrofitInstance.service)
    val livedata = MutableLiveData<ArrayList<User>>()
    val progressdata = MutableLiveData<Boolean>()
     val listdata = MutableLiveData<ArrayList<User>>()
    val logsData = MutableLiveData<Logs>()
    var page = 1
    var totalPage = 0
    var mypage =0
    val randomId = (0..100000).random()


    fun requestPolling():Boolean{
        var res = false
        mypage++
        if(mypage<=totalPage){
            val request =RetrofitInstance.buildService(RetrofitApi::class.java)

            dataRepositery.getApi(object :DataRepositery.DataList{
                override fun <T> returnList(userList: Response<T>) {
                    if(userList.isSuccessful)
                    {
                        res = true
                    }
                }
            },request.getUsers(mypage))
        }
        return res
    }
    fun requestAndSaveData(){
        progressdata.postValue(true)
        var logsInstance = LogsApplication.getLogs()
        val request =RetrofitInstance.buildService(RetrofitApi::class.java)
        logsInstance.methodName = "Post"
        logsInstance.plainRequest = request.toString()

        dataRepositery.getApi(object :DataRepositery.DataList{
            override fun <T> returnList(userList: Response<T>) {
                var response = (userList.body()as RootResponse).data
                livedata.postValue(response)
                var pages = (userList.body() as RootResponse)
                totalPage = pages.total_pages
                progressdata.postValue(false)

                val gson = Gson()
                val json = gson.toJson(userList.body())
                logsInstance.response = json
                logsInstance.url = RetrofitInstance.retrofit.baseUrl().toString()+"/users?page="+(userList.body() as RootResponse).page

            }
        },request.getUsers(page))
       progressdata.postValue(false)
        logsData.postValue(logsInstance)
    }
    fun listScrolled() {
        progressdata.postValue(true)
        page++
        if (page <= totalPage) {
            val request = RetrofitInstance.buildService(RetrofitApi::class.java)
            dataRepositery.getApi(object : DataRepositery.DataList {
                override fun <T> returnList(userList: Response<T>) {
                    var response = (userList.body() as RootResponse).data
                    listdata.postValue(response)
                    progressdata.postValue(false)
                }
            }, request.getUsers(page))
        }
        progressdata.postValue(false)
    }
//        Timer().schedule(1000){
//            progressdata.postValue(false)
//        }
//    fun fetchUser(){
//
//        viewModelScope.launch {
//          val user = withContext(Dispatchers.IO){
//                dataRepositery.getUsers().data[0]
//            }
//            userLiveData.value = user
//        }
//    }
//    fun fetchAllUser(){
//        dataRepositery.getAllUser(object: DataRepositery.hello{
//            override fun returnList(userList: List<User>) {
//
//                livedata.postValue(userList)
//            }
//
//        })
//
//    }
//    fun fetchAllResource(){
//        dataRepositery.getAllResource(object :DataRepositery.resource{
//            override fun returnList(resourceList: List<Resource>) {
//                resourceLiveData.postValue(resourceList)
//            }
//        })
//    }
//
//   fun getApi(){
//       val request = RetrofitInstance.buildService(RetrofitApi::class.java)
//       val call = request.getAllUsers("users?page=2")
//       dataRepositery.getApi(object :DataRepositery.Content{
//           override fun <T> retunOnSucess(value: T) {
//              var users = value
//           }
//
//           override fun returnOnFailure(value: String) {
//               TODO("Not yet implemented")
//           }
//       },call)
//   }

}