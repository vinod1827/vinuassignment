package com.vinu.vinodassigment.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.vinu.vinodassigment.database.NewsRoomDatabase
import com.vinu.vinodassigment.repository.NewsRepository
import com.vinu.vinodassigment.models.ResponseModel

class NewsFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NewsRepository

    val responseModel: LiveData<ResponseModel>

    init {
        val newsDao = NewsRoomDatabase.getDatabase(application).newsDao()
        repository = NewsRepository(newsDao)
        responseModel = repository.responseData
    }

    fun getNewsFeed() {
        repository.getNewsFeed()
    }
}