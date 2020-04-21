package com.vinu.vinodassigment.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vinu.vinodassigment.models.ResponseModel

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewsData(responseModel: ResponseModel)

    @Query("SELECT * FROM news_data")
    fun getAllNews(): ResponseModel?
}