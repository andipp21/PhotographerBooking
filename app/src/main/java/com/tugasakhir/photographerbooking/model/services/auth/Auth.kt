package com.tugasakhir.photographerbooking.model.services.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.tugasakhir.photographerbooking.model.pojo.auth.User
import javax.inject.Inject

class Auth @Inject constructor() {
    private val auth = FirebaseAuth.getInstance()
    private val userCollection = FirebaseFirestore.getInstance().collection("users")

    fun register(user: User, response: (String) -> Unit) {
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnSuccessListener {
                val data = HashMap<String, Any>()
                data["fullname"] = user.fullname
                data["email"] = user.email
                data["password"] = user.password
                data["role"] = user.role
                data["city"] = user.city
                data["phone_number"] = user.phoneNumber
                data["profil_picture"] = user.profilePicture
                data["about"] = user.about

                val profileChangeRequest =
                    UserProfileChangeRequest.Builder().setDisplayName(user.fullname).build()

                auth.currentUser?.updateProfile(profileChangeRequest)

                //save to firestore
                userCollection.document(it.user!!.uid)
                    .set(data)
                    .addOnSuccessListener {
                        Log.d("Firebase", "Successfully Registered")
                        response.invoke("Successfully Registered")
                    }
                    .addOnFailureListener { exception ->
                        Log.e("Register", exception.localizedMessage!!)
                        response.invoke("Error Registered: ${exception.localizedMessage}")
                    }

            }
            .addOnFailureListener { exception ->
                response.invoke("Error Registered: ${exception.localizedMessage}")
            }
    }

    fun login(email: String, password: String, response: (String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                response.invoke("Login Successfully")
            }
            .addOnFailureListener { exception ->
                response.invoke("Error Login: ${exception.localizedMessage}")
            }
    }

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
                        doc.data?.getValue("profile_picture").toString(),
                        doc.data?.getValue("about").toString()
                    )
                    Log.d("user", user.toString())

                    response.invoke(user)
                }
                .addOnFailureListener {
                    Log.d("Errors: ", it.localizedMessage)
                }
        }
    }

}