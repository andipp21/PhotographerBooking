package com.tugasakhir.photographerbooking.view.photographer.fragment.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.model.pojo.photographer.Portofolio
import com.tugasakhir.photographerbooking.view.photographer.adapter.profile.portofolio.PhotographerPortofolioAdapter
import com.tugasakhir.photographerbooking.view.photographer.fragment.profile.subFragment.PhotographerProfilPortofolioAddFragment
import com.tugasakhir.photographerbooking.view.photographer.fragment.profile.subFragment.PhotographerProfilPortofolioDetailFragment
import com.tugasakhir.photographerbooking.viewModel.photographer.PhotographerProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile_photographer_portofolio.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfilePhotographerPortofolioFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfilePhotographerPortofolioFragment(val viewModel: PhotographerProfileViewModel) : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var adapter: PhotographerPortofolioAdapter

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
        return inflater.inflate(R.layout.fragment_profile_photographer_portofolio, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnAddPortofolio.setOnClickListener {
            PhotographerProfilPortofolioAddFragment(viewModel).show(childFragmentManager,"Fragment Add Portofolio")
        }

        adapter = PhotographerPortofolioAdapter()
        rvPortofolio.adapter = adapter

        Log.d("portofolio", " on create")
    }

    override fun onResume() {
        super.onResume()

        GlobalScope.launch {
            viewModel.fetchPortofolio()
        }
        observeViewModel(viewModel, viewLifecycleOwner)

        Log.d("portofolio", " on resume")
    }

    override fun onStart() {
        super.onStart()

        Log.d("portofolio", " on start")
    }

    private fun observeViewModel(actionDelegate: PhotographerProfileViewModel, lifecycleOwner: LifecycleOwner) {
        actionDelegate.responseListPortofolio.observe(lifecycleOwner, {
            activity?.runOnUiThread {
                adapter.updateLists(it)
                adapter.setOnItemClickCallback(object : PhotographerPortofolioAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: Portofolio) {
                        PhotographerProfilPortofolioDetailFragment(viewModel, data).show(childFragmentManager, "Portofolio Detail")
                    }
                })
            }
        })
    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment ProfilePhotographerPortofolioFragment.
//         */
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            ProfilePhotographerPortofolioFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}