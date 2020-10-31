package com.tugasakhir.photographerbooking.model.services.photographer.profile

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.tugasakhir.photographerbooking.model.pojo.User
import com.tugasakhir.photographerbooking.model.pojo.Package
import com.tugasakhir.photographerbooking.model.pojo.Portofolio
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

class PhotographerProfile @Inject constructor() {
    private val auth = FirebaseAuth.getInstance()
    private val userCollection = FirebaseFirestore.getInstance().collection("users")
    private val storageRef = FirebaseStorage.getInstance().reference
    private val portofolioCollection = FirebaseFirestore.getInstance().collection("portofolio")
    private val packageCollection = FirebaseFirestore.getInstance().collection("pakcage")

    fun updateUserData(user: User, response: (String) -> Unit) {

        val data = HashMap<String, Any>()
        data["fullname"] = user.fullname
        data["email"] = user.email
        data["password"] = user.password
        data["role"] = user.role
        data["city"] = user.city
        data["phone_number"] = user.phoneNumber
        data["profil_picture"] = user.profilePicture
        data["about"] = user.about

        auth.uid?.let { it ->
            userCollection
                .document(it)
                .set(data, SetOptions.merge())
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

    fun changeProfilePicture(path: Uri, response: (String) -> Unit) {
        val ref = storageRef.child("users/${auth.uid}/profile_picture/pp-${auth.uid}")

        ref.putFile(path).continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            ref.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                Log.d("Upload Storage Success", "Download url : $downloadUri")
                if (downloadUri != null) {
                    val dt = hashMapOf("profile_picture" to downloadUri.toString())

                    auth.uid?.let { it ->
                        userCollection
                            .document(it)
                            .set(dt, SetOptions.merge())
                            .addOnSuccessListener {
                                Log.d("User Data Update", "Successfully Update User")
                                response.invoke("Successfully Upload Image")
                            }
                            .addOnFailureListener { error ->
                                Log.e("User Data Update", error.localizedMessage!!)
                                response.invoke("Error Update: ${error.localizedMessage}")
                            }
                    }
                }
            }
        }
    }

    fun addPortofolio(path: Uri, response: (String) -> Unit) {
        val ref = storageRef.child("users/${auth.uid}/portofolio/${UUID.randomUUID()}")

        ref.putFile(path).continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            ref.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                Log.d("Upload Storage Success", "Download url : $downloadUri")
                if (downloadUri != null) {
                    val dt = hashMapOf(
                        "portofolio_image" to downloadUri.toString(),
                        "user_id" to auth.uid
                    )

                    portofolioCollection.document()
                        .set(dt)
                        .addOnSuccessListener {
                            Log.d("Add Portofolio", "Successfully Add Portofolio")
                            response.invoke("Successfully Add Portofolio")
                        }
                        .addOnFailureListener {
                            Log.e("Add Portofolio", it.localizedMessage!!)
                            response.invoke("Error Add Portofolio: ${it.localizedMessage}")
                        }
                }
            }
        }
    }

    fun fetchPortofolio(response: (List<Portofolio>) -> Unit) {
        portofolioCollection
            .addSnapshotListener { value, _ ->
                if (value != null){
                    val listData: MutableList<Portofolio> = mutableListOf()
                    for (doc in value) {
                        if (doc["user_id"] == auth.uid) {
                            listData.add(
                                Portofolio(
                                    doc.id,
                                    doc["portofolio_image"].toString(),
                                    doc["user_id"].toString()
                                )
                            )
                        }
                    }
                    response.invoke(listData)
                }
            }
    }

    fun changePortofolio(idPortofolio: String, path: Uri, response: (String) -> Unit) {
        val ref = storageRef.child("users/${auth.uid}/portofolio/${UUID.randomUUID()}")

        ref.putFile(path).continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            ref.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                Log.d("Upload Storage Success", "Download url : $downloadUri")
                if (downloadUri != null) {
                    val dt = hashMapOf("portofolio_image" to downloadUri.toString())

                    Log.d("Id Portofolio", idPortofolio)

                    portofolioCollection.document(idPortofolio)
                        .set(dt, SetOptions.merge())
                        .addOnSuccessListener {
                            Log.d("Update Portofolio", "Successfully Update Portofolio")
                            response.invoke("Successfully Update Portofolio")
                        }
                        .addOnFailureListener {
                            Log.e("Update Portofolio", it.localizedMessage!!)
                            response.invoke("Error Update Portofolio: ${it.localizedMessage}")
                        }
                }
            }
        }
    }

    fun deletePortofolio(idPortofolio: String, response: (String) -> Unit) {
        portofolioCollection.document(idPortofolio)
            .delete()
            .addOnSuccessListener {
                Log.d("Delete Portofolio", "Successfully Delete Portofolio")
                response.invoke("Successfully Delete Portofolio")
            }
            .addOnFailureListener {
                Log.e("Delete Portofolio", it.localizedMessage!!)
                response.invoke("Error Delete Portofolio: ${it.localizedMessage}")
            }
    }

    fun addPackage(data: Package, response: (String) -> Unit) {
        val dt = HashMap<String, Any>()
        dt["title"] = data.title
        dt["type"] = data.type
        dt["time"] = data.time
        dt["price"] = data.price
        dt["benefit"] = data.benefit
        dt["user_id"] = data.userID

        packageCollection.document().set(dt)
            .addOnSuccessListener {
                Log.d("Add Package", "Successfully Add Package")
                response.invoke("Successfully Add Package")
            }
            .addOnFailureListener {
                Log.d("Add Package", it.localizedMessage)
                response.invoke("Error Add Package : ${it.localizedMessage}")
            }
    }

    fun fetchPackage(response: (List<Package>) -> Unit) {
        packageCollection
            .addSnapshotListener { value, _ ->
                if (value != null){
                    val listData: MutableList<Package> = mutableListOf()
                    for (doc in value) {
                        if (doc["user_id"] == auth.uid) {
                            listData.add(
                                Package(
                                    doc.id,
                                    doc["title"].toString(),
                                    doc["type"].toString(),
                                    doc["time"].toString(),
                                    doc["price"] as Long,
                                    doc["benefit"] as List<String>,
                                    doc["userID"].toString()
                                )
                            )
                        }
                    }
                    response.invoke(listData)
                }
            }
    }

    fun editPackage(data: Package, response: (String) -> Unit) {
        val dt = HashMap<String, Any>()
        dt["title"] = data.title
        dt["type"] = data.type
        dt["time"] = data.time
        dt["price"] = data.price
        dt["benefit"] = data.benefit
        dt["user_id"] = data.userID

        packageCollection.document(data.uid!!)
            .set(dt, SetOptions.merge())
            .addOnSuccessListener {
                Log.d("Update Package", "Successfully Update Package")
                response.invoke("Successfully Update Package")
            }
            .addOnFailureListener {
                Log.d("Update Package", it.localizedMessage)
                response.invoke("Error Update Package : ${it.localizedMessage}")
            }
    }

    fun deletePackage(data: Package, response: (String) -> Unit) {
        packageCollection.document(data.uid!!)
            .delete()
            .addOnSuccessListener {
                Log.d("Delete Package", "Successfully Delete Package")
                response.invoke("Successfully Delete Package")
            }
            .addOnFailureListener {
                Log.d("Delete Package", it.localizedMessage)
                response.invoke("Error Delete Package : ${it.localizedMessage}")
            }
    }
}