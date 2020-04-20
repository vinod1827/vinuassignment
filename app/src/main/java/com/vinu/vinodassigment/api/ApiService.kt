package com.vinu.vinodassigment.api

import com.vinu.vinodassigment.models.ResponseModel
import retrofit2.http.GET

interface ApiService {
    @GET("/s/2iodh4vg0eortkl/facts.json/")
    suspend fun getNewsResponse(): ResponseModel
}