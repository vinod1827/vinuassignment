package com.vinu.vinodassigment.repository

import androidx.lifecycle.MutableLiveData
import com.vinu.vinodassigment.api.RetrofitBuilder
import com.vinu.vinodassigment.dao.NewsDao
import com.vinu.vinodassigment.models.ResponseModel
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import retrofit2.HttpException

class NewsRepository(
    private val newsDao: NewsDao
) {

    var liveData = MutableLiveData<ResponseModel>()

    fun getNewsFeed() {
        CoroutineScope(IO).launch {
            //Get data from db first then go for API call
            var responseData = newsDao.getAllNews()
            if (responseData != null) {
                responseData.isDataFromDb = true
                liveData.postValue(responseData)
                delay(500)
            } else {
                responseData = ResponseModel()
            }

            try {
                val responseModel = RetrofitBuilder.apiService.getNewsResponse()
                val rows = responseModel.rows?.filter {
                    it.description != null
                }
                responseModel.rows = rows
                responseModel.let {
                    newsDao.insertNewsData(it)
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