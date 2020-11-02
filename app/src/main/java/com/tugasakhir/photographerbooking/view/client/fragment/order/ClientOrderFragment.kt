package com.tugasakhir.photographerbooking.view.client.fragment.order

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.model.pojo.Order
import com.tugasakhir.photographerbooking.model.pojo.User
import com.tugasakhir.photographerbooking.view.client.activity.ClientActivity
import com.tugasakhir.photographerbooking.view.client.activity.order.OrderProcessingActivity
import com.tugasakhir.photographerbooking.view.client.adapter.order.ClientOrderAdapter
import com.tugasakhir.photographerbooking.viewModel.order.OrderViewModel
import kotlinx.android.synthetic.main.fragment_client_order.view.*
import kotlinx.android.synthetic.main.item_order_photographer.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ClientOrderFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var adapter: ClientOrderAdapter? = null

    private var viewModelOrder: OrderViewModel? = null

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
        return inflater.inflate(R.layout.fragment_client_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as ClientActivity).supportActionBar?.title = "Profile"

        viewModelOrder = ViewModelProvider(this).get(OrderViewModel::class.java)

        adapter = ClientOrderAdapter()

        view.rvOrderClient.adapter = adapter

        adapter?.setOnItemClickCallback(object : ClientOrderAdapter.OnItemClickCallback {
            override fun onItemClicked(order: Order, client: User) {
                if (order.isConfirmed && order.isDone){

                } else if (order.isConfirmed && !order.isDone){

                } else if (!order.isConfirmed && !order.isDone){
                    val intent = Intent(activity, OrderProcessingActivity::class.java)
                    startActivity(intent)
                }
            }

        })
    }

    override fun onResume() {
        super.onResume()

        viewModelOrder?.fetchOrderClient()
        viewModelOrder?.fetchPhotographer()

        viewModelOrder?.let { observeViewModel(it, viewLifecycleOwner) }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        viewModelOrder = null

        adapter?.clearList()

        adapter = null
    }

    private fun observeViewModel(
        actionDelegate: OrderViewModel,
        lifecycleOwner: LifecycleOwner
    ) {
        actionDelegate.responseListOrder.observe(lifecycleOwner, {
            Log.d("hasil", it.toString())
            adapter?.updateListsOrder(it)
        })

        actionDelegate.responseListUser.observe(lifecycleOwner, {
            Log.d("hasil2", it.toString())
            adapter?.updateListUser(it)
        })
    }
//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment ClientOrderFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            ClientOrderFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}