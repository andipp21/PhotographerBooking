package com.tugasakhir.photographerbooking.view.photographer.fragment.order

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.google.firebase.auth.FirebaseAuth
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.model.pojo.Order
import com.tugasakhir.photographerbooking.model.pojo.User
import com.tugasakhir.photographerbooking.view.photographer.activity.PhotographerActivity
import com.tugasakhir.photographerbooking.view.photographer.activity.order.PhotographerOrderDetailActivity
import com.tugasakhir.photographerbooking.view.photographer.adapter.order.PhotographerOrderAdapter
import com.tugasakhir.photographerbooking.viewModel.order.OrderViewModel
import kotlinx.android.synthetic.main.fragment_photographer_order.view.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PhotographerOrderFragment(val viewModel: OrderViewModel) : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private var adapter: PhotographerOrderAdapter? = null

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
        return inflater.inflate(R.layout.fragment_photographer_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usrID = FirebaseAuth.getInstance().uid.toString()

        (activity as PhotographerActivity).supportActionBar?.title = "Order"

        adapter = PhotographerOrderAdapter()
        view.rvOrderPhotographer.adapter = adapter

        adapter?.setOnItemClickCallback(object : PhotographerOrderAdapter.OnItemClickCallback {
            override fun onItemClicked(order: Order, client: User) {
                val intent= Intent(activity, PhotographerOrderDetailActivity::class.java)
                intent.putExtra("order", order)
                intent.putExtra("client", client)
                startActivity(intent)
            }
        })
    }

    override fun onResume() {
        super.onResume()

        viewModel.fetchOrderPhotographer(usrID)
        viewModel.fetchClient()

        observeViewModel(viewModel, viewLifecycleOwner)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        adapter?.clearList()

        adapter = null
    }

    private fun observeViewModel(
        actionDelegate: OrderViewModel,
        lifecycleOwner: LifecycleOwner
    ) {
        actionDelegate.responseListOrder.observe(lifecycleOwner, {
            adapter?.updateListsOrder(it)
        })

        actionDelegate.responseListUser.observe(lifecycleOwner, {
            adapter?.updateListUser(it)
        })
    }

}