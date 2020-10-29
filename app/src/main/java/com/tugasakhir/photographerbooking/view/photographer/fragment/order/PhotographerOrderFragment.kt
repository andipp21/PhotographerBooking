package com.tugasakhir.photographerbooking.view.photographer.fragment.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.view.photographer.activity.PhotographerActivity
import com.tugasakhir.photographerbooking.view.photographer.adapter.order.PhotographerOrderAdapter
import com.tugasakhir.photographerbooking.viewModel.order.OrderViewModel
import kotlinx.android.synthetic.main.fragment_photographer_order.view.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PhotographerOrderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PhotographerOrderFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private var viewModel: OrderViewModel? = null

    private lateinit var adapter: PhotographerOrderAdapter

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

        viewModel = ViewModelProvider(this).get(OrderViewModel::class.java)

        (activity as PhotographerActivity).supportActionBar?.title = "Order"

        adapter = PhotographerOrderAdapter()
        view.rvOrderPhotographer.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        viewModel?.fetchOrderPhotographer()
        viewModel?.fetchClient()

        viewModel?.let { observeViewModel(it, viewLifecycleOwner) }
    }

    private fun observeViewModel(
        actionDelegate: OrderViewModel,
        lifecycleOwner: LifecycleOwner
    ){
        actionDelegate.responseListOrder.observe(lifecycleOwner, {
            adapter.updateListsOrder(it)
        })

        actionDelegate.responseListUser.observe(lifecycleOwner, {
            adapter.updateListUser(it)
        })
    }

    override fun onDestroy() {
        super.onDestroy()

        viewModel = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PhotographerOrderFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PhotographerOrderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}