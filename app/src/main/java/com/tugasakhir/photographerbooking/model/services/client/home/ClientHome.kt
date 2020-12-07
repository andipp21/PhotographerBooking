package com.tugasakhir.photographerbooking.model.services.client.home

import com.google.firebase.firestore.FirebaseFirestore
import com.tugasakhir.photographerbooking.model.pojo.Package
import com.tugasakhir.photographerbooking.model.pojo.Portofolio
import com.tugasakhir.photographerbooking.model.pojo.User
import javax.inject.Inject

class ClientHome @Inject constructor() {
    private val userCollection = FirebaseFirestore.getInstance().collection("users")
    private val portofolioCollection = FirebaseFirestore.getInstance().collection("portfolio")
    private val packageCollection = FirebaseFirestore.getInstance().collection("package")

    fun fetchPhotographer(response: (List<User>) -> Unit) {
        userCollection.whereEqualTo("role", "photographer")
            .addSnapshotListener { value, _ ->
                if (value != null) {
                    val listData: MutableList<User> = mutableListOf()
                    for (doc in value) {
                        listData.add(
                            User(
                                uid = doc.id,
                                fullname = doc.data.getValue("fullname").toString(),
                                email = doc.data.getValue("email").toString(),
                                password = doc.data.getValue("password").toString(),
                                role = doc.data.getValue("role").toString(),
                                city = doc.data.getValue("city").toString(),
                                phoneNumber = doc.data.getValue("phone_number").toString(),
                                profilePicture = doc.data.get("profile_picture").toString(),
                                about = doc.data.getValue("about").toString(),
                                numberOvo = doc.data.getValue("ovo_number").toString(),
                                numberDana = doc.data.getValue("dana_number").toString(),
                                numberLinkAja = doc.data.getValue("link_aja_number").toString(),
                                numberGopay = doc.data.getValue("gopay_number").toString()
                            )
                        )
                    }
                    response.invoke(listData)
                }
            }
    }

    fun fetchPotofolioByPhotographerID(
        idPhotographer: String,
        response: (List<Portofolio>) -> Unit
    ) {
        portofolioCollection.whereEqualTo("user_id", idPhotographer)
            .addSnapshotListener { value, _ ->
                if (value != null) {
                    val listData: MutableList<Portofolio> = mutableListOf()
                    for (doc in value) {
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
            }
    }

    fun fetchPackageByPhotographerID(idPhotographer: String, response: (List<Package>) -> Unit) {
        packageCollection.whereEqualTo("user_id", idPhotographer)
            .addSnapshotListener { value, _ ->
                if (value != null) {
                    val listData: MutableList<Package> = mutableListOf()
                    for (doc in value) {
                        listData.add(
                            Package(
                                doc.id,
                                doc.data.getValue("title").toString(),
                                doc.data.getValue("type").toString(),
                                doc.data.getValue("price").toString().toLong(),
                                doc.data.getValue("benefit") as List<String>,
                                doc.data.getValue("user_id").toString()
                            )
                        )
                    }
                    response.invoke(listData)
                }
            }
    }
}