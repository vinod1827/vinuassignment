package com.vinu.vinodassigment.repository

import com.vinu.vinodassigment.api.RetrofitBuilder
import com.vinu.vinodassigment.dao.NewsDao
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class NewsRepository(private val newsDao: NewsDao) {

    val responseData = newsDao.getAllNews()

    fun getNewsFeed() {
        CoroutineScope(IO).launch {
            val responseModel = RetrofitBuilder.apiService.getNewsResponse()
            val rows = responseModel.rows?.filter {
                it.title != null && it.description != null
            }
            responseModel.rows = rows
            responseModel.let {
                newsDao.insertNewsData(it)
            }
        }
    }
}