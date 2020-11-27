package com.tugasakhir.photographerbooking.model.pojo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Review (
    val score: Float,
    val review: String,
): Parcelable