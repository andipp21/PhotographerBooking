package com.tugasakhir.photographerbooking.model.services.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.tugasakhir.photographerbooking.model.pojo.User
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
                data["gopay_number"] = user.numberGopay
                data["dana_number"] = user.numberDana
                data["link_aja_number"] = user.numberLinkAja
                data["ovo_number"] = user.numberOvo

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
            userCollection.document(it)
                .addSnapshotListener { value, _ ->
                    var nOvo = value?.data?.get("ovo_number").toString()
                    var nDana = value?.data?.get("dana_number").toString()
                    var nGopay = value?.data?.get("gopay_number").toString()
                    var nLinkAja = value?.data?.get("link_aja_number").toString()

                    val user = User(
                        uid = value?.id,
                        fullname = value?.data?.getValue("fullname").toString(),
                        email = value?.data?.getValue("email").toString(),
                        password = value?.data?.getValue("password").toString(),
                        role = value?.data?.getValue("role").toString(),
                        city = value?.data?.getValue("city").toString(),
                        phoneNumber = value?.data?.getValue("phone_number").toString(),
                        profilePicture = value?.data?.getValue("profile_picture").toString(),
                        about = value?.data?.getValue("about").toString(),
                        numberOvo = nOvo,
                        numberDana = nDana,
                        numberLinkAja = nLinkAja,
                        numberGopay = nGopay
                    )
                    Log.d("user", user.toString())

                    response.invoke(user)
                }
        }
    }

}