package com.vinu.vinodassigment.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.vinu.vinodassigment.models.NewsModel

class Converters {
    @TypeConverter
    fun fromArrayLisr(value: List<NewsModel>?) = Gson().toJson(value)

    @TypeConverter
    fun fromString(value: String) = Gson().fromJson(value, Array<NewsModel>::class.java).toList()
}