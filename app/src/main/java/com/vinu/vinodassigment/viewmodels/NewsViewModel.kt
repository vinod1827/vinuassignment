package com.vinu.vinodassigment.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vinu.vinodassigment.database.NewsRoomDatabase
import com.vinu.vinodassigment.repository.NewsRepository
import com.vinu.vinodassigment.models.ResponseModel

class NewsViewModel(application: Application) :
    AndroidViewModel(application) {

    private var repository: NewsRepository
    val responseModel: LiveData<ResponseModel> = MutableLiveData()

    /*
     * Name : init block
     *  Description : 1. getting instance of  data access object
     *                2. creating an instance of repository
     *                3. observing variables / data from the repository
     */
    init {
        val newsDao = NewsRoomDatabase.getDatabase(getApplication()).newsDao()
        repository = NewsRepository(newsDao)
        repository.responseModelData.observeForever { responseData ->
            (responseModel as MutableLiveData).value = responseData
        }
    }


    /*
     * Name : getNewsFeed()
     *  Description :  Fetching Data using Repository,
     */
    fun getNewsFeed() {
        repository.getNewsFeed()
    }
}