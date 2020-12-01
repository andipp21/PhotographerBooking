package com.tugasakhir.photographerbooking.model.services.order

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.tugasakhir.photographerbooking.model.pojo.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap


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
        data["is_reviewed"] = order.isReviewed

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
                                payImage = doc.data.getValue("pay_image").toString(),
                                isReviewed = doc.data.getValue("is_reviewed") as Boolean,
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
                                isPayed = doc.data.getValue("is_payed") as Boolean,
                                isReviewed = doc.data.getValue("is_reviewed") as Boolean
                            )
                        )
                    }

                    Log.d("list photographer", listData.toString())
                    response.invoke(listData)
                }
            }
    }

    fun getPhotographerOrderAmount(photographerID: String, response: (Int) -> Unit) {
        orderCollection.whereEqualTo("photographer_id", photographerID)
            .addSnapshotListener { value, _ ->
                val jumlah = value?.documents?.size

                if (jumlah != null) {
                    response.invoke(jumlah)
                }
            }
    }

    fun getPhotographerOrderAmountExt(photographerID: String, response: (OrderAmount) -> Unit) {
        orderCollection.whereEqualTo("photographer_id", photographerID)
            .addSnapshotListener { value, _ ->
                val total = value?.documents?.size!!

                var orderYear = 0
                var orderMonth = 0
                var orderDay = 0
                var waitConfirmation = 0
                var confirmed = 0
                var payed = 0
                var done = 0
                var reviewed = 0

                var photoshootYear = 0
                var photoshootMonth = 0
                var photoshootDay = 0

                val calOrder = Calendar.getInstance()
                val calPhotoshoot = Calendar.getInstance()

                val cal = Calendar.getInstance()
                val year: Int = cal.get(Calendar.YEAR)
                val month: Int = cal.get(Calendar.MONTH)
                val day: Int = cal.get(Calendar.DAY_OF_MONTH)

                for (doc in value) {
                    calOrder.time = doc.getTimestamp("order_time")?.toDate()!!
                    val yearOrder = calOrder.get(Calendar.YEAR)
                    val monthOrder = calOrder.get(Calendar.MONTH)
                    val dayOrder = calOrder.get(Calendar.DAY_OF_MONTH)

                    calPhotoshoot.time = doc.getTimestamp("photoshoot_time")?.toDate()!!
                    val yearPhotoshoot = calPhotoshoot.get(Calendar.YEAR)
                    val monthPhotoshoot = calPhotoshoot.get(Calendar.MONTH)
                    val dayPhotoshoot = calPhotoshoot.get(Calendar.DAY_OF_MONTH)

                    val isConfirmed = doc.data.getValue("is_confirmed") as Boolean
                    val isDone = doc.data.getValue("is_done") as Boolean
                    val isPayed = doc.data.getValue("is_payed") as Boolean
                    val isReviewed = doc.data.getValue("is_reviewed") as Boolean


                    if (yearOrder == year) {
                        orderYear++
                    }
                    if (monthOrder == month) {
                        orderMonth++
                    }
                    if (dayOrder == day) {
                        orderDay++
                    }

                    if (isConfirmed && isPayed && isDone) {
                        done++
                    } else if (isConfirmed && isPayed && !isDone) {
                        payed++

                        if (yearPhotoshoot == year) {
                            photoshootYear++
                        }
                        if (monthPhotoshoot == month) {
                            photoshootMonth++
                        }
                        if (dayPhotoshoot == day) {
                            photoshootDay++
                        }
                    } else if (isConfirmed && !isPayed && !isDone) {
                        confirmed++
                    } else if (!isConfirmed && !isPayed && !isDone) {
                        waitConfirmation++
                    }

                    if (isReviewed) {
                        reviewed++
                    }

                }

                val data = OrderAmount(
                    totalOrder = total,
                    orderThisYear = orderYear,
                    orderThisMonth = orderMonth,
                    orderThisDay = orderDay,
                    orderWaitConfirmation = waitConfirmation,
                    orderConfirmed = confirmed,
                    orderPayed = payed,
                    orderDone = done,
                    orderReviewed = reviewed,
                    photoshootThisYear = photoshootYear,
                    photoshootThisMonth = photoshootMonth,
                    photoshootThisDay = photoshootDay
                )

                response.invoke(data)
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
                                profilePicture = doc.data["profile_picture"].toString(),
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

    fun createReview(orderID: String, review: Review, response: (String) -> Unit) {
        val dt = HashMap<String, Any>()

        dt["review_text"] = review.review
        dt["review_score"] = review.score

        val data = HashMap<String, Any>()

        data["review"] = dt
        data["is_reviewed"] = true

        orderCollection.document(orderID)
            .set(data, SetOptions.merge())
            .addOnSuccessListener {
                Log.d("Make Review", "Successfully Make Review")
                response.invoke("Successfully Make Review")
            }
            .addOnFailureListener { error ->
                Log.e("Make Review", error.localizedMessage!!)
                response.invoke("Error Make Review: ${error.localizedMessage}")
            }
    }

    fun getReview(idOrder: String, response: (Review) -> Unit) {
        orderCollection.document(idOrder).get()
            .addOnSuccessListener {

                val hashmap = it["review"] as Map<*, *>

                val data = Review(
                    review = hashmap["review_text"].toString(),
                    score = hashmap["review_score"].toString().toInt()
                )

                response.invoke(data)
            }
    }

    fun getAllPhotographerReview(idPhotographer: String, response: (List<Review>) -> Unit) {
        orderCollection.whereEqualTo("is_reviewed", true)
            .whereEqualTo("photographer_id", idPhotographer)
            .addSnapshotListener { value, _ ->
                if (value != null) {
                    val listData: MutableList<Review> = mutableListOf()

                    for (doc in value) {
                        val hashmap = doc.data["review"] as Map<*, *>

                        val data = Review(
                            review = hashmap["review_text"].toString(),
                            score = hashmap["review_score"].toString().toInt()
                        )

                        Log.d("data review", data.toString())

                        listData.add(data)
                    }

                    response.invoke(listData)
                }
            }
    }
}