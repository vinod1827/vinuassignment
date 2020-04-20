package com.vinu.vinodassigment

import com.vinu.vinodassigment.api.RetrofitBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.robolectric.annotation.Config


@RunWith(JUnit4::class)
@Config(manifest = Config.NONE)
class ApiTest {

    @Test
    fun testListIsNull() {
        CoroutineScope(Dispatchers.IO).launch {
            val responseModel = RetrofitBuilder.apiService.getNewsResponse()
            assert(responseModel.rows != null)
        }
    }


    @Test
    fun testImageUrlIsNull() {
        CoroutineScope(Dispatchers.IO).launch {
            var imageIsNull = false
            val responseModel = RetrofitBuilder.apiService.getNewsResponse()
            responseModel.rows?.forEach {
                if (it.imageHref == null) imageIsNull = true
            }
            assert(imageIsNull)
        }
    }


}