package com.tugasakhir.photographerbooking.model.services.client.home

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tugasakhir.photographerbooking.model.pojo.auth.User
import com.tugasakhir.photographerbooking.model.pojo.photographer.Package
import com.tugasakhir.photographerbooking.model.pojo.photographer.Portofolio
import javax.inject.Inject

class ClientHome @Inject constructor() {
    private val auth = FirebaseAuth.getInstance()
    private val userCollection = FirebaseFirestore.getInstance().collection("users")
    private val portofolioCollection = FirebaseFirestore.getInstance().collection("portofolio")
    private val packageCollection = FirebaseFirestore.getInstance().collection("pakcage")

    fun fetchPhotographer(response: (List<User>) -> Unit){
        userCollection.whereEqualTo("role", "photographer").get()
            .addOnSuccessListener {
                val listData: MutableList<User> = mutableListOf()
                for (doc in it) {
                    listData.add(
                        User(
                            doc.id,
                            doc.data.getValue("fullname").toString(),
                            doc.data.getValue("email").toString(),
                            doc.data.getValue("password").toString(),
                            doc.data.getValue("role").toString(),
                            doc.data.getValue("city").toString(),
                            doc.data.getValue("phone_number").toString(),
                            doc.data.getValue("profile_picture").toString(),
                            doc.data.getValue("about").toString()
                        )
                    )
                }
                response.invoke(listData)
            }
            .addOnFailureListener {
                Log.d("Errors: ", it.localizedMessage)
            }
    }

    fun fetchPotofolioByPhotographerID(idPhotographer: String, response: (List<Portofolio>) -> Unit){
        portofolioCollection.whereEqualTo("user_id", idPhotographer).get()
            .addOnSuccessListener {
                val listData: MutableList<Portofolio> = mutableListOf()
                for (doc in it){
                    listData.add(
                        Portofolio(
                            doc.id,
                            doc.data.getValue("portofolio_image").toString(),
                            doc.data.getValue("user_id").toString()
                        )
                    )
                }
                response.invoke(listData)
            }
            .addOnFailureListener {
                Log.d("Errors: ", it.localizedMessage)
            }

    }

    fun fetchPackageByPhotographerID(idPhotographer: String, response: (List<Package>) -> Unit){
        packageCollection.whereEqualTo("user_id", idPhotographer).get()
            .addOnSuccessListener {
                val listData: MutableList<Package> = mutableListOf()
                for (doc in it){
                    listData.add(
                        Package(
                            doc.id,
                            doc.data.getValue("title").toString(),
                            doc.data.getValue("type").toString(),
                            doc.data.getValue("time").toString(),
                            doc.data.getValue("price").toString().toLong(),
                            doc.data.getValue("benefit") as List<String>,
                            doc.data.getValue("user_id").toString()
                        )
                    )
                }
                response.invoke(listData)
            }
            .addOnFailureListener {
                Log.d("Errors: ", it.localizedMessage)
            }
    }
}