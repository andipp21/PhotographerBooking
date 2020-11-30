package com.tugasakhir.photographerbooking.model.pojo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReviewDetail (
    val idOrder: String,
    val photographerId: String,
    val clientId: String,
    val reviewText: String,
    val reviewScore: Int
):Parcelable