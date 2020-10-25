package com.tugasakhir.photographerbooking.view.photographer.fragment.profile.subFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.model.pojo.photographer.Package
import com.tugasakhir.photographerbooking.view.photographer.activity.profile.PhotographerAddPackageActivity
import com.tugasakhir.photographerbooking.view.photographer.activity.profile.PhotographerEditPackageActivity
import com.tugasakhir.photographerbooking.view.photographer.adapter.profile.photographerPackage.PhotographerPackageAdapter
import com.tugasakhir.photographerbooking.viewModel.photographer.PhotographerProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile_photographer_package.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProfilePhotographerPackageFragment(val viewModel: PhotographerProfileViewModel) : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var adapter: PhotographerPackageAdapter

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
        return inflater.inflate(R.layout.fragment_profile_photographer_package, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnAddPackage.setOnClickListener {
            startActivity(Intent(activity, PhotographerAddPackageActivity::class.java))
        }

        adapter = PhotographerPackageAdapter()
        rvPackage.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        GlobalScope.launch {
            viewModel.fetchPackage()
        }
        observeViewModel(viewModel, viewLifecycleOwner)
    }

    private fun observeViewModel(actionDelegate: PhotographerProfileViewModel, lifecycleOwner: LifecycleOwner) {
        actionDelegate.responseListPackage.observe(lifecycleOwner, {
            activity?.runOnUiThread {
                adapter.updateLists(it)
                adapter.setOnItemClickCallback(object : PhotographerPackageAdapter.OnItemClickCallback {
                    override fun editPackageClicked(data: Package) {
                        val intent = Intent(activity, PhotographerEditPackageActivity::class.java)
                        intent.putExtra("packageData", data)

                        startActivity(intent)
                    }

                    override fun deletePacakageClicked(data: Package) {
                        viewModel.deletePackage(data)
                        observeViewModelDelete(viewModel, viewLifecycleOwner)
                    }
                })
            }
        })
    }

    fun observeViewModelDelete(actionDelegate: PhotographerProfileViewModel, lifecycleOwner: LifecycleOwner){
        actionDelegate.responseLiveData.observe(lifecycleOwner, {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
            GlobalScope.launch {
                viewModel.fetchPackage()
            }
            observeViewModel(viewModel, viewLifecycleOwner)
        })
    }
}