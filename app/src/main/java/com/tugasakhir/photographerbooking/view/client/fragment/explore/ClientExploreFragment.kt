package com.tugasakhir.photographerbooking.view.client.fragment.explore

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.model.pojo.User
import com.tugasakhir.photographerbooking.view.client.activity.ClientActivity
import com.tugasakhir.photographerbooking.view.client.activity.photographerDetail.PhotographerDetailActivity
import com.tugasakhir.photographerbooking.view.client.adapter.explore.ClientExploreAdapter
import com.tugasakhir.photographerbooking.viewModel.client.ClientHomeViewModel
import com.tugasakhir.photographerbooking.viewModel.order.OrderViewModel
import kotlinx.android.synthetic.main.fragment_client_explore.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ClientExploreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClientExploreFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var viewModel: ClientHomeViewModel? = null

    private lateinit var adapter: ClientExploreAdapter

    override fun onDestroy() {
        super.onDestroy()

        viewModel = null
    }

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
        return inflater.inflate(R.layout.fragment_client_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as ClientActivity).supportActionBar?.title = "Explore"

        viewModel = ViewModelProvider(this).get(ClientHomeViewModel::class.java)

        adapter = ClientExploreAdapter()

//        if (orderViewModel != null){
//            adapter.setViewModel(orderViewModel!!)
//        }

        adapter.setOnItemClickCallback(object : ClientExploreAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intent= Intent(activity, PhotographerDetailActivity::class.java)
                intent.putExtra("photographer", data)
                startActivity(intent)
            }
        })

        rvExploreClient.adapter = adapter

        filterButton.setOnClickListener {
            FilterPhotographerFragment(viewModel!!,adapter).show(childFragmentManager, "Filter")
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel?.fetchPhotographer()

        viewModel?.let { observeViewModel(it, viewLifecycleOwner) }
    }

    private fun observeViewModel(actionDelegate: ClientHomeViewModel, lifecycleOwner: LifecycleOwner){
        actionDelegate.responseLivePhotographer.observe(lifecycleOwner, Observer{
            adapter.updateLists(it)
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ClientExploreFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ClientExploreFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}