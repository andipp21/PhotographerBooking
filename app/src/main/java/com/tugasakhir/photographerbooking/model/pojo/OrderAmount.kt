package com.tugasakhir.photographerbooking.model.pojo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderAmount (
    val totalOrder: Int,
    val orderThisYear: Int,
    val orderThisMonth: Int,
    val orderThisDay: Int,
    val orderWaitConfirmation: Int,
    val orderConfirmed: Int,
    val orderPayed: Int,
    val orderDone: Int,
    val orderReviewed: Int,
    val photoshootThisYear: Int,
    val photoshootThisMonth: Int,
    val photoshootThisDay: Int
): Parcelable