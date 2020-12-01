package com.tugasakhir.photographerbooking.view.photographer.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.viewModel.order.OrderViewModel
import kotlinx.android.synthetic.main.fragment_photographer_home.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PhotographerHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PhotographerHomeFragment(val viewModel: OrderViewModel) : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var usrID: String

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
        return inflater.inflate(R.layout.fragment_photographer_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usrID = FirebaseAuth.getInstance().uid.toString()

        viewModel.getAllAmount(usrID)
        observer(viewModel, viewLifecycleOwner)
    }

    fun observer(actionDelegate: OrderViewModel, lifecycleOwner: LifecycleOwner){
        actionDelegate.responseOrderAmount.observe(lifecycleOwner, Observer {
            totalOrder.text = "${it.totalOrder} Total Orders"
            orderThisYearAmount.text = "${it.orderThisYear}"
            orderThisMonthAmount.text = "${it.orderThisMonth}"
            orderThisDayAmount.text = "${it.orderThisDay}"

            orderWaitConfirmationAmount.text = "${it.orderWaitConfirmation}"
            orderConfirmatedAmount.text = "${it.orderConfirmed}"
            orderPayedAmount.text = "${it.orderPayed}"
            orderDoneAmount.text = "${it.orderDone}"
            orderReviewedAmount.text = "${it.orderReviewed}"

            photoshootThisYearAmount.text = "${it.photoshootThisYear}"
            photoshootThisMonthAmount.text = "${it.photoshootThisMonth}"
            photoshootThisDayAmount.text = "${it.photoshootThisDay}"

        })
    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment PhotographerHomeFragment.
//         */
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            PhotographerHomeFragment()
//                .apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}