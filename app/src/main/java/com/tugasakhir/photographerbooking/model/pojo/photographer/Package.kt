package com.tugasakhir.photographerbooking.model.pojo.photographer

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Package (
    val uid: String? = null,
    val title: String,
    val type: String,
    val time: String,
    val price: Long,
    val benefit: List<String>,
    val userID: String
) : Parcelable