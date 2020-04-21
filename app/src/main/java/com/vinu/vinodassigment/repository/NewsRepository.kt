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

    var responseModelData = MutableLiveData<ResponseModel>()

    /*
     * Name : getNewsFeed()
     *  Description : 1.  This method fetches data from the local db first and updates the UI,
     *                2. Then it fetches the data from server and inserts the data into local db
     *                3. The Local Database takes care of updating the UI thats been observed in view model
     */
    fun getNewsFeed() {
        CoroutineScope(IO).launch {
            var responseData = newsDao.getAllNews()
            if (responseData != null) {
                responseData.isDataFromDb = true
                responseModelData.postValue(responseData)
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
                responseModelData.postValue(responseModel)
            }  catch (e: Exception) {
                responseData.errorMessage = "Error occurred. Please contact support"
                responseData.isDataFromDb = false
                e.printStackTrace()
                responseModelData.postValue(
                    responseData
                )
            }
        }
    }
}