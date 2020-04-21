package com.vinu.vinodassigment

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vinu.vinodassigment.api.RetrofitBuilder
import com.vinu.vinodassigment.dao.NewsDao
import com.vinu.vinodassigment.database.NewsRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.io.IOException

@RunWith(AndroidJUnit4::class)
@Config(manifest= Config.NONE)
class LocalDatabaseTest {
    private lateinit var newsDao: NewsDao
    private lateinit var db: NewsRoomDatabase

    @Before
    fun setup() {
        NewsRoomDatabase.TEST_MODE = true
        db = NewsRoomDatabase.getDatabase(ApplicationProvider.getApplicationContext())
        newsDao =
            NewsRoomDatabase.getDatabase(ApplicationProvider.getApplicationContext()).newsDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeReadNewsFeed() {
        CoroutineScope(Dispatchers.IO).launch {
            val responseModel = RetrofitBuilder.apiService.getNewsResponse()
            val rows = responseModel.rows?.filter {
                it.description != null
            }
            responseModel.rows = rows
            responseModel.let {
                newsDao.insertNewsData(it)
                assert(newsDao.getAllNews()?.rows?.size ?: 0 > 0)
            }
        }
    }
}