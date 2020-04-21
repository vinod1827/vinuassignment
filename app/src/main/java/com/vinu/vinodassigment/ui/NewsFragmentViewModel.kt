package com.vinu.vinodassigment.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vinu.vinodassigment.repository.NewsRepository
import com.vinu.vinodassigment.models.ResponseModel

class NewsFragmentViewModel(var repository: NewsRepository) :
    ViewModel() {

    val responseModel: LiveData<ResponseModel> = MutableLiveData()

    init {
        repository.liveData.observeForever { responseData ->
            (responseModel as MutableLiveData).value = responseData
        }
    }

    fun getNewsFeed() {
        repository.getNewsFeed()
    }
}