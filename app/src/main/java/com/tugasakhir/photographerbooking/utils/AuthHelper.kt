package com.tugasakhir.photographerbooking.utils

import com.google.firebase.auth.FirebaseAuth

object AuthHelper {
	val authFirebase: FirebaseAuth = FirebaseAuth.getInstance()

	fun hashPassword(passwordText: String): Int {
		return passwordText.hashCode()
	}

	fun checkAuth(): Boolean {
		val user = authFirebase.currentUser

		if (user != null) {
			return true
		}
		return false
	}
}