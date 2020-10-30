package com.tugasakhir.photographerbooking.model.services.order

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tugasakhir.photographerbooking.model.pojo.Order
import com.tugasakhir.photographerbooking.model.pojo.Package
import com.tugasakhir.photographerbooking.model.pojo.User
import javax.inject.Inject

class OrderServices  @Inject constructor() {
    private val auth = FirebaseAuth.getInstance()
    private val userCollection = FirebaseFirestore.getInstance().collection("users")
    private val orderCollection = FirebaseFirestore.getInstance().collection("orders")
    private val packageCollection = FirebaseFirestore.getInstance().collection("pakcage")

    fun createOrder(order: Order, response: (String)-> Unit){
        val data = HashMap<String, Any>()

        data["client_id"] = order.clientID
        data["photographer_id"] = order.photographerID
        data["package_id"] = order.packageID
        data["photoshoot_time"] = order.photoshootTime
        data["order_time"] = order.orderTime
        data["is_confirmed"] = order.isConfirmed
        data["is_done"] = order.isDone

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

    fun fetchOrderPhotographer(response: (List<Order>) -> Unit){
        orderCollection.whereEqualTo("photographer_id", auth.uid).get()
            .addOnSuccessListener {
                val listData: MutableList<Order> = mutableListOf()
                for (doc in it){
                    listData.add(
                        Order(
                            uid = doc.id,
                            clientID = doc.data.getValue("client_id").toString(),
                            photographerID = doc.data.getValue("photographer_id").toString(),
                            packageID = doc.data.getValue("package_id").toString(),
                            photoshootTime = doc.getTimestamp("photoshoot_time")?.toDate()!!,
                            orderTime = doc.getTimestamp("order_time")?.toDate()!!,
                            isConfirmed = doc.data.getValue("is_confirmed") as Boolean,
                            isDone = doc.data.getValue("is_done") as Boolean
                        )
                    )
                }
                response.invoke(listData)
            }
            .addOnFailureListener {
                Log.d("Errors: ", it.localizedMessage)
            }
    }

    fun fetchClient(response: (List<User>) -> Unit){
        userCollection.whereEqualTo("role", "client").get()
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

    fun getPackagebyID(idPackage: String, response: (Package) -> Unit){
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
}