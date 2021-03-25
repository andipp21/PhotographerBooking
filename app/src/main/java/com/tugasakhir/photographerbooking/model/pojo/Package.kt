package com.tugasakhir.photographerbooking.model.pojo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Package (
    val uid: String? = null,
    var title: String,
    var type: String,
    var price: Long,
    var benefit: List<String>,
    var userID: String
) : Parcelable