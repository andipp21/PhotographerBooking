package com.tugasakhir.photographerbooking.model.pojo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Order(
    val uid: String? = null,
    var clientID: String,
    var photographerID: String,
    var packageID: String,
    var photoshootTime: Date,
    var orderTime: Date,
    var isConfirmed: Boolean,
    var isDone: Boolean,
    var isPayed: Boolean,
    var payImage: String
): Parcelable