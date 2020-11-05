package com.tugasakhir.photographerbooking.model.services.order

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.tugasakhir.photographerbooking.model.pojo.Order
import com.tugasakhir.photographerbooking.model.pojo.Package
import com.tugasakhir.photographerbooking.model.pojo.User
import javax.inject.Inject

class OrderServices @Inject constructor() {
    private val userCollection = FirebaseFirestore.getInstance().collection("users")
    private val orderCollection = FirebaseFirestore.getInstance().collection("orders")
    private val packageCollection = FirebaseFirestore.getInstance().collection("pakcage")
    private val storageRef = FirebaseStorage.getInstance().reference

    fun createOrder(order: Order, response: (String) -> Unit) {
        val data = HashMap<String, Any>()

        data["client_id"] = order.clientID
        data["photographer_id"] = order.photographerID
        data["package_id"] = order.packageID
        data["photoshoot_time"] = order.photoshootTime
        data["order_time"] = order.orderTime
        data["is_confirmed"] = order.isConfirmed
        data["is_done"] = order.isDone
        data["is_payed"] = order.isPayed
        data["pay_image"] = order.payImage

        orderCollection.document()
            .set(data)
            .addOnSuccessListener {
                Log.d("Create Order", "Successfully Create Order")
                response.invoke("Successfully Create Order")
            }
            .addOnFailureListener {
                Log.e("Create Order", it.localizedMessage!!)
                response.invoke("Error Create Order: ${it.localizedMessage}")
            }
    }

    fun makePayment(path: Uri, orderID: String, response: (String) -> Unit) {
        val ref = storageRef.child("order/$orderID")

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
                    val dt = HashMap<String, Any>()

                    dt["pay_image"] = downloadUri.toString()
                    dt["is_payed"] = true

                    orderCollection.document(orderID)
                        .set(dt, SetOptions.merge())
                        .addOnSuccessListener {
                            Log.d("Order Payment", "Successfully Pay Order")
                            response.invoke("Successfully Pay Order")
                        }
                        .addOnFailureListener { error ->
                            Log.e("Order Payment", error.localizedMessage!!)
                            response.invoke("Error Payment: ${error.localizedMessage}")
                        }
                }
            }
        }
    }

    fun fetchOrderPhotographer(usrID: String, response: (List<Order>) -> Unit) {
        orderCollection.whereEqualTo("photographer_id", usrID)
//            .orderBy("order_time", Query.Direction.DESCENDING)
            .addSnapshotListener { value, _ ->
                val listData: MutableList<Order> = mutableListOf()
                if (value != null) {
                    for (doc in value) {
                        listData.add(
                            Order(
                                uid = doc.id,
                                clientID = doc.data.getValue("client_id").toString(),
                                photographerID = doc.data.getValue("photographer_id").toString(),
                                packageID = doc.data.getValue("package_id").toString(),
                                photoshootTime = doc.getTimestamp("photoshoot_time")?.toDate()!!,
                                orderTime = doc.getTimestamp("order_time")?.toDate()!!,
                                isConfirmed = doc.data.getValue("is_confirmed") as Boolean,
                                isDone = doc.data.getValue("is_done") as Boolean,
                                isPayed = doc.data.getValue("is_payed") as Boolean,
                                payImage = doc.data.getValue("pay_image").toString()
                            )
                        )
                    }

                    Log.d("list photographer", listData.toString())
                    response.invoke(listData)
                }
            }
    }

    fun fetchOrderClient(usrID: String, response: (List<Order>) -> Unit) {
        orderCollection.whereEqualTo("client_id", usrID)
//            .orderBy("order_time", Query.Direction.DESCENDING)
            .addSnapshotListener { value, _ ->
                val listData: MutableList<Order> = mutableListOf()
                if (value != null) {
                    for (doc in value) {
                        listData.add(
                            Order(
                                uid = doc.id,
                                clientID = doc.data.getValue("client_id").toString(),
                                photographerID = doc.data.getValue("photographer_id").toString(),
                                packageID = doc.data.getValue("package_id").toString(),
                                photoshootTime = doc.getTimestamp("photoshoot_time")?.toDate()!!,
                                orderTime = doc.getTimestamp("order_time")?.toDate()!!,
                                isConfirmed = doc.data.getValue("is_confirmed") as Boolean,
                                isDone = doc.data.getValue("is_done") as Boolean,
                                payImage = doc.data.getValue("pay_image").toString(),
                                isPayed = doc.data.getValue("is_payed") as Boolean
                            )
                        )
                    }

                    Log.d("list photographer", listData.toString())
                    response.invoke(listData)
                }
            }
    }

    fun fetchClient(response: (List<User>) -> Unit) {
        userCollection.whereEqualTo("role", "client")
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
                                profilePicture = doc.data.getValue("profile_picture").toString(),
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

    fun getPackagebyID(idPackage: String, response: (Package) -> Unit) {
        packageCollection.document(idPackage).get()
            .addOnSuccessListener {
                val dt = Package(
                    it.id,
                    it["title"].toString(),
                    it["type"].toString(),
                    it["time"].toString(),
                    it["price"] as Long,
                    it["benefit"] as List<String>,
                    it["userID"].toString()
                )

                response.invoke(dt)
            }
            .addOnFailureListener {
                Log.d("Errors: ", it.localizedMessage)
            }
    }

    fun confirmOrder(idOrder: String, response: (String) -> Unit) {
        val dt = hashMapOf("is_confirmed" to true)
        orderCollection.document(idOrder).set(dt, SetOptions.merge())
            .addOnSuccessListener {
                Log.d("Order Confirmation", "Order Confirmated")
                response.invoke("Order Confirmated")
            }
            .addOnFailureListener { error ->
                Log.e("Order Confirmation", error.localizedMessage!!)
                response.invoke("Error Order Confirmation: ${error.localizedMessage}")
            }
    }

    fun confirmOrderDone(idOrder: String, response: (String) -> Unit) {
        val dt = hashMapOf("is_done" to true)
        orderCollection.document(idOrder).set(dt, SetOptions.merge())
            .addOnSuccessListener {
                Log.d("Order Confirmation", "Order Done")
                response.invoke("Order Done")
            }
            .addOnFailureListener { error ->
                Log.e("Order Confirmation", error.localizedMessage!!)
                response.invoke("Error Order Confirmation: ${error.localizedMessage}")
            }
    }
}