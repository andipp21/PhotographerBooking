package com.tugasakhir.photographerbooking.model.services.photographer.profile

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.tugasakhir.photographerbooking.model.pojo.auth.User
import javax.inject.Inject

class PhotographerProfile @Inject constructor() {
    private val auth = FirebaseAuth.getInstance()
    private val userCollection = FirebaseFirestore.getInstance().collection("users")
    private val portofolioCollection = FirebaseFirestore.getInstance().collection("portofolio")
    private val packageCollection = FirebaseFirestore.getInstance().collection("pakcage")

    fun getUser(response: (User) -> Unit) {
        auth.uid?.let { it ->
            userCollection.document(it).get()
                .addOnSuccessListener { doc ->
                    val user = User(
                        doc.id,
                        doc.data?.getValue("fullname").toString(),
                        doc.data?.getValue("email").toString(),
                        doc.data?.getValue("password").toString(),
                        doc.data?.getValue("role").toString(),
                        doc.data?.getValue("city").toString(),
                        doc.data?.getValue("phone_number").toString(),
                        doc.data?.getValue("profile_picture").toString()
                    )
                    Log.d("user", user.toString())

                    response.invoke(user)
                }
                .addOnFailureListener {
                    Log.d("Errors: ", it.localizedMessage)
                }
        }
    }

    fun updateUserData(user: User, response: (String) -> Unit) {
        user.uid?.let {
            userCollection
                .document(it)
                .set(user, SetOptions.merge())
                .addOnSuccessListener {
                    Log.d("User Data Update", "Successfully Update User")
                    response.invoke("Successfully Update")
                }
                .addOnFailureListener {
                    Log.e("User Data Update", it.localizedMessage!!)
                    response.invoke("Error Update: ${it.localizedMessage}")
                }
        }
    }
}