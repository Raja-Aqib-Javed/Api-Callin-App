package com.example.apicalling.source

import android.util.Log
import com.example.apicalling.model.RootResponse
import com.example.apicalling.model.Users
import com.example.apicalling.retrofit.RetrofitApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val GITHUB_STARTING_PAGE_INDEX = 1


class DataRepositery(val usersApi: RetrofitApi) {
    suspend fun getUsers(param: DataList, users: Call<RootResponse>): Users {

        return usersApi.getUsers()
    }

    fun <T> getApi(data: DataList, call: Call<T>) {
        call.enqueue(object : Callback<T> {

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if(response.isSuccessful) {

                    data.returnList(response)
                }
            }
            override fun onFailure(call: Call<T>, t: Throwable) {
                Log.e("Error", t.message.toString())
            }
        })
    }
    interface DataList {
        fun<T> returnList(userList: Response<T>)
    }

//    suspend fun requestMore(query: String) {
//        if (isRequestInProgress) return
//        val successful = requestAndSaveData(query)
//        if (successful) {
//            lastRequestedPage++
//        }
//    }
//    suspend fun getSearchResultStream(query: String): MutableSharedFlow<RootResponse> {
//        Log.d("GithubRepository", "New query: $query")
//        lastRequestedPage = 1
//        inMemoryCache.clear()
//        requestAndSaveData(query)
//
//        return searchResults
//    }

//
//
//     fun getAllUser(hello: hello) {
//
//        val request = RetrofitInstance.buildService(RetrofitApi::class.java)
//        val call = request.getAllUsers("users?page=2")
//
//         call.enqueue {
//             onResponse = {
//                 if(it.isSuccessful){
//                     var users=it.body()!!.data
//                    list = users
//                    hello.returnList(list)
//                 }
//             }
//             onFailure = {
//                 Log.d("error", it.toString())
//
//             }
//         }
//
////        call.enqueue(object :retrofit2.Callback<Users>{
////            override fun onResponse(call: Call<Users>, response: Response<Users>) {
////                if (response.isSuccessful){
////                    var users=response.body()!!.data
////                    list = users
////                    hello.returnList(list)
////                }
////            }
////            override fun onFailure(call: Call<Users>, t: Throwable) {
////
////                t.message?.let { Log.d("error", it) }
////
////            }
////        })
//    }
//
//    interface hello{
//        fun returnList(userList:List<User>)
//    }
//
//    fun getAllResource(resource:resource){
//        val request = RetrofitInstance.buildService(RetrofitApi::class.java)
//        val call = request.getAllResources("unknown")
//        call.enqueue {
//            onResponse = {
//
//                if(it.isSuccessful){
//                    var users = it.body()!!.data
//                    mylist = users
//                    resource.returnList(mylist)
//                }
//            }
//            onFailure = {
//
//            }
//        }
//
////        call.enqueue(object :retrofit2.Callback<Resources>{
////            override fun onResponse(call: Call<Resources>, response: Response<Resources>) {
////                if (response.isSuccessful){
////                    var users=response.body()!!.data
////                    mylist = users
////                    resource.returnList(mylist)
////                }
////            }
////
////            override fun onFailure(call: Call<Resources>, t: Throwable) {
////                TODO("Not yet implemented")
////            }
////        })
//    }
//    interface resource{
//        fun returnList(resourceList:List<Resource>)
//    }
//
//     interface Content {
//         fun<T> retunOnSucess(value:T)
//         fun returnOnFailure(value: String)
//     }
//    fun <T> getApi(data: Content, call: Call<T>){
//        call.enqueue(object : Callback<T> {
//            override fun onResponse(call: Call<T>, response: Response<T>) {
//
//                if(response.isSuccessful) {
//
//                    data.retunOnSucess(response.body())
//
//                }
//            }
//            override fun onFailure(call: Call<T>, t: Throwable) {
//                Log.e("Error", t.message.toString())
//                data.returnOnFailure(t.message.toString())
//            }
//        })
//    }
//
//
//         fun<T> Call<T>.enqueue(callback: CallBackKt<T>.() -> Unit) {
//        val callBackKt = CallBackKt<T>()
//        callback.invoke(callBackKt)
//        this.enqueue(callBackKt)
//    }
//
//    class CallBackKt<T>: Callback<T> {
//        var onResponse: ((Response<T>) -> Unit)? = null
//        var onFailure: ((t: Throwable?) -> Unit)? = null
//
//        override fun onFailure(call: Call<T>, t: Throwable) {
//            onFailure?.invoke(t)
//        }
//
//        override fun onResponse(call: Call<T>, response: Response<T>) {
//            onResponse?.invoke(response)
//        }
//
//    }

}