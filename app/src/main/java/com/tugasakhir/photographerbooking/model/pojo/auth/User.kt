package com.tugasakhir.photographerbooking.model.pojo.auth

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    val uid: String? = null,
    val fullname: String,
    val email: String,
    val password: String,
    val role: String,
    val city : String,
    val phoneNumber: String,
    val profilePicture: String,
    val about: String
) : Parcelable