package com.tugasakhir.photographerbooking.view.client.fragment.orderReview

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.model.pojo.Order
import com.tugasakhir.photographerbooking.model.pojo.Package
import com.tugasakhir.photographerbooking.model.pojo.User
import com.tugasakhir.photographerbooking.view.client.activity.order.OrderProcessingActivity
import com.tugasakhir.photographerbooking.viewModel.order.OrderViewModel
import kotlinx.android.synthetic.main.fragment_client_order_review.view.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ClientOrderReviewFragment(
    private val paket: Package,
    val photographer: User,
    val time: Date
) : BottomSheetDialogFragment() {
    private var param1: String? = null
    private var param2: String? = null

    private var viewModel: OrderViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_order_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(OrderViewModel::class.java)

        view.btnClose.setOnClickListener {
            dismiss()
        }

        Glide.with(this)
            .load(photographer.profilePicture)
            .circleCrop()
            .into(view.photographerProfilePicture)

        view.photographerName.text = photographer.fullname
        view.typeContent.text = paket.type
        view.packageContent.text = paket.title
        view.totalFee.text = convertMoney(paket.price)

        val cal = Calendar.getInstance()
        cal.time = time

        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val minute = cal.get(Calendar.MINUTE)

        val bulan: String = if (month + 1 < 10) {
            "0${month + 1}"
        } else {
            "${month + 1}"
        }

        val hari: String = if (day < 10) {
            "0$day"
        } else {
            "$day"
        }

        val jam: String = if (hour < 10) {
            "0$hour"
        } else {
            "$hour"
        }

        val menit: String = if (minute < 10) {
            "0$minute"
        } else {
            "$minute"
        }

        view.dateContent.text = "$hari / $bulan / $year"
        view.timeContent.text = "$jam : $menit"

        view.btnOrder.setOnClickListener {
            val data = FirebaseAuth.getInstance().uid?.let { it1 ->
                photographer.uid?.let { it2 ->
                    paket.uid?.let { it3 ->
                        Order(
                            clientID = it1,
                            photographerID = it2,
                            packageID = it3,
                            photoshootTime = time,
                            orderTime = Calendar.getInstance().time,
                            isConfirmed = false,
                            isDone = false
                        )
                    }
                }
            }

            if (data != null) {
                viewModel?.createOrder(data)
                observerViewModel(viewModel!!, viewLifecycleOwner)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        viewModel = null
    }

    private fun observerViewModel(viewModel: OrderViewModel, lifecycleOwner: LifecycleOwner) {
        viewModel.responseLiveData.observe(lifecycleOwner, {
            Log.d("Create Order", it)
            if (it == "Successfully Create Order") {
                val intent = Intent(activity, OrderProcessingActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        })
    }

    private fun convertMoney(input: Long): String {
        val formatter =
            NumberFormat.getCurrencyInstance(Locale("in", "ID")) as DecimalFormat

        var formattedString = formatter.format(input).toString()

        if (formattedString.contains("Rp")) {
            formattedString = formattedString.replace("Rp".toRegex(), "Rp ")
        }

        if (formattedString.contains(",00")) {
            formattedString = formattedString.replace(",00".toRegex(), "")
        }

        return formattedString
    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment ClientOrderReviewFragment.
//         */
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            ClientOrderReviewFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}