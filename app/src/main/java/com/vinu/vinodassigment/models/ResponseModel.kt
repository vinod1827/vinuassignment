package com.vinu.vinodassigment.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "news_data")
data class ResponseModel(
    @PrimaryKey @ColumnInfo(name = "title") var title: String = "",
    var rows: List<NewsModel>? = ArrayList(),
    var exceptionMsg: String = "",
    var isDataFromDb: Boolean = false
) : Parcelable
