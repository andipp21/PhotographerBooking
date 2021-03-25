package com.tugasakhir.photographerbooking.view.photographer.fragment.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.view.client.adapter.photographerDetail.review.PhotographerDetailReviewAdapter
import com.tugasakhir.photographerbooking.view.photographer.activity.PhotographerActivity
import com.tugasakhir.photographerbooking.viewModel.order.OrderViewModel
import kotlinx.android.synthetic.main.fragment_photographer_review.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PhotographerReviewFragment(val viewModel: OrderViewModel) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var usrID: String
    private lateinit var adapter: PhotographerDetailReviewAdapter

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
        return inflater.inflate(R.layout.fragment_photographer_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usrID = FirebaseAuth.getInstance().uid.toString()

        (activity as PhotographerActivity).supportActionBar?.title = "Review"

        adapter = PhotographerDetailReviewAdapter()
        rvPhotographerReview.adapter = adapter

        viewModel.getAllPhotographerReview(usrID)
        observer(viewModel, viewLifecycleOwner)
    }

    private fun observer(actionDelegate: OrderViewModel, lifecycleOwner: LifecycleOwner){
        actionDelegate.responseListReview.observe(lifecycleOwner, Observer {
            adapter.updateList(it)
        })
    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment PhotographerReviewFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            PhotographerReviewFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}