package com.tugasakhir.photographerbooking.model.services.order

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.tugasakhir.photographerbooking.model.pojo.Order
import javax.inject.Inject

class OrderServices  @Inject constructor() {
    private val orderCollection = FirebaseFirestore.getInstance().collection("orders")

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
}