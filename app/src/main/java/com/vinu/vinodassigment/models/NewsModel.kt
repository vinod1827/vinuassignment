package com.vinu.vinodassigment.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class NewsModel(
    var title: String? = null,
    var description: String? = null,
    var imageHref: String? = null
) : Parcelable