package com.vinu.vinodassigment.repository

import androidx.lifecycle.MutableLiveData
import com.vinu.vinodassigment.api.ApiService
import com.vinu.vinodassigment.database.NewsRoomDatabase
import com.vinu.vinodassigment.models.ResponseModel
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import retrofit2.HttpException

class NewsRepository(
    private val apiService: ApiService,
    private val newsDatabase: NewsRoomDatabase
) {

    var liveData = MutableLiveData<ResponseModel>()

    fun getNewsFeed() {
        CoroutineScope(IO).launch {
            //Get data from db first then go for API call
            var responseData = newsDatabase.newsDao().getAllNews()
            if (responseData != null) {
                responseData.isDataFromDb = true
                liveData.postValue(responseData)
                delay(500)
            } else {
                responseData = ResponseModel()
            }

            try {
                val responseModel = apiService.getNewsResponse()
                val rows = responseModel.rows?.filter {
                    it.description != null
                }
                responseModel.rows = rows
                responseModel.let {
                    newsDatabase.newsDao().insertNewsData(it)
                }
                responseModel.isDataFromDb = false
                liveData.postValue(responseModel)
            } catch (e: HttpException) {
                responseData.exceptionMsg = "No Internet Connection"
                responseData.isDataFromDb = false
                e.printStackTrace()
                liveData.postValue(ResponseModel(exceptionMsg = "No Internet Connection", isDataFromDb = false))
            } catch (e: Exception) {
                responseData.exceptionMsg = "Error occurred. Please contact support"
                responseData.isDataFromDb = false
                e.printStackTrace()
                liveData.postValue(
                    responseData
                )
            }
        }
    }
}