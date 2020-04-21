package com.vinu.vinodassigment.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsModel(
    @PrimaryKey @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "imageHref") var imageHref: String?
) : Parcelable