package com.vinu.vinodassigment.models

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "news_data")
data class ResponseModel(
    var title: String = "",
    var rows: List<NewsModel>? = ArrayList()
) : Parcelable {
    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int = 1
}