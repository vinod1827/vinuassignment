package com.vinu.vinodassigment.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vinu.vinodassigment.database.NewsRoomDatabase
import com.vinu.vinodassigment.repository.NewsRepository
import com.vinu.vinodassigment.models.ResponseModel

class NewsViewModel(application: Application) :
    AndroidViewModel(application) {

    private var repository: NewsRepository
    val responseModel: LiveData<ResponseModel> = MutableLiveData()

    init {
        val newsDao = NewsRoomDatabase.getDatabase(getApplication()).newsDao()
        repository = NewsRepository(newsDao)
        repository.liveData.observeForever { responseData ->
            (responseModel as MutableLiveData).value = responseData
        }
    }

    fun getNewsFeed() {
        repository.getNewsFeed()
    }
}