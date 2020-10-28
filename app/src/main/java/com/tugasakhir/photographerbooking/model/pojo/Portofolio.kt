package com.tugasakhir.photographerbooking.model.pojo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Portofolio (
    val uid: String? = null,
    val portofolioImage: String,
    val userID: String
) : Parcelable